package com.haile.exe101.depairapplication.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "^(0|84)(2(0[3-9]|1[0-689]|2[0-25-9]|3[2-9]|4[0-9]|5[124-9]|6[0369]|7[0-7]|8[0-9]|9[012346789])|3[2-9]|5[25689]|7[06-9]|8[0-9]|9[012346789])([0-9]{7})$", message = "Invalid phone number")
    private String phoneNumber;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 20, message = "Password must be in 6 chars - 20 chars long")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;
}