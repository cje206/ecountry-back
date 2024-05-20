package com.growup.ecountry.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InvestDTO {
    private Long id;
    private String name;
    private String unit;
    private String info;
    private Long countryId;
}
