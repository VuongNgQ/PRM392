package com.haile.exe101.depairapplication.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreatePaymentLinkRequestBody {
    @NotNull(message = "Cart ID is required")
    private Long cartId;
    @NotNull(message = "Customer Info ID is required")
    private Long customerInfoId;
    @NotNull(message = "Description is required")
    private String description;
    @NotBlank(message = "Return URL is required")
    private String returnUrl;
    @NotBlank(message = "Cancel URL is required")
    private String cancelUrl;
}
