package com.project.opendota.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.opendota.aop.exception.InvalidRequestException;
import com.project.opendota.aop.exception.ResourceNotFoundException;
import com.project.opendota.cache.CacheEntity;
import com.project.opendota.entity.Match;
import com.project.opendota.entity.Player;
import com.project.opendota.entity.Team;
import com.project.opendota.repository.PlayerRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PlayerService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class PlayerServiceTest {
  @MockBean
  private CacheEntity<Long, Player> cacheEntity;

  @MockBean
  private PlayerRepository playerRepository;

  @Autowired
  private PlayerService playerService;

  /**
   * Method under test: {@link PlayerService#getPlayers()}
   */
  @Test
  void testGetPlayers() {
    // Arrange
    ArrayList<Player> playerList = new ArrayList<>();
    when(playerRepository.findAll()).thenReturn(playerList);

    // Act
    List<Player> actualPlayers = playerService.getPlayers();

    // Assert
    verify(playerRepository).findAll();
    assertTrue(actualPlayers.isEmpty());
    assertSame(playerList, actualPlayers);
  }

  /**
   * Method under test: {@link PlayerService#getPlayers()}
   */
  @Test
  void testGetPlayers2() {
    // Arrange
    when(playerRepository.findAll()).thenThrow(new ResourceNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> playerService.getPlayers());
    verify(playerRepository).findAll();
  }

  /**
   * Method under test: {@link PlayerService#findPlayerById(Long)}
   */
  @Test
  void testFindPlayerById() {
    // Arrange
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
    when(cacheEntity.get(Mockito.<Long>any())).thenReturn(player);

    // Act
    Player actualFindPlayerByIdResult = playerService.findPlayerById(1L);

    // Assert
    verify(cacheEntity).get(eq(1L));
    assertSame(player, actualFindPlayerByIdResult);
  }

  /**
   * Method under test: {@link PlayerService#findPlayerById(Long)}
   */
  @Test
  void testFindPlayerById2() {
    // Arrange
    when(cacheEntity.get(Mockito.<Long>any())).thenThrow(
        new ResourceNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> playerService.findPlayerById(1L));
    verify(cacheEntity).get(eq(1L));
  }

  /**
   * Method under test: {@link PlayerService#deletePlayerById(Long)}
   */
  @Test
  void testDeletePlayerById() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());

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
    doNothing().when(playerRepository).delete(Mockito.<Player>any());
    when(playerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    // Act
    playerService.deletePlayerById(1L);

    // Assert
    verify(cacheEntity).remove(eq(1L));
    verify(playerRepository).delete(isA(Player.class));
    verify(playerRepository).findById(eq(1L));
  }

  /**
   * Method under test: {@link PlayerService#deletePlayerById(Long)}
   */
  @Test
  void testDeletePlayerById2() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());

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
    doThrow(new ResourceNotFoundException("An error occurred")).when(playerRepository)
        .delete(Mockito.<Player>any());
    when(playerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> playerService.deletePlayerById(1L));
    verify(cacheEntity).remove(eq(1L));
    verify(playerRepository).delete(isA(Player.class));
    verify(playerRepository).findById(eq(1L));
  }

  /**
   * Method under test: {@link PlayerService#deletePlayerById(Long)}
   */
  @Test
  void testDeletePlayerById3() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());

    Team team = new Team();
    team.setName("Name");
    team.setPlayers(new HashSet<>());
    team.setTeamId(1L);

    Team team2 = new Team();
    team2.setName("Name");
    team2.setPlayers(new HashSet<>());
    team2.setTeamId(1L);

    Match match = new Match();
    match.setDireScore(3);
    match.setDuration(1);
    match.setMatchId(1L);
    match.setPlayers(new HashSet<>());
    match.setRadiantScore(3);

    HashSet<Match> matchSet = new HashSet<>();
    matchSet.add(match);
    Player player = mock(Player.class);
    when(player.getTeam()).thenReturn(team2);
    when(player.getMatches()).thenReturn(matchSet);
    doNothing().when(player).setAccountId(Mockito.<Long>any());
    doNothing().when(player).setMatches(Mockito.<Set<Match>>any());
    doNothing().when(player).setNickName(Mockito.<String>any());
    doNothing().when(player).setRating(Mockito.<Integer>any());
    doNothing().when(player).setTeam(Mockito.<Team>any());
    player.setAccountId(1L);
    player.setMatches(new HashSet<>());
    player.setNickName("Nick Name");
    player.setRating(1);
    player.setTeam(team);
    Optional<Player> ofResult = Optional.of(player);
    doNothing().when(playerRepository).delete(Mockito.<Player>any());
    when(playerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    // Act
    playerService.deletePlayerById(1L);

    // Assert
    verify(cacheEntity).remove(eq(1L));
    verify(player).getMatches();
    verify(player).getTeam();
    verify(player).setAccountId(eq(1L));
    verify(player, atLeast(1)).setMatches(Mockito.<Set<Match>>any());
    verify(player).setNickName(eq("Nick Name"));
    verify(player).setRating(eq(1));
    verify(player, atLeast(1)).setTeam(Mockito.<Team>any());
    verify(playerRepository).delete(isA(Player.class));
    verify(playerRepository).findById(eq(1L));
  }

  /**
   * Method under test: {@link PlayerService#deletePlayerById(Long)}
   */
  @Test
  void testDeletePlayerById4() {
    // Arrange
    Optional<Player> emptyResult = Optional.empty();
    when(playerRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> playerService.deletePlayerById(1L));
    verify(playerRepository).findById(eq(1L));
  }

  /**
   * Method under test: {@link PlayerService#updatePlayerById(Long, Player)}
   */
  @Test
  void testUpdatePlayerById() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());

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
    when(playerRepository.save(Mockito.<Player>any())).thenReturn(player);

    Team team2 = new Team();
    team2.setName("Name");
    team2.setPlayers(new HashSet<>());
    team2.setTeamId(1L);

    Player player2 = new Player();
    player2.setAccountId(1L);
    player2.setMatches(new HashSet<>());
    player2.setNickName("Nick Name");
    player2.setRating(1);
    player2.setTeam(team2);

    // Act
    playerService.updatePlayerById(1L, player2);

    // Assert
    verify(cacheEntity).remove(eq(1L));
    verify(playerRepository).save(isA(Player.class));
    assertEquals(1L, player2.getAccountId().longValue());
  }

  /**
   * Method under test: {@link PlayerService#updatePlayerById(Long, Player)}
   */
  @Test
  void testUpdatePlayerById2() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());
    when(playerRepository.save(Mockito.<Player>any())).thenThrow(
        new ResourceNotFoundException("An error occurred"));

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

    // Act and Assert
    assertThrows(InvalidRequestException.class, () -> playerService.updatePlayerById(1L, player));
    verify(cacheEntity).remove(eq(1L));
    verify(playerRepository).save(isA(Player.class));
  }

  /**
   * Method under test: {@link PlayerService#createPlayer(Player)}
   */
  @Test
  void testCreatePlayer() {
    // Arrange
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
    when(playerRepository.save(Mockito.<Player>any())).thenReturn(player);

    Team team2 = new Team();
    team2.setName("Name");
    team2.setPlayers(new HashSet<>());
    team2.setTeamId(1L);

    Player player2 = new Player();
    player2.setAccountId(1L);
    player2.setMatches(new HashSet<>());
    player2.setNickName("Nick Name");
    player2.setRating(1);
    player2.setTeam(team2);

    // Act
    playerService.createPlayer(player2);

    // Assert that nothing has changed
    verify(playerRepository).save(isA(Player.class));
    assertEquals("Name", player2.getTeam().getName());
    assertEquals("Nick Name", player2.getNickName());
    assertEquals(1, player2.getRating().intValue());
    assertEquals(1L, player2.getAccountId().longValue());
    assertTrue(player2.getMatches().isEmpty());
  }

  /**
   * Method under test: {@link PlayerService#createPlayer(Player)}
   */
  @Test
  void testCreatePlayer2() {
    // Arrange
    when(playerRepository.save(Mockito.<Player>any())).thenThrow(
        new ResourceNotFoundException("An error occurred"));

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

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> playerService.createPlayer(player));
    verify(playerRepository).save(isA(Player.class));
  }
}
