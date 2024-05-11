package com.growup.ecountry.repository;

import com.growup.ecountry.entity.Seats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seats, Long> {
    List<Seats> findByCountryId(Long countryId);
}
