package com.growup.ecountry.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountLists {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TINYINT(1) default 1", nullable = false)
    private Boolean division;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double interest;

    @Column(nullable = false)
    private Integer dueDate;

    @Column(columnDefinition = "TINYINT(1) default 1", nullable = false)
    private Boolean available;

    @ManyToOne
    @JoinColumn(name = "country_id", insertable=false, updatable=false)
    private Countries countries;

    @Column(name = "country_id")
    private Long countryId;

    public AccountLists(Long countryId) {
        this.countryId = countryId;
    }
}
