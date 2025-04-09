package com.efederation.Repository;

import com.efederation.Model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface MatchRepository extends JpaRepository<Match, String> {
    @Query(value = "select wm.match_id, winner, created_at, npc_id, condition" +
            " from match m inner join wrestler_match wm on m.id = wm.match_id" +
            " inner join npc_match npcm on m.id = npcm.match_id" +
            " WHERE wm.wrestler_id = :id" +
            " ORDER BY created_at DESC", nativeQuery = true)
    List<Map<String, Object>> getMatchesByWrestlerId(@Param("id") int id);

}
