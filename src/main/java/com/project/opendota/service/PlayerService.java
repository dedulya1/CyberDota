package com.project.opendota.service;

import com.project.opendota.entity.Match;
import com.project.opendota.entity.Player;
import com.project.opendota.repository.MatchRepository;
import com.project.opendota.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;

    public PlayerService(PlayerRepository playerRepository, MatchRepository matchRepository) {
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
    }

    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    public Optional<Player> findPlayerById(Long accountId) {
        Optional<Player> player = playerRepository.findById(accountId);
        return player;
    }

    public List<Player> savePlayers(List<Player> players) {
        players.forEach(player -> {
            player.setAccountId(0L);
        });
        return playerRepository.saveAll(players);
    }
    public void deletePlayerById(Long accountId) {
        Player player = playerRepository.findById(accountId).orElse(null);
        for(Match match : player.getMatches()) {
            match.getPlayers().remove(player);
        }
        player.setMatches(null);
        player.getTeam().getPlayers().remove(player);
        player.setTeam(null);
        playerRepository.delete(player);
    }
    public void updatePlayerById(Long accountId, Player player) {
        Optional<Player> playerOptional = playerRepository.findById(accountId);
        if(playerOptional.isPresent()) {
            Player playerToUpdate = playerOptional.get();
            playerToUpdate.setNickName(player.getNickName());
        playerRepository.save(playerToUpdate);
        }
    }

    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }
}
