package com.growup.ecountry.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Taxes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer division;

    @Column(nullable = false)
    private Double tax;

    @ManyToOne
    @JoinColumn(name = "country_id", insertable = false, updatable = false)
    private Countries countries;

    @Column(name = "country_id")
    private Long countryId;

    public Taxes(Long countryId){
        this.countryId = countryId;
    }
}
