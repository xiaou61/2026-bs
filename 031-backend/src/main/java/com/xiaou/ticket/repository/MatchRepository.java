package com.xiaou.ticket.repository;

import com.xiaou.ticket.entity.Match;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.table;

@Repository
public class MatchRepository {

    private final DSLContext dsl;

    public MatchRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<Match> findAll() {
        return baseSelect()
                .orderBy(field(name("m", "match_date"), LocalDateTime.class).asc())
                .fetch(this::mapToMatch);
    }

    public List<Match> findUpcoming() {
        return baseSelect()
                .where(field(name("m", "status"), String.class).eq("UPCOMING"))
                .and(field(name("m", "match_date"), LocalDateTime.class).ge(LocalDateTime.now()))
                .orderBy(field(name("m", "match_date"), LocalDateTime.class).asc())
                .fetch(this::mapToMatch);
    }

    public Optional<Match> findById(Long id) {
        return Optional.ofNullable(baseSelect()
                .where(field(name("m", "id")).eq(id))
                .fetchOne(this::mapToMatch));
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
                    .set(field("updated_at"), LocalDateTime.now())
                    .where(field("id").eq(match.getId()))
                    .execute();
        }
        return findById(match.getId()).orElse(match);
    }

    private org.jooq.SelectJoinStep<Record> baseSelect() {
        Table<?> matchTable = table(name("match")).as("m");
        Table<?> homeTeam = table(name("team")).as("ht");
        Table<?> awayTeam = table(name("team")).as("at");
        Table<?> stadium = table(name("stadium")).as("s");

        return dsl.select(matchTable.asterisk())
                .select(
                        field(name("ht", "name")).as("home_team_name"),
                        field(name("at", "name")).as("away_team_name"),
                        field(name("s", "name")).as("stadium_name"),
                        field(name("s", "location")).as("stadium_location")
                )
                .from(matchTable)
                .leftJoin(homeTeam).on(field(name("m", "home_team_id")).eq(field(name("ht", "id"))))
                .leftJoin(awayTeam).on(field(name("m", "away_team_id")).eq(field(name("at", "id"))))
                .leftJoin(stadium).on(field(name("m", "stadium_id")).eq(field(name("s", "id"))));
    }

    private Match mapToMatch(Record record) {
        if (record == null) {
            return null;
        }
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
        match.setHomeTeamName(record.get("home_team_name", String.class));
        match.setAwayTeamName(record.get("away_team_name", String.class));
        match.setStadiumName(record.get("stadium_name", String.class));
        match.setStadiumLocation(record.get("stadium_location", String.class));
        match.setCreatedAt(record.get("created_at", LocalDateTime.class));
        match.setUpdatedAt(record.get("updated_at", LocalDateTime.class));
        return match;
    }
}
