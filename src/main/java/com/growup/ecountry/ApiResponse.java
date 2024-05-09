package com.growup.ecountry;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ApiResponse<T> {
    private Boolean success;
    private String message ;
    private List<T> result = new ArrayList<>();
}

