package com.haile.exe101.depairapplication.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.haile.exe101.depairapplication.models.entity.*;
import com.haile.exe101.depairapplication.models.enums.OrderStatus;
import com.haile.exe101.depairapplication.models.enums.PaymentStatus;
import com.haile.exe101.depairapplication.models.request.CreatePaymentLinkRequestBody;
import com.haile.exe101.depairapplication.models.response.BaseResponse;
import com.haile.exe101.depairapplication.services.interfaces.ICartService;
import com.haile.exe101.depairapplication.services.interfaces.ICustomerInfoService;
import com.haile.exe101.depairapplication.services.interfaces.IOrderService;
import com.haile.exe101.depairapplication.services.interfaces.IPaymentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.eclipse.angus.mail.imap.protocol.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;
import vn.payos.type.PaymentLinkData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private ICartService cartService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ICustomerInfoService customerInfoService;

    @Autowired
    private IPaymentService paymentService;

    private final PayOS payOS;

    public OrderController(PayOS payOS) {
        super();
        this.payOS = payOS;
    }

    @Operation(summary = "Create payment link",
            description = "PayOS returns payOs payment link and some responses")
    @PostMapping(path = "/create")
    public ObjectNode createPaymentLink(@RequestBody @Valid CreatePaymentLinkRequestBody RequestBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            // Tim cart item
            int price;
            int totalQuantity = 0;
            Cart cart = cartService.findCartById(RequestBody.getCartId()).getDetails();

            List<ItemData> items = new ArrayList<>();
            if (cart != null) {
                for (CartItem item : cart.getItems()) {
                    Product product = item.getProduct();
                    items.add(
                            ItemData.builder().
                                    name(product.getProductName()).
                                    price(product.getPrice()).
                                    quantity(item.getQuantity()).
                                    build())
                    ;
                    totalQuantity += item.getQuantity();
                }
                price = cart.getTotalPrice().intValueExact();
            } else {
                response.put("error", -1);
                response.put("message", "fail");
                response.set("data", null);
                return response;
            }
            // Tao order duoi db
            Order order = new Order();
            order.setTotalPrice(BigDecimal.valueOf(price));
            order.setOrderStatus(OrderStatus.PROCESSING);
            order.setCart(cart);

            // Tao payment
            Payment payment = new Payment();
            payment.setAmount(BigDecimal.valueOf(price));
            payment.setPaymentStatus(PaymentStatus.PENDING);

            Payment newPayment = paymentService.createOrSavePayment(payment);
            if (newPayment != null) {
                order.setPayment(payment);
            } else {
                response.put("error", -1);
                response.put("message", "fail");
                response.set("data", null);
                return response;
            }

            final String description = RequestBody.getDescription(); // Dien them sale - %
            final String returnUrl = RequestBody.getReturnUrl();
            final String cancelUrl = RequestBody.getCancelUrl();
            // Gen order code
            String currentTimeString = String.valueOf(String.valueOf(new Date().getTime()));
            long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));
            order.setOrderTrackingNumber(String.valueOf(orderCode));
            CustomerInfo customerInfo = customerInfoService.getCustomerInfo(RequestBody.getCustomerInfoId());
            order.setCustomerInfo(customerInfo);
            order.setTotalQuantity(totalQuantity);

            Order createdOrder = orderService.createOrSaveOrder(order);
            if (createdOrder != null) {
                PaymentData paymentData = PaymentData.builder().orderCode(orderCode).description(description).amount(price)
                        .items(items).buyerAddress(customerInfo.getShippingAddress()).buyerEmail(customerInfo.getEmail()).
                        buyerName(customerInfo.getFullName()).buyerPhone(customerInfo.getPhoneNumber()).
                        returnUrl(returnUrl).cancelUrl(cancelUrl).build();

                CheckoutResponseData data = payOS.createPaymentLink(paymentData);

                response.put("error", 0);
                response.put("message", "success");
                response.set("data", objectMapper.valueToTree(data));
                return response;
            } else {
                response.put("error", -1);
                response.put("message", "fail");
                response.set("data", null);
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", "fail");
            response.set("data", null);
            return response;

        }
    }

    @Operation(summary = "Get order that created on PayOS",
            description = "Input order ID that payOS returned")
    @GetMapping(path = "/{orderId}")
    public ObjectNode getOrderById(@PathVariable("orderId") long orderId) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();

        try {
            PaymentLinkData order = payOS.getPaymentLinkInformation(orderId);

            response.set("data", objectMapper.valueToTree(order));
            response.put("error", 0);
            response.put("message", "ok");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", e.getMessage());
            response.set("data", null);
            return response;
        }

    }

    @Operation(summary = "Cancel an order that processing",
            description = "Input order ID that payOS returned (using cancelUrl)")
    @PutMapping(path = "/{orderId}")
    public ObjectNode cancelOrder(@PathVariable("orderId") int orderId) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            PaymentLinkData order = payOS.cancelPaymentLink(orderId, null);

            if (order != null) {
                Order o = orderService.getOrderByOrderTrackingNumber(String.valueOf(orderId));
                if (o != null) {
                    o.setOrderStatus(OrderStatus.CANCELLED);
                    Payment p = o.getPayment();
                    p.setPaymentStatus(PaymentStatus.CANCELED);
                    paymentService.createOrSavePayment(p);
                    orderService.createOrSaveOrder(o);
                }
            }

            response.set("data", objectMapper.valueToTree(order));
            response.put("error", 0);
            response.put("message", "ok");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", e.getMessage());
            response.set("data", null);
            return response;
        }
    }

    @Operation(summary = "Confirm payOS payment",
            description = "Check payment by request payload with web hook")
    @PostMapping(path = "/confirm-webhook")
    public ObjectNode confirmWebhook(@RequestBody Map<String, String> requestBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            String str = payOS.confirmWebhook(requestBody.get("webhookUrl"));
            response.set("data", objectMapper.valueToTree(str));
            response.put("error", 0);
            response.put("message", "ok");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", e.getMessage());
            response.set("data", null);
            return response;
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @Operation(summary = "Get order in DB with track number",
            description = "Return the order with tracking number")
    @GetMapping("/tracking/{tracking-number}")
    public ResponseEntity<BaseResponse<Order>> getOrderByTrackingNumber(@PathVariable("tracking-number") String trackingNumber) {
        try {
            Order order = orderService.getOrderByOrderTrackingNumber(trackingNumber);
            if (order != null) {
                return ResponseEntity.ok(new BaseResponse<>(200, "Order found", null, order));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(404, "Order not found", null, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(500, e.getMessage(), null, null));
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @GetMapping("/search")
    public ResponseEntity<Page<Order>> searchOrdersByCustomerInfo(
            @RequestParam("searchTerm") String searchTerm,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Order> orders = orderService.searchOrdersByCustomerInfo(searchTerm, pageable);

        if (!orders.isEmpty()) {
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Page.empty());
        }
    }
}
