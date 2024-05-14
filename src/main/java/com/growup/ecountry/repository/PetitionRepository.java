package com.growup.ecountry.repository;

import com.growup.ecountry.entity.Petitions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetitionRepository extends JpaRepository<Petitions, Long> {
    List<Petitions> findByCountryId(Long countryId);
}
