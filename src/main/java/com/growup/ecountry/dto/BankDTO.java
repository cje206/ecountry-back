package com.growup.ecountry.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class BankDTO {
    private Long id;
    private Integer transaction;
    private Timestamp createdAt;
    private String memo;
    private Long isPenalty;
    private Long depositId;
    private Long withdrawId;
    private String depositName;
    private String withdrawName;
}
