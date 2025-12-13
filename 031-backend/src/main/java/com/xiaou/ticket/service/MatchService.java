package com.xiaou.ticket.service;

import com.xiaou.ticket.entity.Match;
import com.xiaou.ticket.repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {
    
    private final MatchRepository matchRepository;
    
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }
    
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }
    
    public List<Match> getUpcomingMatches() {
        return matchRepository.findUpcoming();
    }
    
    public Match getMatchById(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found"));
    }
    
    public Match createMatch(Match match) {
        match.setStatus("UPCOMING");
        return matchRepository.save(match);
    }
    
    public Match updateMatch(Long id, Match match) {
        Match existing = getMatchById(id);
        match.setId(id);
        return matchRepository.save(match);
    }
}
