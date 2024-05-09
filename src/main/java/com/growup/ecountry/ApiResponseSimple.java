package com.growup.ecountry;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseSimple {
        private Boolean success;
        private String message ;
}
