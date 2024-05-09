package com.growup.ecountry.entity;

import jakarta.persistence.*;

@Entity
public class Students {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer roll_number;

    @Column(nullable = false)
    private String pw;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private String img;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Countries countries;
}
