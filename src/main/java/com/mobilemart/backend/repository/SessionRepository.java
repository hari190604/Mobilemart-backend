package com.mobilemart.backend.repository;

import com.mobilemart.backend.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    
    Optional<Session> findByJwtToken(String jwtToken);
    
    Optional<Session> findByUserIdAndActiveStatusTrue(Long userId);
}
