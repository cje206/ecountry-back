package com.growup.ecountry.repository;

import com.growup.ecountry.entity.JobDetails;
import com.growup.ecountry.entity.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;

public interface JobRepository extends JpaRepository<Jobs, Long> {

    //국가아이디로 직업리스트 가져오기
    List<Jobs> findByCountryId(Long countryId);
}



