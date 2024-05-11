package com.growup.ecountry.repository;

import com.growup.ecountry.entity.AccountLists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountListRepository extends JpaRepository<AccountLists, Long> {
    List<AccountLists> findByCountryIdAndDivisionAndAvailable(Long countryId, boolean division, boolean available);
}
