package com.project.opendota.controller;

import com.project.opendota.aop.exception.NotImplementedException;
import com.project.opendota.entity.Match;
import com.project.opendota.service.MatchService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/matches")
@RestController
public class MatchController {
  private static final String CREATE_SUCCESS_MESSAGE = "Created successfully!";
  private static final String DELETE_SUCCESS_MESSAGE = "Deleted successfully!";
  private static final String UPDATE_SUCCESS_MESSAGE = "Updated successfully!";
  private final MatchService matchService;

  public MatchController(MatchService matchService) {
    this.matchService = matchService;
  }

  @PostMapping
  public ResponseEntity<String> createMatch(@Validated @RequestBody Match match) {
    matchService.createMatch(match);
    return new ResponseEntity<>(CREATE_SUCCESS_MESSAGE, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Match>> getAllMatches() {
    return new ResponseEntity<>(matchService.getAllMatches(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Match> getMatchById(@PathVariable Long id) {
    return new ResponseEntity<>(matchService.getMatchById(id), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteMatch(@PathVariable Long id) {
    matchService.deleteMatch(id);
    return new ResponseEntity<>(DELETE_SUCCESS_MESSAGE, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateMatch(@PathVariable Long id, @RequestBody Match newMatch) {
    matchService.updateMatch(id, newMatch);
    return new ResponseEntity<>(UPDATE_SUCCESS_MESSAGE, HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<String> partialUpdateMatch(@PathVariable Long id,
                                                   @RequestBody Match matchDetails) {
    matchService.partialUpdateMatch(id, matchDetails);
    return new ResponseEntity<>(UPDATE_SUCCESS_MESSAGE, HttpStatus.OK);
  }

  @PostMapping("/{matchId}/players/{playerId}")
  public ResponseEntity<String> addPlayerToMatch(@PathVariable Long matchId,
                                                 @PathVariable Long playerId) {
    matchService.addPlayerToMatch(matchId, playerId);
    return new ResponseEntity<>(UPDATE_SUCCESS_MESSAGE, HttpStatus.OK);
  }

  @GetMapping("/filter")
  public ResponseEntity<String> getSomething() {
    matchService.filter();
    return new ResponseEntity<>(HttpStatus.OK);
  }
}

