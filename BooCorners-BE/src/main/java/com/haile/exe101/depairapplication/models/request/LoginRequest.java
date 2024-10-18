package com.haile.exe101.depairapplication.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "Phone Number is mandatory")
    @Size(max = 20, message = "Phone Number is not valid")
    @Pattern(regexp = "^(0|84)(2(0[3-9]|1[0-689]|2[0-25-9]|3[2-9]|4[0-9]|5[124-9]|6[0369]|7[0-7]|8[0-9]|9[012346789])|3[2-9]|5[25689]|7[06-9]|8[0-9]|9[012346789])([0-9]{7})$", message = "Phone Number is not valid")
    private String phoneNumber;

    @NotBlank(message = "Password is mandatory")
    private String password;
}
