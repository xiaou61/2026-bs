package com.xiaou.ticket.controller;

import com.xiaou.ticket.dto.Result;
import com.xiaou.ticket.entity.Match;
import com.xiaou.ticket.service.MatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchController {
    
    private final MatchService matchService;
    
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }
    
    @GetMapping
    public Result<List<Match>> getAllMatches() {
        return Result.success(matchService.getAllMatches());
    }
    
    @GetMapping("/upcoming")
    public Result<List<Match>> getUpcomingMatches() {
        return Result.success(matchService.getUpcomingMatches());
    }
    
    @GetMapping("/{id}")
    public Result<Match> getMatchById(@PathVariable Long id) {
        try {
            return Result.success(matchService.getMatchById(id));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @PostMapping
    public Result<Match> createMatch(@RequestBody Match match) {
        try {
            return Result.success(matchService.createMatch(match));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public Result<Match> updateMatch(@PathVariable Long id, @RequestBody Match match) {
        try {
            return Result.success(matchService.updateMatch(id, match));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
