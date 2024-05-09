package com.growup.ecountry.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String user_id;

    @Column(nullable = false)
    private String pw;

    private String img;

    @OneToMany(mappedBy = "users")
    private List<Countries> countries = new ArrayList<>();


}
