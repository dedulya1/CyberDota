package com.project.opendota.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "players")
public class Player {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long accountId;
  private String nickName;
  private Integer rating;
  @JsonIgnoreProperties({"players"})
  @ManyToOne
  @JoinColumn(name = "team_id")
  private Team team;

  @JsonIgnoreProperties({"players"})
  @ManyToMany(mappedBy = "players")
  private Set<Match> matches;

  public void addMatch(Match match) {
    matches.add(match);
  }
}