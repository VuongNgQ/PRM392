package com.haile.exe101.depairapplication.services;

import com.haile.exe101.depairapplication.models.entity.Cart;
import com.haile.exe101.depairapplication.models.entity.CartItem;
import com.haile.exe101.depairapplication.models.entity.Product;
import com.haile.exe101.depairapplication.models.entity.Sale;
import com.haile.exe101.depairapplication.models.request.BatchCartItem;
import com.haile.exe101.depairapplication.models.request.BatchCartItemRequest;
import com.haile.exe101.depairapplication.models.request.CartItemRequest;
import com.haile.exe101.depairapplication.models.request.RemoveCartItemRequest;
import com.haile.exe101.depairapplication.models.response.BaseResponse;
import com.haile.exe101.depairapplication.repositories.CartItemRepository;
import com.haile.exe101.depairapplication.repositories.CartRepository;
import com.haile.exe101.depairapplication.repositories.ProductRepository;
import com.haile.exe101.depairapplication.services.interfaces.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CartService implements ICartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public BaseResponse<Cart> addProductToCart(CartItemRequest cartItemRequest) {
        Product product = productRepository.findById(cartItemRequest.getProductId())
                .orElse(null);

        if (product == null || product.getQuantity() <= 0) {
            return new BaseResponse<>(404, "Product is not found or out of stock", null, null);
        }

        // Lấy giỏ hàng hiện tại (nếu có)
        Cart cart = cartRepository.findById(cartItemRequest.getCartId())
                .orElse(new Cart());

        // Thêm sản phẩm vào giỏ hàng
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemRequest.getQuantity());
        cartItem.setCart(cart);

        cart.getItems().add(cartItem);

        // cap nhat tong gia gio hang
        BigDecimal totalPrice = cart.getItems().stream()
                .map(item -> calculateFinalPrice(item.getProduct(), item.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalPrice(totalPrice);

        // Lưu giỏ hàng sau khi cập nhật
        Cart newCart = cartRepository.save(cart);
        return new BaseResponse<>(200, "Added product to cart", null, newCart);
    }

    @Override
    public BaseResponse<Cart> addBatchProductToCart(BatchCartItemRequest batchCartItemRequest) {
        // Tìm giỏ hàng hoặc tạo mới nếu chưa có
        Cart cart;
        if (batchCartItemRequest.getCartId() != null) {
            cart = cartRepository.findById(batchCartItemRequest.getCartId())
                    .orElse(new Cart());
        } else {
            cart = new Cart();
        }

        // Duyệt qua danh sách các sản phẩm trong batch và thêm vào giỏ hàng
        for (BatchCartItem cartItemRequest : batchCartItemRequest.getItems()) {
            Product product = productRepository.findById(cartItemRequest.getProductId())
                    .orElse(null);

            if (product == null || product.getQuantity() <= 0) {
                return new BaseResponse<>(404, "Product " + cartItemRequest.getProductId() + " is not found or out of stock", null, null);
            }

            // Kiểm tra xem sản phẩm này đã có trong giỏ hàng chưa
            CartItem existingItem = cart.getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(product.getId()))
                    .findFirst().orElse(null);

            if (existingItem != null) {
                // Nếu sản phẩm đã có, tăng số lượng
                existingItem.setQuantity(cartItemRequest.getQuantity());
            } else {
                // Nếu sản phẩm chưa có trong giỏ hàng, thêm mới
                CartItem cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(cartItemRequest.getQuantity());
                cartItem.setCart(cart);
                cart.getItems().add(cartItem);
            }
        }

        // cap nhat tong gia gio hang
        BigDecimal totalPrice = cart.getItems().stream()
                .map(item -> calculateFinalPrice(item.getProduct(), item.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalPrice(totalPrice);

        // Lưu giỏ hàng sau khi cập nhật
        Cart newCart = cartRepository.save(cart);
        return new BaseResponse<>(200, "Added products to cart", null, newCart);
    }

    @Override
    public BaseResponse<Cart> removeProductFromCart(RemoveCartItemRequest removeCartItemRequest) {
        // Tìm giỏ hàng theo ID
        Cart cart = cartRepository.findById(removeCartItemRequest.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Tìm sản phẩm trong giỏ hàng
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(removeCartItemRequest.getProductId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        // Xóa sản phẩm khỏi giỏ hàng
        cart.getItems().remove(cartItem);
        cartItemRepository.delete(cartItem); // Xóa CartItem trong DB

        // Tính lại tổng giá của giỏ hàng nếu còn sản phẩm
        if (!cart.getItems().isEmpty()) {
            BigDecimal totalPrice = cart.getItems().stream()
                    .map(item -> BigDecimal.valueOf((long) item.getProduct().getPrice() * item.getQuantity()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            cart.setTotalPrice(totalPrice);
        } else {
            // Nếu giỏ hàng trống, đặt tổng giá về 0
            cart.setTotalPrice(BigDecimal.ZERO);
        }

        // Cập nhật giỏ hàng sau khi xóa
        Cart updatedCart = cartRepository.save(cart);

        return new BaseResponse<>(200, "Added products to cart", null, updatedCart);
    }

    @Override
    public BaseResponse<Cart> findCartById(Long id) {
        return new BaseResponse<>(200, "Get cart by id successfully", null, cartRepository.findById(id).orElse(null));
    }

    private BigDecimal calculateFinalPrice(Product product, int quantity) {
        Sale sale = product.getSales().stream()
                .filter(Sale::isActive) // Kiem tra con sales ko
                .findFirst()
                .orElse(null);

        BigDecimal productPrice = BigDecimal.valueOf(product.getPrice());
        BigDecimal finalPrice;

        if (sale != null) {
            // ap dung gia sale (nhan voi quantity
            BigDecimal discount = productPrice.multiply(BigDecimal.valueOf(sale.getDiscountPercentage()).divide(BigDecimal.valueOf(100), RoundingMode.UNNECESSARY));
            BigDecimal discountedPrice = productPrice.subtract(discount);
            finalPrice = discountedPrice.multiply(BigDecimal.valueOf(quantity));
        } else {
            // ap dung gia binh thuong
            finalPrice = productPrice.multiply(BigDecimal.valueOf(quantity));
        }

        return finalPrice;
    }
}
