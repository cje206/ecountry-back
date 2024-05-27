package com.growup.ecountry.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Jobs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "integer default 1")
    private Integer limited;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String roll;

    @Column(nullable = false)
    private String standard;

    @Column(nullable = false)
    private Integer salary;

    @Column(nullable = true)
    private Integer[] skills;

    @Column
    private String jobImg;

    @ManyToOne
    @JoinColumn(name = "country_id", insertable = false, updatable = false)
    private Countries countries;

    @Column(name = "country_id")
    private Long countryId;

    @Builder.Default
    @OneToMany(mappedBy = "jobs")
    private List<Students> students = new ArrayList<>();

    public Jobs(Long countryId){
        this.countryId = countryId;
    }
}
