package com.growup.ecountry.dto;

import lombok.*;

import javax.lang.model.type.NullType;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ApiResponseDTO<T> {
    private Boolean success;
    private String message;
    private T result;

    public ApiResponseDTO(Boolean success, String message) {
        this.success = success;
        this.message = message;
        this.result = null;
    }
}