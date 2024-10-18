package com.haile.exe101.depairapplication.models.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BatchCartItemRequest {
    @NotNull
    private Long cartId; // ID của giỏ hàng, nếu chưa có thì có thể bỏ qua hoặc truyền null

    @Size(min = 1, max = 20)
    private List<BatchCartItem> items; // Danh sách các sản phẩm được chọn và số lượng
}
