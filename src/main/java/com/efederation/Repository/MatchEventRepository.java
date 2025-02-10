package com.efederation.Repository;

import com.efederation.Model.MatchEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchEventRepository extends JpaRepository<MatchEvent, Long> {
}
