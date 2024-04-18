package com.project.opendota.entity;

import jakarta.persistence.*;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;


@Entity
@Data
@Table (name = "matches")
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
