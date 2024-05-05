package com.project.opendota.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.opendota.aop.exception.ResourceNotFoundException;
import com.project.opendota.cache.CacheEntity;
import com.project.opendota.entity.Player;
import com.project.opendota.entity.Team;
import com.project.opendota.repository.PlayerRepository;
import com.project.opendota.repository.TeamRepository;
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

@ContextConfiguration(classes = {TeamService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class TeamServiceTest {
  @MockBean
  private CacheEntity<Long, Team> cacheEntity;

  @MockBean
  private PlayerRepository playerRepository;

  @MockBean
  private TeamRepository teamRepository;

  @Autowired
  private TeamService teamService;

  /**
   * Method under test: {@link TeamService#findTeamById(Long)}
   */
  @Test
  void testFindTeamById() {
    // Arrange
    Team team = new Team();
    team.setName("Name");
    team.setPlayers(new HashSet<>());
    team.setTeamId(1L);
    when(cacheEntity.get(Mockito.<Long>any())).thenReturn(team);

    // Act
    Team actualFindTeamByIdResult = teamService.findTeamById(1L);

    // Assert
    verify(cacheEntity).get(eq(1L));
    assertSame(team, actualFindTeamByIdResult);
  }

  /**
   * Method under test: {@link TeamService#findTeamById(Long)}
   */
  @Test
  void testFindTeamById2() {
    // Arrange
    when(cacheEntity.get(Mockito.<Long>any())).thenThrow(
        new ResourceNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> teamService.findTeamById(1L));
    verify(cacheEntity).get(eq(1L));
  }

  /**
   * Method under test: {@link TeamService#deleteTeamById(Long)}
   */
  @Test
  void testDeleteTeamById() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());

    Team team = new Team();
    team.setName("Name");
    team.setPlayers(new HashSet<>());
    team.setTeamId(1L);
    Optional<Team> ofResult = Optional.of(team);
    doNothing().when(teamRepository).deleteById(Mockito.<Long>any());
    when(teamRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
    when(playerRepository.saveAll(Mockito.<Iterable<Player>>any())).thenReturn(new ArrayList<>());

    // Act
    teamService.deleteTeamById(1L);

    // Assert that nothing has changed
    verify(cacheEntity).remove(eq(1L));
    verify(teamRepository).deleteById(eq(1L));
    verify(teamRepository).findById(eq(1L));
    verify(playerRepository).saveAll(isA(Iterable.class));
  }

  /**
   * Method under test: {@link TeamService#deleteTeamById(Long)}
   */
  @Test
  void testDeleteTeamById2() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());

    Team team = new Team();
    team.setName("Name");
    team.setPlayers(new HashSet<>());
    team.setTeamId(1L);
    Optional<Team> ofResult = Optional.of(team);
    when(teamRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
    when(playerRepository.saveAll(Mockito.<Iterable<Player>>any()))
        .thenThrow(new ResourceNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> teamService.deleteTeamById(1L));
    verify(cacheEntity).remove(eq(1L));
    verify(teamRepository).findById(eq(1L));
    verify(playerRepository).saveAll(isA(Iterable.class));
  }

  /**
   * Method under test: {@link TeamService#deleteTeamById(Long)}
   */
  @Test
  void testDeleteTeamById3() {
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

    HashSet<Player> players = new HashSet<>();
    players.add(player);

    Team team2 = new Team();
    team2.setName("Name");
    team2.setPlayers(players);
    team2.setTeamId(1L);
    Optional<Team> ofResult = Optional.of(team2);
    doNothing().when(teamRepository).deleteById(Mockito.<Long>any());
    when(teamRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
    when(playerRepository.saveAll(Mockito.<Iterable<Player>>any())).thenReturn(new ArrayList<>());

    // Act
    teamService.deleteTeamById(1L);

    // Assert
    verify(cacheEntity).remove(eq(1L));
    verify(teamRepository).deleteById(eq(1L));
    verify(teamRepository).findById(eq(1L));
    verify(playerRepository).saveAll(isA(Iterable.class));
  }

  /**
   * Method under test: {@link TeamService#deleteTeamById(Long)}
   */
  @Test
  void testDeleteTeamById4() {
    // Arrange
    Optional<Team> emptyResult = Optional.empty();
    when(teamRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> teamService.deleteTeamById(1L));
    verify(teamRepository).findById(eq(1L));
  }

  /**
   * Method under test: {@link TeamService#updateTeam(Long, Team)}
   */
  @Test
  void testUpdateTeam() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());

    Team team = new Team();
    team.setName("Name");
    team.setPlayers(new HashSet<>());
    team.setTeamId(1L);
    when(teamRepository.save(Mockito.<Team>any())).thenReturn(team);

    Team teamDetails = new Team();
    teamDetails.setName("Name");
    teamDetails.setPlayers(new HashSet<>());
    teamDetails.setTeamId(1L);

    // Act
    teamService.updateTeam(1L, teamDetails);

    // Assert
    verify(cacheEntity).remove(eq(1L));
    verify(teamRepository).save(isA(Team.class));
    assertEquals(1L, teamDetails.getTeamId().longValue());
  }

  /**
   * Method under test: {@link TeamService#updateTeam(Long, Team)}
   */
  @Test
  void testUpdateTeam2() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());
    when(teamRepository.save(Mockito.<Team>any())).thenThrow(
        new ResourceNotFoundException("An error occurred"));

    Team teamDetails = new Team();
    teamDetails.setName("Name");
    teamDetails.setPlayers(new HashSet<>());
    teamDetails.setTeamId(1L);

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> teamService.updateTeam(1L, teamDetails));
    verify(cacheEntity).remove(eq(1L));
    verify(teamRepository).save(isA(Team.class));
  }

  /**
   * Method under test: {@link TeamService#addPlayer(Long, Long)}
   */
  @Test
  void testAddPlayer() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());

    Team team = new Team();
    team.setName("Name");
    team.setPlayers(new HashSet<>());
    team.setTeamId(1L);
    Optional<Team> ofResult = Optional.of(team);

    Team team2 = new Team();
    team2.setName("Name");
    team2.setPlayers(new HashSet<>());
    team2.setTeamId(1L);
    when(teamRepository.save(Mockito.<Team>any())).thenReturn(team2);
    when(teamRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    Team team3 = new Team();
    team3.setName("Name");
    team3.setPlayers(new HashSet<>());
    team3.setTeamId(1L);

    Player player = new Player();
    player.setAccountId(1L);
    player.setMatches(new HashSet<>());
    player.setNickName("Nick Name");
    player.setRating(1);
    player.setTeam(team3);
    Optional<Player> ofResult2 = Optional.of(player);
    when(playerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

    // Act
    teamService.addPlayer(1L, 1L);

    // Assert
    verify(cacheEntity).remove(eq(1L));
    verify(playerRepository).findById(eq(1L));
    verify(teamRepository).findById(eq(1L));
    verify(teamRepository).save(isA(Team.class));
  }

  /**
   * Method under test: {@link TeamService#addPlayer(Long, Long)}
   */
  @Test
  void testAddPlayer2() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());

    Team team = new Team();
    team.setName("Name");
    team.setPlayers(new HashSet<>());
    team.setTeamId(1L);
    Optional<Team> ofResult = Optional.of(team);
    when(teamRepository.save(Mockito.<Team>any())).thenThrow(
        new ResourceNotFoundException("An error occurred"));
    when(teamRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    Team team2 = new Team();
    team2.setName("Name");
    team2.setPlayers(new HashSet<>());
    team2.setTeamId(1L);

    Player player = new Player();
    player.setAccountId(1L);
    player.setMatches(new HashSet<>());
    player.setNickName("Nick Name");
    player.setRating(1);
    player.setTeam(team2);
    Optional<Player> ofResult2 = Optional.of(player);
    when(playerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> teamService.addPlayer(1L, 1L));
    verify(cacheEntity).remove(eq(1L));
    verify(playerRepository).findById(eq(1L));
    verify(teamRepository).findById(eq(1L));
    verify(teamRepository).save(isA(Team.class));
  }

  /**
   * Method under test: {@link TeamService#addPlayer(Long, Long)}
   */
  @Test
  void testAddPlayer3() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());
    Optional<Team> emptyResult = Optional.empty();
    when(teamRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

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

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> teamService.addPlayer(1L, 1L));
    verify(cacheEntity).remove(eq(1L));
    verify(teamRepository).findById(eq(1L));
  }

  /**
   * Method under test: {@link TeamService#addPlayer(Long, Long)}
   */
  @Test
  void testAddPlayer4() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());

    Team team = new Team();
    team.setName("Name");
    team.setPlayers(new HashSet<>());
    team.setTeamId(1L);
    Optional<Team> ofResult = Optional.of(team);
    when(teamRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
    Optional<Player> emptyResult = Optional.empty();
    when(playerRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> teamService.addPlayer(1L, 1L));
    verify(cacheEntity).remove(eq(1L));
    verify(playerRepository).findById(eq(1L));
    verify(teamRepository).findById(eq(1L));
  }

  /**
   * Method under test: {@link TeamService#createTeam(Team)}
   */
  @Test
  void testCreateTeam() {
    // Arrange
    Team team = new Team();
    team.setName("Name");
    team.setPlayers(new HashSet<>());
    team.setTeamId(1L);
    when(teamRepository.save(Mockito.<Team>any())).thenReturn(team);

    Team team2 = new Team();
    team2.setName("Name");
    team2.setPlayers(new HashSet<>());
    team2.setTeamId(1L);

    // Act
    teamService.createTeam(team2);

    // Assert that nothing has changed
    verify(teamRepository).save(isA(Team.class));
    assertEquals("Name", team2.getName());
    assertEquals(1L, team2.getTeamId().longValue());
    assertTrue(team2.getPlayers().isEmpty());
  }

  /**
   * Method under test: {@link TeamService#createTeam(Team)}
   */
  @Test
  void testCreateTeam2() {
    // Arrange
    when(teamRepository.save(Mockito.<Team>any())).thenThrow(
        new ResourceNotFoundException("An error occurred"));

    Team team = new Team();
    team.setName("Name");
    team.setPlayers(new HashSet<>());
    team.setTeamId(1L);

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> teamService.createTeam(team));
    verify(teamRepository).save(isA(Team.class));
  }

  /**
   * Method under test: {@link TeamService#removePlayerFromTeam(Long, Long)}
   */
  @Test
  void testRemovePlayerFromTeam() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());
    when(teamRepository.existsById(Mockito.<Long>any())).thenReturn(true);

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
    when(playerRepository.save(Mockito.<Player>any())).thenReturn(player2);
    when(playerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    // Act
    teamService.removePlayerFromTeam(1L, 1L);

    // Assert
    verify(cacheEntity).remove(eq(1L));
    verify(teamRepository).existsById(eq(1L));
    verify(playerRepository).findById(eq(1L));
    verify(playerRepository).save(isA(Player.class));
  }

  /**
   * Method under test: {@link TeamService#removePlayerFromTeam(Long, Long)}
   */
  @Test
  void testRemovePlayerFromTeam2() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());
    when(teamRepository.existsById(Mockito.<Long>any())).thenReturn(true);

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
    when(playerRepository.save(Mockito.<Player>any())).thenThrow(
        new ResourceNotFoundException("An error occurred"));
    when(playerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> teamService.removePlayerFromTeam(1L, 1L));
    verify(cacheEntity).remove(eq(1L));
    verify(teamRepository).existsById(eq(1L));
    verify(playerRepository).findById(eq(1L));
    verify(playerRepository).save(isA(Player.class));
  }

  /**
   * Method under test: {@link TeamService#removePlayerFromTeam(Long, Long)}
   */
  @Test
  void testRemovePlayerFromTeam3() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());
    when(teamRepository.existsById(Mockito.<Long>any())).thenReturn(false);

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> teamService.removePlayerFromTeam(1L, 1L));
    verify(cacheEntity).remove(eq(1L));
    verify(teamRepository).existsById(eq(1L));
  }

  /**
   * Method under test: {@link TeamService#removePlayerFromTeam(Long, Long)}
   */
  @Test
  void testRemovePlayerFromTeam4() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());
    when(teamRepository.existsById(Mockito.<Long>any())).thenReturn(true);
    Optional<Player> emptyResult = Optional.empty();
    when(playerRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> teamService.removePlayerFromTeam(1L, 1L));
    verify(cacheEntity).remove(eq(1L));
    verify(teamRepository).existsById(eq(1L));
    verify(playerRepository).findById(eq(1L));
  }

  /**
   * Method under test: {@link TeamService#addPlayerToTeam(Long, Long)}
   */
  @Test
  void testAddPlayerToTeam() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());

    Team team = new Team();
    team.setName("Name");
    team.setPlayers(new HashSet<>());
    team.setTeamId(1L);
    Optional<Team> ofResult = Optional.of(team);
    when(teamRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    Team team2 = new Team();
    team2.setName("Name");
    team2.setPlayers(new HashSet<>());
    team2.setTeamId(1L);

    Player player = new Player();
    player.setAccountId(1L);
    player.setMatches(new HashSet<>());
    player.setNickName("Nick Name");
    player.setRating(1);
    player.setTeam(team2);
    Optional<Player> ofResult2 = Optional.of(player);

    Team team3 = new Team();
    team3.setName("Name");
    team3.setPlayers(new HashSet<>());
    team3.setTeamId(1L);

    Player player2 = new Player();
    player2.setAccountId(1L);
    player2.setMatches(new HashSet<>());
    player2.setNickName("Nick Name");
    player2.setRating(1);
    player2.setTeam(team3);
    when(playerRepository.save(Mockito.<Player>any())).thenReturn(player2);
    when(playerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

    // Act
    teamService.addPlayerToTeam(1L, 1L);

    // Assert
    verify(cacheEntity).remove(eq(1L));
    verify(playerRepository).findById(eq(1L));
    verify(teamRepository).findById(eq(1L));
    verify(playerRepository).save(isA(Player.class));
  }

  /**
   * Method under test: {@link TeamService#addPlayerToTeam(Long, Long)}
   */
  @Test
  void testAddPlayerToTeam2() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());

    Team team = new Team();
    team.setName("Name");
    team.setPlayers(new HashSet<>());
    team.setTeamId(1L);
    Optional<Team> ofResult = Optional.of(team);
    when(teamRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    Team team2 = new Team();
    team2.setName("Name");
    team2.setPlayers(new HashSet<>());
    team2.setTeamId(1L);

    Player player = new Player();
    player.setAccountId(1L);
    player.setMatches(new HashSet<>());
    player.setNickName("Nick Name");
    player.setRating(1);
    player.setTeam(team2);
    Optional<Player> ofResult2 = Optional.of(player);
    when(playerRepository.save(Mockito.<Player>any())).thenThrow(
        new ResourceNotFoundException("An error occurred"));
    when(playerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> teamService.addPlayerToTeam(1L, 1L));
    verify(cacheEntity).remove(eq(1L));
    verify(playerRepository).findById(eq(1L));
    verify(teamRepository).findById(eq(1L));
    verify(playerRepository).save(isA(Player.class));
  }

  /**
   * Method under test: {@link TeamService#addPlayerToTeam(Long, Long)}
   */
  @Test
  void testAddPlayerToTeam3() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());
    Optional<Team> emptyResult = Optional.empty();
    when(teamRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

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

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> teamService.addPlayerToTeam(1L, 1L));
    verify(cacheEntity).remove(eq(1L));
    verify(teamRepository).findById(eq(1L));
  }

  /**
   * Method under test: {@link TeamService#addPlayerToTeam(Long, Long)}
   */
  @Test
  void testAddPlayerToTeam4() {
    // Arrange
    doNothing().when(cacheEntity).remove(Mockito.<Long>any());

    Team team = new Team();
    team.setName("Name");
    team.setPlayers(new HashSet<>());
    team.setTeamId(1L);
    Optional<Team> ofResult = Optional.of(team);
    when(teamRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
    Optional<Player> emptyResult = Optional.empty();
    when(playerRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> teamService.addPlayerToTeam(1L, 1L));
    verify(cacheEntity).remove(eq(1L));
    verify(playerRepository).findById(eq(1L));
    verify(teamRepository).findById(eq(1L));
  }

  /**
   * Method under test:
   * {@link TeamService#findPlayersInTeamWithRating(Long, Integer, Integer)}
   */
  @Test
  void testFindPlayersInTeamWithRating() {
    // Arrange
    ArrayList<Player> playerList = new ArrayList<>();
    when(
        teamRepository.findPlayersInTeamWithRating(Mockito.<Long>any(), Mockito.<Integer>any(),
            Mockito.<Integer>any()))
        .thenReturn(playerList);

    // Act
    List<Player> actualFindPlayersInTeamWithRatingResult =
        teamService.findPlayersInTeamWithRating(1L, 1, 3);

    // Assert
    verify(teamRepository).findPlayersInTeamWithRating(eq(1L), eq(1), eq(3));
    assertTrue(actualFindPlayersInTeamWithRatingResult.isEmpty());
    assertSame(playerList, actualFindPlayersInTeamWithRatingResult);
  }

  /**
   * Method under test:
   * {@link TeamService#findPlayersInTeamWithRating(Long, Integer, Integer)}
   */
  @Test
  void testFindPlayersInTeamWithRating2() {
    // Arrange
    when(
        teamRepository.findPlayersInTeamWithRating(Mockito.<Long>any(), Mockito.<Integer>any(),
            Mockito.<Integer>any()))
        .thenThrow(new ResourceNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ResourceNotFoundException.class,
        () -> teamService.findPlayersInTeamWithRating(1L, 1, 3));
    verify(teamRepository).findPlayersInTeamWithRating(eq(1L), eq(1), eq(3));
  }
}
