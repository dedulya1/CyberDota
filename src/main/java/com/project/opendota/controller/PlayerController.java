package com.project.opendota.controller;

import com.project.opendota.entity.Player;
import com.project.opendota.service.PlayerService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
  private final PlayerService playerService;

  private static final String CREATE_SUCCESS_MESSAGE = "Created successfully!";
  private static final String DELETE_SUCCESS_MESSAGE = "Deleted successfully!";
  private static final String UPDATE_SUCCESS_MESSAGE = "Updated successfully!";

  public PlayerController(PlayerService playerService) {
    this.playerService = playerService;
  }

  @PostMapping
  public ResponseEntity<String> createPlayer(@RequestBody @Validated Player player) {
    playerService.createPlayer(player);
    return new ResponseEntity<>(CREATE_SUCCESS_MESSAGE, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Player>> getAllPlayers() {
    return new ResponseEntity<>(playerService.getPlayers(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Player> getPlayer(@PathVariable Long id) {
    return new ResponseEntity<>(playerService.findPlayerById(id), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deletePlayerById(@PathVariable Long id) {
    playerService.deletePlayerById(id);
    return new ResponseEntity<>(DELETE_SUCCESS_MESSAGE, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updatePlayerById(@PathVariable Long id,
                                                 @RequestBody Player player) {
    playerService.updatePlayerById(id, player);
    return new ResponseEntity<>(UPDATE_SUCCESS_MESSAGE, HttpStatus.OK);
  }
}
