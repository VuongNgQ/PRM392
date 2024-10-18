package com.haile.exe101.depairapplication.models.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfoRequest {
    @NotBlank(message = "Full Name is mandatory")
    private String fullName;
    @Email(message = "Invalid email")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Phone number is mandatory")
    @Size(max = 20)
    @Pattern(regexp = "^(0|84)(2(0[3-9]|1[0-689]|2[0-25-9]|3[2-9]|4[0-9]|5[124-9]|6[0369]|7[0-7]|8[0-9]|9[012346789])|3[2-9]|5[25689]|7[06-9]|8[0-9]|9[012346789])([0-9]{7})$", message = "Invalid phone number")
    private String phoneNumber;

    @NotBlank(message = "Address is mandatory")
    private String shippingAddress;

    @NotNull(message = "Send mail required true or false")
    private boolean sendMailFlag;
}
