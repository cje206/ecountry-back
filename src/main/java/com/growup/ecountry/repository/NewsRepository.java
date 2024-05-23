package com.growup.ecountry.repository;

import com.growup.ecountry.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByCountryIdOrderByIdDesc(Long countryId);
}
