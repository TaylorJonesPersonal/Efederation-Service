package com.efederation.Repository;

import com.efederation.Model.NPC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NPCRepository extends JpaRepository<NPC, Long> {
}
