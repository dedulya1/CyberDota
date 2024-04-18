package com.project.opendota.controller;
import org.springframework.web.bind.annotation.*;

import com.project.opendota.entity.Match;
import com.project.opendota.service.MatchService;

import java.util.List;
import java.util.Optional;

import org.springframework.validation.annotation.Validated;

@RequestMapping("/api/matches")
@RestController
public class MatchController {
    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping
    public Match createMatch(@Validated @RequestBody Match match) {
        return matchService.createMatch(match);
    }
    @GetMapping
    public List<Match> getAllMatches() {
        return matchService.getAllMatches();
    }
    @GetMapping("/{match_id}")
    public Optional<Match> getMatchById(@PathVariable Long match_id) {
        return matchService.getMatchById(match_id);
    }
    @DeleteMapping("/{match_id}")
    public void deleteMatch(@PathVariable Long match_id) {
        matchService.deleteMatch(match_id);
    }
    @PutMapping("/{match_id}")
    public Match updateMatch(@PathVariable Long match_id, @RequestBody Match matchDetails) {
        return matchService.updateMatch(match_id, matchDetails);
    }
    @PatchMapping("/{match_id}")
    public void partialUpdateMatch(@PathVariable Long match_id, @RequestBody Match matchDetails) {
        matchService.partialUpdateMatch(match_id, matchDetails);
    }
    @PostMapping("/{match_id}/players/{player_id}")
    public void addPlayerToMatch(@PathVariable Long match_id, @PathVariable Long player_id) {
        matchService.addPlayerToMatch(match_id, player_id);
    }

}

