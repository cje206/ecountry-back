package com.growup.ecountry.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String userId;
    private String pw;
    private String img;
}
