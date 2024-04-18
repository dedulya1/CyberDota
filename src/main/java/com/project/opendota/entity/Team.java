package com.project.opendota.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import jakarta.persistence.*;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long teamId;
    private String name;

    @JsonIgnoreProperties({"team", "matches"})
    @OneToMany(mappedBy = "team", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Player> players;

    public void addPlayer(Player player) {
        players.add(player);
    }
}