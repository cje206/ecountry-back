package com.growup.ecountry.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SeatStatusDTO {
    private Integer rowNum;
    private Integer colNum;
    private Long ownerId;
    private Long studentId;
}
