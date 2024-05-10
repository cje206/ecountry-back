package com.growup.ecountry.repository;

import com.growup.ecountry.entity.InvestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestStatusRepository extends JpaRepository<InvestStatus, Long> {
    List<InvestStatus> findByInvestId(Long investId);
}
