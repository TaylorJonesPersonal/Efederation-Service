package com.efederation.Repository;

import com.efederation.Model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface MatchRepository extends JpaRepository<Match, String> {
    @Query(value = "select match_id, winner, created_at, npc_participants_npc_id, condition" +
            " from match m inner join match_human_participants mhp on m.match_id = mhp.match_match_id" +
            " inner join match_npc_participants mnp on m.match_id = mnp.match_match_id" +
            " WHERE mhp.human_participants_wrestler_id = :id", nativeQuery = true)
    List<Map<String, Object>> getMatchesByWrestlerId(@Param("id") int id);

}
