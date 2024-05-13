package com.growup.ecountry.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NoticeDTO {
    private Long id;
    private String content;
    private Boolean isChecked;
    private Long createdAt;
    private Long studentId;
}
