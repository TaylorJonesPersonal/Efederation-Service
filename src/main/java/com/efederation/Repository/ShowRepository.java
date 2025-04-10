package com.efederation.Repository;

import com.efederation.Model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
    @Query(value = "select id, name, importance, default_image, logo_image from wrestler_show ws inner join show sh on ws.show_id = sh.id where ws.wrestler_id = :id", nativeQuery = true)
    List<Show> getShowsByWrestlerId(@Param("id") int id);
}

