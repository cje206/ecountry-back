package com.growup.ecountry.repository;

import com.growup.ecountry.entity.JobDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface JobDetailRepository extends JpaRepository<JobDetails, Long> {

    //직업아이디로 직업부가기능 가져오기
    List<JobDetails> findByJobId(Long jobId);
}
