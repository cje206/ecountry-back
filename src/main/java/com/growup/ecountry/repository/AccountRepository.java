package com.growup.ecountry.repository;

import com.growup.ecountry.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, Long> {
    List<Accounts> findByAccountListId(Long accountListId);
    List<Accounts> findByStudentId(Long studentId);
}
