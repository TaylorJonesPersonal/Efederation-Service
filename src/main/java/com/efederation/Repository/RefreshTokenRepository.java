package com.efederation.Repository;

import com.efederation.Model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query("SELECT r from RefreshToken r WHERE r.token = :token AND r.expirationTime > :exp")
    RefreshToken findByTokenAndExpirationTimeAfter(@Param("token") String token, @Param("exp") OffsetDateTime exp);

}
