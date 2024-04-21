package com.project.opendota.repository;

import com.project.opendota.entity.Player;
import com.project.opendota.entity.Team;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
  @Query("SELECT p FROM Player p JOIN p.team t WHERE t.teamId = :teamId "
      + "AND (p.rating BETWEEN :min AND :max)")
  List<Player> findPlayersInTeamWithRating(Long teamId, Integer min, Integer max);
}
