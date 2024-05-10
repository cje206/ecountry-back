package com.growup.ecountry.repository;

import com.growup.ecountry.entity.Countries;
import com.growup.ecountry.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Countries, Long> {
    Optional<Countries> findById(Long countryId);
    List<Countries> findAllByUsers_Id(Long userId);
}
