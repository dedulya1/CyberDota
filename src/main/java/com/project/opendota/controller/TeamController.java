package com.project.opendota.controller;

import com.project.opendota.entity.Match;
import com.project.opendota.service.TeamService;
import com.project.opendota.entity.Team;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }
    @PostMapping
    public Team createTeam(@RequestBody @Validated Team team) {
        return teamService.createTeam(team);
    }
    @GetMapping("/{id}")
    public Optional<Team> getTeam (@PathVariable("id") Long teamId) {
        return teamService.findTeamById(teamId);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam (@PathVariable("id") Long teamId) {
        teamService.deleteTeamById(teamId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTeam(@PathVariable("id") Long teamId, @RequestBody Team team) {
        teamService.updateTeam(teamId, team);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{teamId}/matches/{playerId}")
    public ResponseEntity<Void> addPlayer(@PathVariable Long teamId, @PathVariable Long playerId) {
        teamService.addPlayer(playerId, teamId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{teamId}/matches/{matchId}")
    public ResponseEntity<Void> removePlayerFromTeam(@PathVariable Long playerId, @PathVariable Long teamId) {
        teamService.removePlayerFromTeam( playerId, teamId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{teamId}/players/{playerId}")
    public ResponseEntity<Void> addPlayerToTeam(@PathVariable Long teamId, @PathVariable Long playerId) {
        teamService.addPlayerToTeam(teamId, playerId);
        return ResponseEntity.ok().build();
    }
}
