package com.haile.exe101.depairapplication.handlers;

import com.haile.exe101.depairapplication.models.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // Nếu validate fail thì trả về 400
    public ErrorResponse handleBindException(BindException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        Map<String, String> errorList = new HashMap<>();
        if (e.getBindingResult().hasErrors()) {
            errorResponse.setCode(400);
            List<FieldError> errors = e.getBindingResult().getFieldErrors();
            for (FieldError error : errors ) {
                errorList.put(error.getField(), error.getDefaultMessage());
            }
            errorResponse.setErrors(errorList);
            errorResponse.setMessage("Request không hợp lệ");
        }
        return errorResponse;
    }
}
