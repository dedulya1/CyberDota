package com.project.opendota.service;

import com.project.opendota.entity.Player;
import com.project.opendota.entity.Team;
import com.project.opendota.repository.PlayerRepository;
import com.project.opendota.repository.TeamRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }
    public Optional<Team> findTeamById(Long teamId) {
        Optional<Team> team = teamRepository.findById(teamId);
        return team;
    }

    public void deleteTeamById(Long teamId) {
        Team team = teamRepository.findById(teamId).orElse(null);
        for (Player player : team.getPlayers()) {
            player.setTeam(null);
        }
        playerRepository.saveAll(team.getPlayers());
        teamRepository.deleteById(teamId);

    }
    public void updateTeam(Long teamId, Team teamDetails) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (teamOptional.isPresent()) {
            Team team = teamOptional.get();
            team.setName(teamDetails.getName());
            teamRepository.save(team);
        }
    }

    public void addPlayer(Long playerId, Long teamId) {
    Team team = teamRepository.findById(teamId).orElse(null);
    Player player = playerRepository.findById(playerId).orElse(null);
    team.addPlayer(player);
    player.setTeam(team);
    teamRepository.save(team);
    }

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public void removePlayerFromTeam(Long playerId, Long teamId) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (teamOptional.isPresent()) {
            Player player = playerRepository.findById(playerId).orElse(null);
            player.setTeam(null);
            playerRepository.save(player);
        }
    }

    public void addPlayerToTeam(Long teamId, Long playerId) {
        Team team = teamRepository.findById(teamId).orElse(null);
        if (team != null) {
            Player player = playerRepository.findById(playerId).orElse(null);
            if (player != null) {
                player.setTeam(team);
                team.addPlayer(player);
                playerRepository.save(player);
            }
        }
    }
}
