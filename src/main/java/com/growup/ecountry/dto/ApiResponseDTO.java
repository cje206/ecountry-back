package com.growup.ecountry.dto;

import lombok.*;

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
}