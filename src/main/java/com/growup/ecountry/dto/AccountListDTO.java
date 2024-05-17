package com.growup.ecountry.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountListDTO {
    private Long id;
    private Boolean division;
    private String name;
    private Double interest;
    private Integer dueDate;
    private Boolean available;
    private Long countryId;
}
