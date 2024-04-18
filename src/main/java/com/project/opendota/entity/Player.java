package com.project.opendota.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import jakarta.persistence.*;

import java.util.Set;

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