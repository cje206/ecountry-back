package com.growup.ecountry.repository;

import com.growup.ecountry.entity.Invests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestRepository extends JpaRepository<Invests, Long> {
    List<Invests> findByCountryId(Long countryId);
}
