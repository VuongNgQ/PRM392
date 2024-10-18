package com.haile.exe101.depairapplication.models.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BatchCartItem {
    @NotNull
    private Long productId;

    @Min(value = 0)
    @Max(value = 99)
    private Integer quantity; // Số lượng của sản phẩm
}
