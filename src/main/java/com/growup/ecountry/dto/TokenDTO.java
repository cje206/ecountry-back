package com.growup.ecountry.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TokenDTO {
    private Long id;
    private Boolean isStudent;
}
