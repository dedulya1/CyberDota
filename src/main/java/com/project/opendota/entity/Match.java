package com.project.opendota.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "matches")
@AllArgsConstructor
@NoArgsConstructor

public class Match {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long matchId;
  private Integer duration;
  private Integer radiantScore;
  private Integer direScore;

  @JsonIgnoreProperties({"matches"})
  @ManyToMany(cascade = {CascadeType.PERSIST})
  @JoinTable(
      name = "match_player",
      joinColumns = @JoinColumn(name = "match_id"),
      inverseJoinColumns = @JoinColumn(name = "account_id")
  )
  private Set<Player> players;

  public void addPlayer(Player player) {
    players.add(player);
  }
}
