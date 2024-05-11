package com.growup.ecountry.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer skill;

    @ManyToOne
    @JoinColumn(name = "job_id", insertable = false, updatable = false)
    private Jobs jobs;

    @Column(name = "job_id")
    private Long jobId;

    public JobDetails(Long jobId){
        this.jobId = jobId;
    }

}
