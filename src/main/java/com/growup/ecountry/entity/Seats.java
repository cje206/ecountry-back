package com.growup.ecountry.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer rowNum;

    @Column(nullable = false)
    private Integer colNum;

    @ManyToOne
    @JoinColumn(name = "country_id", insertable=false, updatable=false)
    private Countries countries;

    @Column(name = "country_id")
    private Long countryId;

    public Seats(Long countryId) {
        this.countryId = countryId;
    }
}
