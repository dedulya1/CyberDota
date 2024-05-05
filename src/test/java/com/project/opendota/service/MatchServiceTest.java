package com.project.opendota.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.opendota.aop.exception.InvalidRequestException;
import com.project.opendota.aop.exception.ResourceNotFoundException;
import com.project.opendota.cache.CacheEntity;
import com.project.opendota.entity.Match;
import com.project.opendota.entity.Player;
import com.project.opendota.entity.Team;
import com.project.opendota.repository.MatchRepository;
import com.project.opendota.repository.PlayerRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MatchService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class MatchServiceTest {
  @MockBean
  private CacheEntity<Long, Match> cacheEntity;

  @MockBean
  private MatchRepository matchRepository;

  @Autowired
  private MatchService matchService;

  @MockBean
  private PlayerRepository playerRepository;

  /**
   * Method under test: {@link MatchService#createMatch(Match)}
   */
  @Test
  void testCreateMatch() {
    // Arrange
    Match match = new Match();
    match.setDireScore(3);
    match.setDuration(1);
    match.setMatchId(1L);
    match.setPlayers(new HashSet<>());
    match.setRadiantScore(3);
    when(matchRepository.save(Mockito.<Match>any())).thenReturn(match);

    Match match2 = new Match();
    match2.setDireScore(3);
    match2.setDuration(1);
    match2.setMatchId(1L);
    match2.setPlayers(new HashSet<>());
    match2.setRadiantScore(3);

    // Act
    Match actualCreateMatchResult = matchService.createMatch(match2);

    // Assert
    verify(matchRepository).save(isA(Match.class));
    assertSame(match, actualCreateMatchResult);
  }

  /**
   * Method under test: {@link MatchService#createMatch(Match)}
   */
  @Test
  void testCreateMatch2() {
    // Arrange
    when(matchRepository.save(Mockito.<Match>any())).thenThrow(
        new InvalidRequestException("An error occurred"));

    Match match = new Match();
    match.setDireScore(3);
    match.setDuration(1);
    match.setMatchId(1L);
    match.setPlayers(new HashSet<>());
    match.setRadiantScore(3);

    // Act and Assert
    assertThrows(InvalidRequestException.class, () -> matchService.createMatch(match));
    verify(matchRepository).save(isA(Match.class));
  }

  /**
   * Method under test: {@link MatchService#getMatchById(Long)}
   */
  @Test
  void testGetMatchById() {
    // Arrange
    Match match = new Match();
    match.setDireScore(3);
    match.setDuration(1);
    match.setMatchId(1L);
    match.setPlayers(new HashSet<>());
    match.setRadiantScore(3);
    when(cacheEntity.get(Mockito.<Long>any())).thenReturn(match);

    // Act
    Match actualMatchById = matchService.getMatchById(1L);

    // Assert
    verify(cacheEntity).get(1L);
    assertSame(match, actualMatchById);
  }

  /**
   * Method under test: {@link MatchService#getMatchById(Long)}
   */
  @Test
  void testGetMatchById2() {
    // Arrange
    when(cacheEntity.get(Mockito.<Long>any())).thenThrow(
        new InvalidRequestException("An error occurred"));

    // Act and Assert
    assertThrows(InvalidRequestException.class, () -> matchService.getMatchById(1L));
    verify(cacheEntity).get(1L);
  }

  /**
   * Method under test: {@link MatchService#deleteMatch(Long)}
   */
  @Test
  void testDeleteMatch() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());
    doNothing().when(matchRepository).deleteById(Mockito.<Long>any());
    when(matchRepository.existsById(Mockito.<Long>any())).thenReturn(true);

    // Act
    matchService.deleteMatch(1L);

    // Assert that nothing has changed
    verify(cacheEntity).remove(1L);
    verify(matchRepository).deleteById(1L);
    verify(matchRepository).existsById(1L);
  }

  /**
   * Method under test: {@link MatchService#deleteMatch(Long)}
   */
  @Test
  void testDeleteMatch2() {
    // Arrange
    doThrow(new InvalidRequestException("An error occurred")).when(matchRepository)
        .deleteById(Mockito.<Long>any());
    when(matchRepository.existsById(Mockito.<Long>any())).thenReturn(true);

    // Act and Assert
    assertThrows(InvalidRequestException.class, () -> matchService.deleteMatch(1L));
    verify(matchRepository).deleteById(1L);
    verify(matchRepository).existsById(1L);
  }

  /**
   * Method under test: {@link MatchService#deleteMatch(Long)}
   */
  @Test
  void testDeleteMatch3() {
    // Arrange
    when(matchRepository.existsById(Mockito.<Long>any())).thenReturn(false);

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> matchService.deleteMatch(1L));
    verify(matchRepository).existsById(1L);
  }

  /**
   * Method under test: {@link MatchService#updateMatch(Long, Match)}
   */
  @Test
  void testUpdateMatch() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());

    Match match = new Match();
    match.setDireScore(3);
    match.setDuration(1);
    match.setMatchId(1L);
    match.setPlayers(new HashSet<>());
    match.setRadiantScore(3);
    when(matchRepository.save(Mockito.<Match>any())).thenReturn(match);

    Match matchDetails = new Match();
    matchDetails.setDireScore(3);
    matchDetails.setDuration(1);
    matchDetails.setMatchId(1L);
    matchDetails.setPlayers(new HashSet<>());
    matchDetails.setRadiantScore(3);

    // Act
    matchService.updateMatch(1L, matchDetails);

    // Assert
    verify(cacheEntity).remove(1L);
    verify(matchRepository).save(isA(Match.class));
    assertEquals(1L, matchDetails.getMatchId().longValue());
  }

  /**
   * Method under test: {@link MatchService#updateMatch(Long, Match)}
   */
  @Test
  void testUpdateMatch2() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());
    when(matchRepository.save(Mockito.<Match>any())).thenThrow(
        new InvalidRequestException("An error occurred"));

    Match matchDetails = new Match();
    matchDetails.setDireScore(3);
    matchDetails.setDuration(1);
    matchDetails.setMatchId(1L);
    matchDetails.setPlayers(new HashSet<>());
    matchDetails.setRadiantScore(3);

    // Act and Assert
    assertThrows(InvalidRequestException.class, () -> matchService.updateMatch(1L, matchDetails));
    verify(cacheEntity).remove(1L);
    verify(matchRepository).save(isA(Match.class));
  }

  /**
   * Method under test: {@link MatchService#getAllMatches()}
   */
  @Test
  void testGetAllMatches() {
    // Arrange
    ArrayList<Match> matchList = new ArrayList<>();
    when(matchRepository.findAll()).thenReturn(matchList);

    // Act
    List<Match> actualAllMatches = matchService.getAllMatches();

    // Assert
    verify(matchRepository).findAll();
    assertTrue(actualAllMatches.isEmpty());
    assertSame(matchList, actualAllMatches);
  }

  /**
   * Method under test: {@link MatchService#getAllMatches()}
   */
  @Test
  void testGetAllMatches2() {
    // Arrange
    when(matchRepository.findAll()).thenThrow(new InvalidRequestException("An error occurred"));

    // Act and Assert
    assertThrows(InvalidRequestException.class, () -> matchService.getAllMatches());
    verify(matchRepository).findAll();
  }

  /**
   * Method under test: {@link MatchService#partialUpdateMatch(Long, Match)}
   */
  @Test
  void testPartialUpdateMatch() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());

    Match match = new Match();
    match.setDireScore(3);
    match.setDuration(1);
    match.setMatchId(1L);
    match.setPlayers(new HashSet<>());
    match.setRadiantScore(3);
    Optional<Match> ofResult = Optional.of(match);

    Match match2 = new Match();
    match2.setDireScore(3);
    match2.setDuration(1);
    match2.setMatchId(1L);
    match2.setPlayers(new HashSet<>());
    match2.setRadiantScore(3);
    when(matchRepository.save(Mockito.<Match>any())).thenReturn(match2);
    when(matchRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    Match updates = new Match();
    updates.setDireScore(3);
    updates.setDuration(1);
    updates.setMatchId(1L);
    updates.setPlayers(new HashSet<>());
    updates.setRadiantScore(3);

    // Act
    matchService.partialUpdateMatch(1L, updates);

    // Assert
    verify(cacheEntity).remove(1L);
    verify(matchRepository).findById(1L);
    verify(matchRepository).save(isA(Match.class));
  }

  /**
   * Method under test: {@link MatchService#partialUpdateMatch(Long, Match)}
   */
  @Test
  void testPartialUpdateMatch2() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());

    Match match = new Match();
    match.setDireScore(3);
    match.setDuration(1);
    match.setMatchId(1L);
    match.setPlayers(new HashSet<>());
    match.setRadiantScore(3);
    Optional<Match> ofResult = Optional.of(match);
    when(matchRepository.save(Mockito.<Match>any())).thenThrow(
        new InvalidRequestException("An error occurred"));
    when(matchRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    Match updates = new Match();
    updates.setDireScore(3);
    updates.setDuration(1);
    updates.setMatchId(1L);
    updates.setPlayers(new HashSet<>());
    updates.setRadiantScore(3);

    // Act and Assert
    assertThrows(InvalidRequestException.class, () -> matchService.partialUpdateMatch(1L, updates));
    verify(cacheEntity).remove(1L);
    verify(matchRepository).findById(1L);
    verify(matchRepository).save(isA(Match.class));
  }

  /**
   * Method under test: {@link MatchService#partialUpdateMatch(Long, Match)}
   */
  @Test
  void testPartialUpdateMatch3() {
    // Arrange
    Optional<Match> emptyResult = Optional.empty();
    when(matchRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

    Match updates = new Match();
    updates.setDireScore(3);
    updates.setDuration(1);
    updates.setMatchId(1L);
    updates.setPlayers(new HashSet<>());
    updates.setRadiantScore(3);

    // Act and Assert
    assertThrows(ResourceNotFoundException.class,
        () -> matchService.partialUpdateMatch(1L, updates));
    verify(matchRepository).findById(1L);
  }

  /**
   * Method under test: {@link MatchService#partialUpdateMatch(Long, Match)}
   */
  @Test
  void testPartialUpdateMatch4() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());

    Match match = new Match();
    match.setDireScore(3);
    match.setDuration(1);
    match.setMatchId(1L);
    match.setPlayers(new HashSet<>());
    match.setRadiantScore(3);
    Optional<Match> ofResult = Optional.of(match);

    Match match2 = new Match();
    match2.setDireScore(3);
    match2.setDuration(1);
    match2.setMatchId(1L);
    match2.setPlayers(new HashSet<>());
    match2.setRadiantScore(3);
    when(matchRepository.save(Mockito.<Match>any())).thenReturn(match2);
    when(matchRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    Match updates = new Match();
    updates.setDireScore(null);
    updates.setDuration(1);
    updates.setMatchId(1L);
    updates.setPlayers(new HashSet<>());
    updates.setRadiantScore(3);

    // Act
    matchService.partialUpdateMatch(1L, updates);

    // Assert
    verify(cacheEntity).remove(1L);
    verify(matchRepository).findById(1L);
    verify(matchRepository).save(isA(Match.class));
  }

  /**
   * Method under test: {@link MatchService#partialUpdateMatch(Long, Match)}
   */
  @Test
  void testPartialUpdateMatch5() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());

    Match match = new Match();
    match.setDireScore(3);
    match.setDuration(1);
    match.setMatchId(1L);
    match.setPlayers(new HashSet<>());
    match.setRadiantScore(3);
    Optional<Match> ofResult = Optional.of(match);

    Match match2 = new Match();
    match2.setDireScore(3);
    match2.setDuration(1);
    match2.setMatchId(1L);
    match2.setPlayers(new HashSet<>());
    match2.setRadiantScore(3);
    when(matchRepository.save(Mockito.<Match>any())).thenReturn(match2);
    when(matchRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    Match updates = new Match();
    updates.setDireScore(3);
    updates.setDuration(null);
    updates.setMatchId(1L);
    updates.setPlayers(new HashSet<>());
    updates.setRadiantScore(3);

    // Act
    matchService.partialUpdateMatch(1L, updates);

    // Assert
    verify(cacheEntity).remove(1L);
    verify(matchRepository).findById(1L);
    verify(matchRepository).save(isA(Match.class));
  }

  /**
   * Method under test: {@link MatchService#partialUpdateMatch(Long, Match)}
   */
  @Test
  void testPartialUpdateMatch6() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());

    Match match = new Match();
    match.setDireScore(3);
    match.setDuration(1);
    match.setMatchId(1L);
    match.setPlayers(new HashSet<>());
    match.setRadiantScore(3);
    Optional<Match> ofResult = Optional.of(match);

    Match match2 = new Match();
    match2.setDireScore(3);
    match2.setDuration(1);
    match2.setMatchId(1L);
    match2.setPlayers(new HashSet<>());
    match2.setRadiantScore(3);
    when(matchRepository.save(Mockito.<Match>any())).thenReturn(match2);
    when(matchRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    Match updates = new Match();
    updates.setDireScore(3);
    updates.setDuration(1);
    updates.setMatchId(1L);
    updates.setPlayers(new HashSet<>());
    updates.setRadiantScore(null);

    // Act
    matchService.partialUpdateMatch(1L, updates);

    // Assert
    verify(cacheEntity).remove(1L);
    verify(matchRepository).findById(1L);
    verify(matchRepository).save(isA(Match.class));
  }

  /**
   * Method under test: {@link MatchService#addPlayerToMatch(Long, Long)}
   */
  @Test
  void testAddPlayerToMatch() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());

    Match match = new Match();
    match.setDireScore(3);
    match.setDuration(1);
    match.setMatchId(1L);
    match.setPlayers(new HashSet<>());
    match.setRadiantScore(3);
    Optional<Match> ofResult = Optional.of(match);

    Match match2 = new Match();
    match2.setDireScore(3);
    match2.setDuration(1);
    match2.setMatchId(1L);
    match2.setPlayers(new HashSet<>());
    match2.setRadiantScore(3);
    when(matchRepository.save(Mockito.<Match>any())).thenReturn(match2);
    when(matchRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    Team team = new Team();
    team.setName("Name");
    team.setPlayers(new HashSet<>());
    team.setTeamId(1L);

    Player player = new Player();
    player.setAccountId(1L);
    player.setMatches(new HashSet<>());
    player.setNickName("Nick Name");
    player.setRating(1);
    player.setTeam(team);
    Optional<Player> ofResult2 = Optional.of(player);
    when(playerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

    // Act
    matchService.addPlayerToMatch(1L, 1L);

    // Assert
    verify(cacheEntity).remove(1L);
    verify(matchRepository).findById(1L);
    verify(playerRepository).findById(1L);
    verify(matchRepository).save(isA(Match.class));
  }

  /**
   * Method under test: {@link MatchService#addPlayerToMatch(Long, Long)}
   */
  @Test
  void testAddPlayerToMatch2() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());

    Match match = new Match();
    match.setDireScore(3);
    match.setDuration(1);
    match.setMatchId(1L);
    match.setPlayers(new HashSet<>());
    match.setRadiantScore(3);
    Optional<Match> ofResult = Optional.of(match);
    when(matchRepository.save(Mockito.<Match>any())).thenThrow(
        new InvalidRequestException("An error occurred"));
    when(matchRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    Team team = new Team();
    team.setName("Name");
    team.setPlayers(new HashSet<>());
    team.setTeamId(1L);

    Player player = new Player();
    player.setAccountId(1L);
    player.setMatches(new HashSet<>());
    player.setNickName("Nick Name");
    player.setRating(1);
    player.setTeam(team);
    Optional<Player> ofResult2 = Optional.of(player);
    when(playerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

    // Act and Assert
    assertThrows(InvalidRequestException.class, () -> matchService.addPlayerToMatch(1L, 1L));
    verify(cacheEntity).remove(1L);
    verify(matchRepository).findById(1L);
    verify(playerRepository).findById(1L);
    verify(matchRepository).save(isA(Match.class));
  }

  /**
   * Method under test: {@link MatchService#addPlayerToMatch(Long, Long)}
   */
  @Test
  void testAddPlayerToMatch3() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());
    Optional<Match> emptyResult = Optional.empty();
    when(matchRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

    Team team = new Team();
    team.setName("Name");
    team.setPlayers(new HashSet<>());
    team.setTeamId(1L);

    Player player = new Player();
    player.setAccountId(1L);
    player.setMatches(new HashSet<>());
    player.setNickName("Nick Name");
    player.setRating(1);
    player.setTeam(team);
    Optional<Player> ofResult = Optional.of(player);
    when(playerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    // Act
    matchService.addPlayerToMatch(1L, 1L);

    // Assert that nothing has changed
    verify(cacheEntity).remove(1L);
    verify(matchRepository).findById(1L);
    verify(playerRepository).findById(1L);
  }

  /**
   * Method under test: {@link MatchService#addPlayerToMatch(Long, Long)}
   */
  @Test
  void testAddPlayerToMatch4() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());

    Match match = new Match();
    match.setDireScore(3);
    match.setDuration(1);
    match.setMatchId(1L);
    match.setPlayers(new HashSet<>());
    match.setRadiantScore(3);
    Optional<Match> ofResult = Optional.of(match);
    when(matchRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
    Optional<Player> emptyResult = Optional.empty();
    when(playerRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

    // Act
    matchService.addPlayerToMatch(1L, 1L);

    // Assert that nothing has changed
    verify(cacheEntity).remove(1L);
    verify(matchRepository).findById(1L);
    verify(playerRepository).findById(1L);
  }
}
