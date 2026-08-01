package com.mobilemart.backend.repository;

import com.mobilemart.backend.entity.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {
    
    Optional<JwtToken> findByToken(String token);
    
    @Transactional
    void deleteByUserId(Long userId);

    @Transactional
    void deleteByToken(String token);
}
