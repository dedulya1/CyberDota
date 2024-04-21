package com.project.opendota.service;

import com.project.opendota.aop.annotation.Logging;
import com.project.opendota.aop.exception.ResourceNotFoundException;
import com.project.opendota.cache.CacheEntity;
import com.project.opendota.entity.Player;
import com.project.opendota.entity.Team;
import com.project.opendota.repository.PlayerRepository;
import com.project.opendota.repository.TeamRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Logging
@Service
public class TeamService {
  private final CacheEntity<Long, Team> cache;
  private final TeamRepository teamRepository;
  private final PlayerRepository playerRepository;

  public TeamService(CacheEntity<Long, Team> cache, TeamRepository teamRepository,
                     PlayerRepository playerRepository) {
    this.cache = cache;
    this.teamRepository = teamRepository;
    this.playerRepository = playerRepository;
  }

  public Team findTeamById(Long id) {
    Team team = cache.get(id);
    if (team == null) {
      team = teamRepository.findById(id).orElseThrow(
          () -> new ResourceNotFoundException("Player (with id = %d) not found".formatted(id)));
      cache.put(id, team);
    }
    return team;
  }

  public void deleteTeamById(Long id) {
    Team team = teamRepository.findById(id).orElse(null);
    if (team == null) {
      throw new ResourceNotFoundException("Team (with id = %d) not found".formatted(id));
    }
    cache.remove(id);
    if (team.getPlayers() != null) {
      for (Player player : team.getPlayers()) {
        player.setTeam(null);
      }
      playerRepository.saveAll(team.getPlayers());
    }
    teamRepository.deleteById(id);

  }

  public void updateTeam(Long teamId, Team teamDetails) {
    teamDetails.setTeamId(teamId);
    cache.remove(teamId);
    teamRepository.save(teamDetails);
  }

  public void addPlayer(Long playerId, Long teamId) {
    cache.remove(teamId);
    Team team = teamRepository.findById(teamId).orElseThrow(
        () -> new ResourceNotFoundException("Team (with id = %d) not found".formatted(teamId))
    );
    Player player = playerRepository.findById(playerId).orElseThrow(
        () -> new ResourceNotFoundException("Player (with id = %d) not found".formatted(playerId))
    );
    team.addPlayer(player);
    player.setTeam(team);
    teamRepository.save(team);
  }

  public void createTeam(Team team) {
    teamRepository.save(team);
  }

  public void removePlayerFromTeam(Long playerId, Long teamId) {
    cache.remove(teamId);
    if (!teamRepository.existsById(teamId)) {
      throw new ResourceNotFoundException("Team (with id = %d) not found".formatted(teamId));
    }
    Player player = playerRepository.findById(playerId).orElseThrow(
        () -> new ResourceNotFoundException("Player (with id = %d) not found".formatted(teamId))
    );
    player.setTeam(null);
    playerRepository.save(player);
  }

  public void addPlayerToTeam(Long teamId, Long playerId) {
    cache.remove(teamId);
    Team team = teamRepository.findById(teamId).orElseThrow(
        () -> new ResourceNotFoundException("Team (with id = %d) not found".formatted(teamId))
    );
    Player player = playerRepository.findById(playerId).orElseThrow(
        () -> new ResourceNotFoundException("Player (with id = %d) not found".formatted(playerId))
    );
    player.setTeam(team);
    team.addPlayer(player);
    playerRepository.save(player);
  }

  public List<Player> findPlayersInTeamWithRating(Long teamId, Integer min, Integer max) {
    return teamRepository.findPlayersInTeamWithRating(teamId, min, max);
  }
}
