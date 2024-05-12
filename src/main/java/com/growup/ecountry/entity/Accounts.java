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
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer balance;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Long createdAt;

    @ManyToOne
    @JoinColumn(name = "student_id", insertable=false, updatable=false)
    private Students students;

    @Column(name = "student_id")
    private Long studentId;

    @ManyToOne
    @JoinColumn(name = "account_list_id", insertable=false, updatable=false)
    private AccountLists accountLists;

    @Column(name = "account_list_id")
    private Long accountListId;

    public Accounts(Long studentId, Long accountListId) {
        this.studentId = studentId;
        this.accountListId = accountListId;
    }
}
