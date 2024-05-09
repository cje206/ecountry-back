package com.growup.ecountry.repository;

import com.growup.ecountry.entity.Rules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleRepository extends JpaRepository<Rules, Long> {

    //국가아이디로 규칙리스트 가져오기
    List<Rules> findByCountryId(Long countryId);
}
