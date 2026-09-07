package com.xiaou.ticket.service;

import com.xiaou.ticket.dto.SeatAvailability;
import com.xiaou.ticket.entity.Match;
import com.xiaou.ticket.entity.MatchPricing;
import com.xiaou.ticket.entity.Seat;
import com.xiaou.ticket.repository.MatchRepository;
import com.xiaou.ticket.repository.SeatRepository;
import com.xiaou.ticket.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;

    public MatchService(MatchRepository matchRepository, SeatRepository seatRepository, TicketRepository ticketRepository) {
        this.matchRepository = matchRepository;
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
    }

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public List<Match> getUpcomingMatches() {
        return matchRepository.findUpcoming();
    }

    public Match getMatchById(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("比赛不存在"));
    }

    public List<MatchPricing> getMatchPricing(Long matchId) {
        getMatchById(matchId);
        return seatRepository.findPricingByMatchId(matchId);
    }

    public List<SeatAvailability> getSeatAvailability(Long matchId, Long categoryId) {
        getMatchById(matchId);
        seatRepository.findPricingByMatchIdAndCategoryId(matchId, categoryId)
                .orElseThrow(() -> new RuntimeException("票价分区不存在"));

        List<Seat> seats = seatRepository.findByCategoryId(categoryId);
        Map<Long, String> activeStatusMap = ticketRepository.findActiveStatusMap(matchId, categoryId);

        return seats.stream()
                .map(seat -> new SeatAvailability(
                        seat.getId(),
                        seat.getCategoryId(),
                        seat.getRowNumber(),
                        seat.getSeatNumber(),
                        seat.getRowNumber() + "-" + seat.getSeatNumber(),
                        resolveSeatStatus(seat.getStatus(), activeStatusMap.get(seat.getId()))
                ))
                .toList();
    }

    public Match createMatch(Match match) {
        match.setStatus("UPCOMING");
        return matchRepository.save(match);
    }

    public Match updateMatch(Long id, Match match) {
        getMatchById(id);
        match.setId(id);
        return matchRepository.save(match);
    }

    private String resolveSeatStatus(String baseStatus, String ticketStatus) {
        if (!"AVAILABLE".equalsIgnoreCase(baseStatus)) {
            return "DISABLED";
        }
        if ("VALID".equalsIgnoreCase(ticketStatus)) {
            return "SOLD";
        }
        if ("LOCKED".equalsIgnoreCase(ticketStatus)) {
            return "LOCKED";
        }
        return "AVAILABLE";
    }
}
