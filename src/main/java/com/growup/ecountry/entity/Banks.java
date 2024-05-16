package com.growup.ecountry.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Banks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer transaction;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column
    private String memo;

//    0일 경우 과태료 x / 과태료인 경우 countryId
    @Column(nullable = false, updatable = true, insertable = true, columnDefinition = "BIGINT default 0")
    private Long isPenalty;

    @ManyToOne
    @JoinColumn(name = "deposit_id", insertable=false, updatable=false)
    private Accounts accounts1;

    @Column(name = "deposit_id")
    private Long depositId;

    @ManyToOne
    @JoinColumn(name = "withdraw_id", insertable=false, updatable=false)
    private Accounts accounts2;

    @Column(name = "withdraw_id")
    private Long withdrawId;

    public Banks(Long depositId, Long withdrawId) {
        this.depositId = depositId;
        this.withdrawId = withdrawId;
    }
}
