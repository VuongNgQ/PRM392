package com.haile.exe101.depairapplication.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class BaseResponse<T> {
    private int code;
    private String message;
    private Collection<T> lists;
    private T details;
}
