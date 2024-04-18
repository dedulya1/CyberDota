package com.project.opendota.controller;

import com.project.opendota.entity.Player;
import com.project.opendota.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public Player createPlayer(@RequestBody @Validated Player player) {
        return playerService.createPlayer(player);
    }
    @GetMapping
    public List<Player> getAllPlayers () {
        return playerService.getPlayers();
    }
    @GetMapping("/{id}")
    public Optional<Player> getPlayer (@PathVariable("id") Long accountId) {
        return playerService.findPlayerById(accountId);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayerById(@PathVariable("id") Long accountId) {
        playerService.deletePlayerById(accountId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePlayerById(@PathVariable("id") Long accountId, @RequestBody Player player) {
        playerService.updatePlayerById(accountId, player);
        return ResponseEntity.ok().build();
    }
}
