package com.growup.ecountry.repository;

import com.growup.ecountry.entity.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Jobs, Long> {
    //국가아이디로 직업리스트 가져오기
    List<Jobs> findByCountryId(Long countryId);
    Optional<Jobs> findById(Long id);
}



