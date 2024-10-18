package com.haile.exe101.depairapplication.services.interfaces;

import com.haile.exe101.depairapplication.models.entity.Cart;
import com.haile.exe101.depairapplication.models.request.BatchCartItemRequest;
import com.haile.exe101.depairapplication.models.request.CartItemRequest;
import com.haile.exe101.depairapplication.models.request.RemoveCartItemRequest;
import com.haile.exe101.depairapplication.models.response.BaseResponse;

public interface ICartService {
    BaseResponse<Cart> addProductToCart(CartItemRequest cartItemRequest);

    BaseResponse<Cart> addBatchProductToCart(BatchCartItemRequest batchCartItemRequest);

    BaseResponse<Cart> removeProductFromCart(RemoveCartItemRequest removeCartItemRequest);

    BaseResponse<Cart> findCartById(Long id);
}
