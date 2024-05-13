package com.growup.ecountry.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, columnDefinition = "tinyint default 0")
    private Boolean isChecked;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Long createdAt;

    @ManyToOne
    @JoinColumn(name = "student_id", insertable=false, updatable=false)
    private Students students;

    @Column(name = "student_id")
    private Long studentId;

    public Notice(Long studentId) {
        this.studentId = studentId;
    }
}
