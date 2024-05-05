package com.project.opendota.service;

import com.project.opendota.aop.annotation.Logging;
import com.project.opendota.aop.exception.InvalidRequestException;
import com.project.opendota.aop.exception.NotImplementedException;
import com.project.opendota.aop.exception.ResourceNotFoundException;
import com.project.opendota.cache.CacheEntity;
import com.project.opendota.entity.Match;
import com.project.opendota.entity.Player;
import com.project.opendota.repository.MatchRepository;
import com.project.opendota.repository.PlayerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;


@Logging
@Service
public class MatchService {
  public static final String MATCH_WITH_ID_D_NOT_FOUND = "Match (with id = %d) not found";
  private final CacheEntity<Long, Match> cache;
  private final MatchRepository matchRepository;
  private final PlayerRepository playerRepository;

  public MatchService(CacheEntity<Long, Match> cache, MatchRepository matchRepository,
                      PlayerRepository playerRepository) {
    this.cache = cache;
    this.matchRepository = matchRepository;
    this.playerRepository = playerRepository;
  }

  public Match createMatch(Match match) {
    try {
      return matchRepository.save(match);
    } catch (Exception e) {
      throw new InvalidRequestException(e.getMessage());
    }
  }

  public Match getMatchById(Long id) {
    Match match = cache.get(id);
    if (match == null) {
      match = matchRepository.findById(id).orElseThrow(
          () -> new ResourceNotFoundException(MATCH_WITH_ID_D_NOT_FOUND.formatted(id)));
      cache.put(id, match);
    }
    return match;
  }

  public void deleteMatch(Long id) {
    if (!matchRepository.existsById(id)) {
      throw new ResourceNotFoundException(MATCH_WITH_ID_D_NOT_FOUND.formatted(id));
    }
    matchRepository.deleteById(id);
    cache.remove(id);
  }

  public void updateMatch(Long id, Match matchDetails) {
    try {
      matchDetails.setMatchId(id);
      cache.remove(id);
      matchRepository.save(matchDetails);
    } catch (Exception e) {
      throw new InvalidRequestException(e.getMessage());
    }
  }

  public List<Match> getAllMatches() {
    return matchRepository.findAll();
  }

  public void partialUpdateMatch(Long id, Match updates) {
    Optional<Match> optionalMatch = matchRepository.findById(id);
    if (!optionalMatch.isPresent()) {
      throw new ResourceNotFoundException(MATCH_WITH_ID_D_NOT_FOUND.formatted(id));
    }
    Match match = optionalMatch.get();
    if (updates.getDireScore() == null
        && updates.getRadiantScore() == null
        && updates.getDuration() == null) {
      throw new InvalidRequestException("Updates were not provided.");
    }
    if (updates.getDireScore() != null) {
      match.setDireScore(updates.getDireScore());
    }
    if (updates.getRadiantScore() != null) {
      match.setRadiantScore(updates.getRadiantScore());
    }
    if (updates.getDuration() != null) {
      match.setDuration(updates.getDuration());
    }
    cache.remove(id);
    matchRepository.save(match);
  }

  public void addPlayerToMatch(Long matchId, Long playerId) {
    cache.remove(matchId);
    Optional<Match> match = matchRepository.findById(matchId);
    Optional<Player> player = playerRepository.findById(playerId);
    if (match.isPresent() && player.isPresent()) {
      match.get().getPlayers().add(player.get());
      player.get().addMatch(match.get());
      matchRepository.save(match.get());
    }
  }

  public void filter() {
    throw new NotImplementedException("Resource doesn't exist");
  }
}