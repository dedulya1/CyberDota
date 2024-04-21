package com.project.opendota.service;

import com.project.opendota.aop.annotation.Logging;
import com.project.opendota.aop.exception.InvalidRequestException;
import com.project.opendota.aop.exception.ResourceNotFoundException;
import com.project.opendota.cache.CacheEntity;
import com.project.opendota.entity.Match;
import com.project.opendota.entity.Player;
import com.project.opendota.repository.PlayerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Logging
@Service
public class PlayerService {
  private final CacheEntity<Long, Player> cache;
  private final PlayerRepository playerRepository;

  public PlayerService(CacheEntity<Long, Player> cache, PlayerRepository playerRepository) {
    this.cache = cache;
    this.playerRepository = playerRepository;
  }

  public List<Player> getPlayers() {
    return playerRepository.findAll();
  }

  public Player findPlayerById(Long id) {
    Player player = cache.get(id);
    if (player == null) {
      player = playerRepository.findById(id).orElseThrow(
          () -> new ResourceNotFoundException("Player (with id = %d) not found".formatted(id)));
      cache.put(id, player);
    }
    return player;
  }

  public void deletePlayerById(Long id) {
    Player player = playerRepository.findById(id).orElse(null);
    if (player == null) {
      throw new ResourceNotFoundException("Player (with id = %d) not found".formatted(id));
    }
    cache.remove(id);
    for (Match match : player.getMatches()) {
      match.getPlayers().remove(player);
    }
    player.setMatches(null);
    player.getTeam().getPlayers().remove(player);
    player.setTeam(null);
    playerRepository.delete(player);
  }

  public void updatePlayerById(Long accountId, Player player) {
    try {
      player.setAccountId(accountId);
      cache.remove(accountId);
      playerRepository.save(player);
    } catch (Exception e) {
      throw new InvalidRequestException(e.getMessage());
    }
  }

  public void createPlayer(Player player) {
    playerRepository.save(player);
  }
}
