package com.growup.ecountry.repository;

import com.growup.ecountry.entity.Countries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Countries, Long> {
    Optional<Countries> findById(Long countryId);
    List<Countries> findAllByUserId(Long userId);
    @Query("select c from Countries c where c.id = :id and c.users.id = :userId")
    Optional<Countries> findByIdANDUserId(@Param("id") Long id, @Param("userId") Long userId);
}
