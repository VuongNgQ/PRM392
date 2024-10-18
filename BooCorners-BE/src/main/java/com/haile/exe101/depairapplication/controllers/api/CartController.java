package com.haile.exe101.depairapplication.controllers.api;

import com.haile.exe101.depairapplication.models.entity.Cart;
import com.haile.exe101.depairapplication.models.request.BatchCartItemRequest;
import com.haile.exe101.depairapplication.models.request.CartItemRequest;
import com.haile.exe101.depairapplication.models.request.RemoveCartItemRequest;
import com.haile.exe101.depairapplication.models.response.BaseResponse;
import com.haile.exe101.depairapplication.services.interfaces.ICartService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    @Operation(summary = "Add single product to cart",
            description = "By requesting a single product id and cart id to add data and price calculation")
    @PostMapping
    public ResponseEntity<BaseResponse<Cart>> addProductToCart(@RequestBody @Valid CartItemRequest cartItemRequest) {
        return ResponseEntity.ok(cartService.addProductToCart(cartItemRequest));
    }

    @Operation(summary = "Add all products to cart",
            description = "By request to a list product id and a cart id to add data and price calculation")
    @PostMapping("/batch")
    public ResponseEntity<BaseResponse<Cart>> addBatchProductToCart(@RequestBody @Valid BatchCartItemRequest cartItemRequest) {
        return ResponseEntity.ok(cartService.addBatchProductToCart(cartItemRequest));
    }

    // API để xóa sản phẩm khỏi giỏ hàng
    @Operation(summary = "Remove product from cart",
            description = "By request to a product id and a cart id to remove data")
    @DeleteMapping
    public ResponseEntity<BaseResponse<Cart>> removeProductFromCart(@RequestBody @Valid RemoveCartItemRequest removeCartItemRequest) {
        try {
            return ResponseEntity.ok(cartService.removeProductFromCart(removeCartItemRequest));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(500, "Failed to find cart or product", null, null));
        }
    }
}
