package com.haile.exe101.depairapplication.controllers.api;

import com.haile.exe101.depairapplication.models.entity.CartItem;
import com.haile.exe101.depairapplication.models.entity.Order;
import com.haile.exe101.depairapplication.models.entity.Product;
import com.haile.exe101.depairapplication.models.response.BaseResponse;
import com.haile.exe101.depairapplication.services.interfaces.IEmailSenderService;
import com.haile.exe101.depairapplication.services.interfaces.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mail")
public class SendMailController {

    @Autowired
    private IEmailSenderService mailSenderService;

    @Autowired
    private IOrderService orderService;

    @Operation(summary = "Email send after order confirmation",
            description = "Sau khi thanh toan thanh cong se gui mail tracking number")
    @PostMapping("/send/{order-tracking-number}/{recipient}")
    public ResponseEntity<BaseResponse<String>> sendMail(
            @PathVariable("order-tracking-number") String orderTrackingNumber,
            @PathVariable("recipient") @Valid @Email(message = "Invalid email") String recipient) {

        Order order = orderService.getOrderByOrderTrackingNumber(orderTrackingNumber);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse<>(404, "Order not found", null, null));
        }

        try {
            if (order.getCustomerInfo().isSendMailFlag()) {
                String subject = "[BooCorners] Thank you for your purchase";

                StringBuilder productsHtml = new StringBuilder();
                for (CartItem item : order.getCart().getItems()) {
                    Product product = item.getProduct();
                    productsHtml.append("<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">")
                            .append("<div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">")
                            .append("<div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">")
//
//                            // Image Column
//                            .append("<div class=\"u-col u-col-37p17\" style=\"max-width: 320px;min-width: 223.02px;display: table-cell;vertical-align: top;\">")
//                            .append("<div style=\"background-color: #ffffff;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">")
//                            .append("<div style=\"box-sizing: border-box; height: 100%; padding: 0px;border: 0px solid transparent;\">")
//                            .append("<table id=\"u_content_image_3\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">")
//                            .append("<tbody><tr><td class=\"v-container-padding-padding\" style=\"padding: 10px 0px 0px;\" align=\"left\">")
//                            .append("<img align=\"right\" border=\"0\" src=\"").append(product.getImageName()).append("\" alt=\"").append(product.getProductName()).append("\" title=\"").append(product.getProductName()).append("\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 100%;max-width: 172px;\" width=\"172\"/>")
//                            .append("</td></tr></tbody></table>")
//                            .append("</div></div></div>")

                            // Product Information Column
                            .append("<div class=\"u-col u-col-62p83\" style=\"max-width: 320px;min-width: 376.98px;display: table-cell;vertical-align: top;\">")
                            .append("<div style=\"background-color: #ffffff;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">")
                            .append("<div style=\"box-sizing: border-box; height: 100%; padding: 0px;border: 0px solid transparent;\">")

                            // Product Name
                            .append("<table id=\"u_content_heading_4\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">")
                            .append("<tbody><tr><td class=\"v-container-padding-padding\" style=\"padding: 15px 10px 10px 20px;\" align=\"left\">")
                            .append("<h1 style=\"margin: 0px; line-height: 110%; text-align: left; word-wrap: break-word; font-size: 20px; font-weight: 700;\">").append(product.getProductName()).append("</h1>")
                            .append("</td></tr></tbody></table>")

                            // Product Description
                            .append("<table id=\"u_content_text_2\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">")
                            .append("<tbody><tr><td class=\"v-container-padding-padding\" style=\"padding: 0px 100px 10px 20px;\" align=\"left\">")
                            .append("<div style=\"font-size: 14px; line-height: 130%; text-align: left; word-wrap: break-word;\">")
                            .append("<p>").append(product.getDescription()).append("</p>")
                            .append("</div></td></tr></tbody></table>")

                            // Quantity
                            .append("<table id=\"u_content_text_3\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">")
                            .append("<tbody><tr><td class=\"v-container-padding-padding\" style=\"padding: 10px 10px 10px 20px;\" align=\"left\">")
                            .append("<div style=\"font-size: 14px; line-height: 20%; text-align: left; word-wrap: break-word;\">")
                            .append("<p>Quantity - ").append(item.getQuantity()).append("</p>")
                            .append("</div></td></tr></tbody></table>")

                            // Price
                            .append("<table id=\"u_content_heading_5\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">")
                            .append("<tbody><tr><td class=\"v-container-padding-padding\" style=\"padding: 10px 10px 10px 20px;\" align=\"left\">")
                            .append("<h1 style=\"margin: 0px; color: #d0a679; line-height: 110%; text-align: left; word-wrap: break-word; font-size: 20px; font-weight: 700;\">")
                            .append(product.getPrice()).append(" VND</h1>")
                            .append("</td></tr></tbody></table>")

                            // Divider
                            .append("<table id=\"u_content_divider_4\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">")
                            .append("<tbody><tr><td class=\"v-container-padding-padding\" style=\"padding: 0px 50px 10px 20px;\" align=\"left\">")
                            .append("<table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-top: 1px solid #d8d8d8;\">")
                            .append("<tbody><tr style=\"vertical-align: top\"><td style=\"font-size: 0px;line-height: 0px;\">&nbsp;</td></tr></tbody>")
                            .append("</table></td></tr></tbody></table>")

                            .append("</div></div></div>") // End of product info column

                            .append("</div></div></div>"); // End of row container
                }

                String body = mailSenderService.buildEmailContent(order.getOrderTrackingNumber(), DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                        .format(order.getDateCreated().plusDays(7)), productsHtml);

                mailSenderService.sendMail(recipient, subject, body);

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new BaseResponse<>(200, "Email sent successfully.", null, null));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(200, "This order dont need to send mail", null, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse<>(500, e.getMessage(), null, null));
        }
    }
}
