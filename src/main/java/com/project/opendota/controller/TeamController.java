package com.project.opendota.controller;

import com.project.opendota.entity.Player;
import com.project.opendota.entity.Team;
import com.project.opendota.service.TeamService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
  private final TeamService teamService;
  private static final String CREATE_SUCCESS_MESSAGE = "Created successfully!";
  private static final String DELETE_SUCCESS_MESSAGE = "Deleted successfully!";
  private static final String UPDATE_SUCCESS_MESSAGE = "Updated successfully!";

  public TeamController(TeamService teamService) {
    this.teamService = teamService;
  }

  @PostMapping
  public ResponseEntity<String> createTeam(@RequestBody @Validated Team team) {
    teamService.createTeam(team);
    return new ResponseEntity<>(CREATE_SUCCESS_MESSAGE, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Team> getTeam(@PathVariable Long id) {
    return new ResponseEntity<>(teamService.findTeamById(id), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteTeam(@PathVariable("id") Long teamId) {
    teamService.deleteTeamById(teamId);
    return new ResponseEntity<>(DELETE_SUCCESS_MESSAGE, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateTeam(@PathVariable("id") Long teamId,
                                           @RequestBody Team team) {
    teamService.updateTeam(teamId, team);
    return new ResponseEntity<>(UPDATE_SUCCESS_MESSAGE, HttpStatus.OK);
  }

  @PostMapping("/{teamId}/matches/{playerId}")
  public ResponseEntity<String> addPlayer(@PathVariable Long teamId, @PathVariable Long playerId) {
    teamService.addPlayer(playerId, teamId);
    return new ResponseEntity<>(UPDATE_SUCCESS_MESSAGE, HttpStatus.OK);
  }

  @DeleteMapping("/{teamId}/matches/{playerId}")
  public ResponseEntity<String> removePlayerFromTeam(@PathVariable Long playerId,
                                                     @PathVariable Long teamId) {
    teamService.removePlayerFromTeam(playerId, teamId);
    return new ResponseEntity<>(UPDATE_SUCCESS_MESSAGE, HttpStatus.OK);
  }

  @PostMapping("/{teamId}/players/{playerId}")
  public ResponseEntity<String> addPlayerToTeam(@PathVariable Long teamId,
                                                @PathVariable Long playerId) {
    teamService.addPlayerToTeam(teamId, playerId);
    return new ResponseEntity<>(UPDATE_SUCCESS_MESSAGE, HttpStatus.OK);
  }

  @GetMapping("/{teamId}/players")
  public ResponseEntity<List<Player>> findPlayersInTeamWithRating(@PathVariable Long teamId,
                                                                  @RequestParam Integer min,
                                                                  @RequestParam Integer max) {
    return new ResponseEntity<>(teamService.findPlayersInTeamWithRating(teamId, min, max),
        HttpStatus.OK);
  }
}
