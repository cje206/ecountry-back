package com.growup.ecountry.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class AccountDTO {
    private Long id;
    private Integer balance;
    private Timestamp createdAt;
    private Long studentId;
    private Long accountListId;
    private String name;
}
