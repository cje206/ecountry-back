package com.growup.ecountry.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Students {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer rollNumber;

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
