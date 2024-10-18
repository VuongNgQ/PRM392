package com.haile.exe101.depairapplication.models.response;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class ErrorResponse {
    private int code;
    private String message;
    private Map<String, String> errors;
}
