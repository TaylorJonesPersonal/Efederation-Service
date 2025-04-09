package com.efederation.Repository;

import com.efederation.Model.Memory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoryRepository extends JpaRepository<Memory, Long> {
    List<Memory> findAllByMatchId(long matchId);
}
