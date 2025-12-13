package com.xiaou.ticket.repository;

import com.xiaou.ticket.entity.Match;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.jooq.impl.DSL.*;

@Repository
public class MatchRepository {
    
    private final DSLContext dsl;
    
    public MatchRepository(DSLContext dsl) {
        this.dsl = dsl;
    }
    
    public List<Match> findAll() {
        return dsl.select()
                .from(table("match"))
                .orderBy(field("match_date").asc())
                .fetch(this::mapToMatch);
    }
    
    public List<Match> findUpcoming() {
        return dsl.select()
                .from(table("match"))
                .where(field("status").eq("UPCOMING"))
                .and(field("match_date").ge(LocalDateTime.now()))
                .orderBy(field("match_date").asc())
                .fetch(this::mapToMatch);
    }
    
    public Optional<Match> findById(Long id) {
        Record record = dsl.select()
                .from(table("match"))
                .where(field("id").eq(id))
                .fetchOne();
        return Optional.ofNullable(mapToMatch(record));
    }
    
    public Match save(Match match) {
        if (match.getId() == null) {
            Long id = dsl.insertInto(table("match"))
                    .set(field("title"), match.getTitle())
                    .set(field("home_team_id"), match.getHomeTeamId())
                    .set(field("away_team_id"), match.getAwayTeamId())
                    .set(field("stadium_id"), match.getStadiumId())
                    .set(field("match_date"), match.getMatchDate())
                    .set(field("match_type"), match.getMatchType())
                    .set(field("league"), match.getLeague())
                    .set(field("season"), match.getSeason())
                    .set(field("description"), match.getDescription())
                    .set(field("poster_url"), match.getPosterUrl())
                    .set(field("status"), match.getStatus())
                    .returningResult(field("id", Long.class))
                    .fetchOne()
                    .value1();
            match.setId(id);
        } else {
            dsl.update(table("match"))
                    .set(field("title"), match.getTitle())
                    .set(field("match_date"), match.getMatchDate())
                    .set(field("description"), match.getDescription())
                    .set(field("status"), match.getStatus())
                    .set(field("home_score"), match.getHomeScore())
                    .set(field("away_score"), match.getAwayScore())
                    .where(field("id").eq(match.getId()))
                    .execute();
        }
        return match;
    }
    
    private Match mapToMatch(Record record) {
        if (record == null) return null;
        Match match = new Match();
        match.setId(record.get("id", Long.class));
        match.setTitle(record.get("title", String.class));
        match.setHomeTeamId(record.get("home_team_id", Long.class));
        match.setAwayTeamId(record.get("away_team_id", Long.class));
        match.setStadiumId(record.get("stadium_id", Long.class));
        match.setMatchDate(record.get("match_date", LocalDateTime.class));
        match.setMatchType(record.get("match_type", String.class));
        match.setLeague(record.get("league", String.class));
        match.setSeason(record.get("season", String.class));
        match.setDescription(record.get("description", String.class));
        match.setPosterUrl(record.get("poster_url", String.class));
        match.setStatus(record.get("status", String.class));
        match.setHomeScore(record.get("home_score", Integer.class));
        match.setAwayScore(record.get("away_score", Integer.class));
        match.setCreatedAt(record.get("created_at", LocalDateTime.class));
        match.setUpdatedAt(record.get("updated_at", LocalDateTime.class));
        return match;
    }
}
