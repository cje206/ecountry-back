package com.growup.ecountry.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatStatusDTO {
    private long id;
    private Integer rowNum;
    private Integer colNum;
    private Long ownerId;
    private Long studentId;
    private Long countryId;
}
