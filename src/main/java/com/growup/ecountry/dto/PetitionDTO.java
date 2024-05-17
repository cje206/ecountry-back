package com.growup.ecountry.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class PetitionDTO {
    private Long id;
    private String title;
    private String content;
    private Timestamp createdAt;
    private Boolean isSecret;
    private Long countryId;
    private Long writerId;
    private String writerName;

}
