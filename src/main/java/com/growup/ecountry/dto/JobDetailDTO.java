package com.growup.ecountry.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JobDetailDTO {
    private Long id;
    private Integer skill;
    private Long jobId;
}
