package com.growup.ecountry.repository;

import com.growup.ecountry.entity.Banks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Banks, Long> {
    List<Banks> findByDepositIdOrWithdrawIdOrderByIdDesc (Long depositId, Long withdrawId);
    List<Banks> findByIsPenalty(Long penalty);
}
