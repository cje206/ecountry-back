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
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer balance;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "accounts1")
    private List<Banks> banks1 = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "accounts2")
    private List<Banks> banks2 = new ArrayList<>();

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
