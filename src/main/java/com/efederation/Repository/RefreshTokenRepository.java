package com.efederation.Repository;

import com.efederation.Model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query("SELECT r from RefreshToken r WHERE r.token = :token")
    RefreshToken findByToken(@Param("token") String token);

}
