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
public class Invests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String unit;

    @Column
    private String info;

    @Builder.Default
    @OneToMany(mappedBy = "invests", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvestStatus> investStatus = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "country_id", insertable=false, updatable=false)
    private Countries countries;

    @Column(name = "country_id")
    private Long countryId;

    public Invests(Long countryId) {
        this.countryId = countryId;
    }
}
