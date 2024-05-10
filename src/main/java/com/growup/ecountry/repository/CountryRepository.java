package com.growup.ecountry.repository;

import com.growup.ecountry.entity.Countries;
import com.growup.ecountry.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Countries, Long> {

}
