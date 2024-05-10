package com.growup.ecountry.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvestStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double status;

    @Column(name = "registered",nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "invest_id", insertable=false, updatable=false)
    private Invests invests;

    @Column(name = "invest_id")
    private Long investId;

    public InvestStatus(Long investId) {
        this.investId = investId;
    }
}
