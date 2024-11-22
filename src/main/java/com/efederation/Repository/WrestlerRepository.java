package com.efederation.Repository;

import com.efederation.Model.Wrestler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WrestlerRepository extends JpaRepository<Wrestler, Long> {

}
