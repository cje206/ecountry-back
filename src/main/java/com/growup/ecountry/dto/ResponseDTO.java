package com.growup.ecountry.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDTO {
    private Boolean success;
    private String message;
}
// 프론트에 반환될 DTO 타입
