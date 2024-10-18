package com.haile.exe101.depairapplication.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String phoneNumber;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String phoneNumber, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.roles = roles;
    }
}
