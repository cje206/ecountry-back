package com.growup.ecountry.repository;

import com.growup.ecountry.entity.SeatStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatStatusRepository extends JpaRepository<SeatStatus, Long> {
    Optional<SeatStatus> findByCountryId(Long countryId);
    List<SeatStatus> findAllByCountryId(Long countryId);
    @Transactional //delete
    void deleteAllByCountryId(Long countryId);
}
