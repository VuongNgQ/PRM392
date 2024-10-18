package com.haile.exe101.depairapplication.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RemoveCartItemRequest {
    private Long cartId;   // ID của giỏ hàng
    private Long productId; // ID của sản phẩm cần xóa
}
