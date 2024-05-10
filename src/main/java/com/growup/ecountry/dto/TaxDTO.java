package com.growup.ecountry.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TaxDTO {
    private Long id;
    private String name;
    private Integer division;
    private Double tax;
    private Long countryId;
}
