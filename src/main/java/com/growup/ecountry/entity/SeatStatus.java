package com.growup.ecountry.entity;

import jakarta.persistence.*;
import lombok.*;

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
    @Column(nullable = false)
    private Long ownerId;

    @ManyToOne
    @JoinColumn(name = "student_id", insertable=false, updatable=false)
    private Countries countries;

    //현재 사용자
    @Column(name = "student_id")
    private Long studentId;

    public SeatStatus(Long studentId) {
        this.studentId = studentId;
    }
}
