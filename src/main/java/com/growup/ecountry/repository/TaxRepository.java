package com.growup.ecountry.repository;

import com.growup.ecountry.entity.Taxes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxRepository extends JpaRepository<Taxes, Long> {
    //국가아이디로 세금리스트 가져오기
    List<Taxes> findByCountryId(Long countryId);
    List<Taxes> findByCountryIdAndDivision(Long countryId, Integer division);
}
