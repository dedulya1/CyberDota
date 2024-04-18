package com.project.opendota.service;

import java.util.List;
import java.util.Optional;

import com.project.opendota.entity.Player;
import com.project.opendota.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import com.project.opendota.entity.Match;
import com.project.opendota.repository.MatchRepository;


@Service
public class MatchService {
    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;

    public MatchService(MatchRepository matchRepository, PlayerRepository playerRepository) {
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
    }

    public Match createMatch(Match match) {
        return matchRepository.save(match);
    }
    public Optional<Match> getMatchById(Long id) {
        return matchRepository.findById(id);
    }
    public void deleteMatch(Long match_id) {
        matchRepository.deleteById(match_id);
    }
    public Match updateMatch(Long match_id, Match matchDetails) {
                Optional<Match> match = matchRepository.findById(match_id);
        if(match.isPresent()) {
            Match existingMatch = match.get();
            existingMatch.setMatchId(matchDetails.getMatchId());
            existingMatch.setDuration(matchDetails.getDuration());
            existingMatch.setRadiantScore(matchDetails.getRadiantScore());
            existingMatch.setDireScore(matchDetails.getDireScore());
            return matchRepository.save(existingMatch);
        }
        return null;
    }

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public void partialUpdateMatch(Long id, Match updates) {
        Optional<Match> optionalMatch = matchRepository.findById(id);
        if (!optionalMatch.isPresent()) {
            return;
        }
        Match match = optionalMatch.get();
        if (updates.getDireScore() != null) {
            match.setDireScore(updates.getDireScore());
        }
        if (updates.getRadiantScore() != null) {
            match.setRadiantScore(updates.getRadiantScore());
        }
        if (updates.getDuration() != null) {
            match.setDuration(updates.getDuration());
        }
        matchRepository.save(match);
    }

    public void addPlayerToMatch(Long match_id, Long player_id) {
        Optional<Match> match = matchRepository.findById(match_id);
        Optional<Player> player = playerRepository.findById(player_id);
        if (match.isPresent()) {
            match.get().getPlayers().add(player.get());
            player.get().addMatch(match.get());
            matchRepository.save(match.get());
        }
    }
}