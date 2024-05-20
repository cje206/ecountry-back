package com.growup.ecountry.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer rowNum;

    @Column(nullable = false)
    private Integer colNum;

    //자리 실 소유주
    private Long ownerId;

    @ManyToOne
    @JoinColumn(name = "student_id", insertable=false, updatable=false)
    private Students students;

    //현재 사용자
    @Column(name = "student_id")
    private Long studentId;

    @ManyToOne
    @JoinColumn(name = "country_id", insertable=false, updatable=false)
    private Countries countries;

    @Column(name = "country_id")
    private Long countryId;

    public SeatStatus(Long studentId,Long countryId) {
        this.studentId = studentId;
        this.countryId = countryId;
    }
}
