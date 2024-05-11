package com.growup.ecountry.repository;

import com.growup.ecountry.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUserIdAndPw(String userId, String pw);
    Optional<Users> findById(Long id);
    Optional<Users> findByName(String name);
    Optional<Users> findByUserId(String userId);
    Optional<Users> findByPw(String pw);
}
