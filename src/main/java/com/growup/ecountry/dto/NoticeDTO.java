package com.growup.ecountry.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class NoticeDTO {
    private Long id;
    private String content;
    private Boolean isChecked;
    private Date createdAt;
    private List<Long> studentId;
}
