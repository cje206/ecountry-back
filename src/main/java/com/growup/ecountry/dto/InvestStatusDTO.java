package com.growup.ecountry.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class InvestStatusDTO {
    private Long id;
    private Double status;
    private Timestamp createdAt;
    private Long investId;
}
