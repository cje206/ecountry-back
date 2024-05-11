package com.growup.ecountry.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class JobDTO {
    private Long id;
    private Integer limited;
    private String name;
    private String roll;
    private String standard;
    private Integer salary;
    private Long countryId;
}
