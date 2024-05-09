package com.growup.ecountry.entity;

import jakarta.persistence.*;

@Entity
public class Rules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String rule;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Countries countries;
}
