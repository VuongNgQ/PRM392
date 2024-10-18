package com.haile.exe101.depairapplication.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@AllArgsConstructor
public class BasePageResponse<T> {
    private int code;
    private String message;
    private Page<T> lists;
    private T details;
}
