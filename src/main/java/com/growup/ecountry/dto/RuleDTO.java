package com.growup.ecountry.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RuleDTO {
    private Long id;
    private String rule;
    private Long countryId;

}
