package com.xiaou.ticket.repository;

import com.xiaou.ticket.entity.MatchPricing;
import com.xiaou.ticket.entity.Seat;
import com.xiaou.ticket.entity.SeatCategory;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.table;

@Repository
public class SeatRepository {

    private final DSLContext dsl;

    public SeatRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<SeatCategory> findCategoriesByStadiumId(Long stadiumId) {
        return dsl.select()
                .from(table("seat_category"))
                .where(field("stadium_id").eq(stadiumId))
                .orderBy(field("id").asc())
                .fetch(this::mapToCategory);
    }

    public List<MatchPricing> findPricingByMatchId(Long matchId) {
        Table<?> pricing = table(name("match_pricing")).as("mp");
        Table<?> category = table(name("seat_category")).as("sc");
        return dsl.select(pricing.asterisk())
                .select(
                        field(name("sc", "name")).as("category_name"),
                        field(name("sc", "description")).as("category_description"),
                        field(name("sc", "row_count")).as("row_count"),
                        field(name("sc", "column_count")).as("column_count")
                )
                .from(pricing)
                .leftJoin(category).on(field(name("mp", "category_id")).eq(field(name("sc", "id"))))
                .where(field(name("mp", "match_id")).eq(matchId))
                .orderBy(field(name("mp", "price")).desc())
                .fetch(this::mapToPricing);
    }

    public Optional<MatchPricing> findPricingById(Long id) {
        Table<?> pricing = table(name("match_pricing")).as("mp");
        Table<?> category = table(name("seat_category")).as("sc");
        Record record = dsl.select(pricing.asterisk())
                .select(
                        field(name("sc", "name")).as("category_name"),
                        field(name("sc", "description")).as("category_description"),
                        field(name("sc", "row_count")).as("row_count"),
                        field(name("sc", "column_count")).as("column_count")
                )
                .from(pricing)
                .leftJoin(category).on(field(name("mp", "category_id")).eq(field(name("sc", "id"))))
                .where(field(name("mp", "id")).eq(id))
                .fetchOne();
        return Optional.ofNullable(mapToPricing(record));
    }

    public Optional<MatchPricing> findPricingByMatchIdAndCategoryId(Long matchId, Long categoryId) {
        Table<?> pricing = table(name("match_pricing")).as("mp");
        Table<?> category = table(name("seat_category")).as("sc");
        Record record = dsl.select(pricing.asterisk())
                .select(
                        field(name("sc", "name")).as("category_name"),
                        field(name("sc", "description")).as("category_description"),
                        field(name("sc", "row_count")).as("row_count"),
                        field(name("sc", "column_count")).as("column_count")
                )
                .from(pricing)
                .leftJoin(category).on(field(name("mp", "category_id")).eq(field(name("sc", "id"))))
                .where(field(name("mp", "match_id")).eq(matchId))
                .and(field(name("mp", "category_id")).eq(categoryId))
                .fetchOne();
        return Optional.ofNullable(mapToPricing(record));
    }

    public List<Seat> findByCategoryId(Long categoryId) {
        return dsl.select()
                .from(table("seat"))
                .where(field("category_id").eq(categoryId))
                .orderBy(field("row_number").asc(), field("seat_number").asc())
                .fetch(this::mapToSeat);
    }

    public List<Seat> findByIds(List<Long> seatIds) {
        if (seatIds == null || seatIds.isEmpty()) {
            return List.of();
        }
        return dsl.select()
                .from(table("seat"))
                .where(field("id").in(seatIds))
                .fetch(this::mapToSeat);
    }

    public Optional<Seat> findSeatById(Long id) {
        Record record = dsl.select()
                .from(table("seat"))
                .where(field("id").eq(id))
                .fetchOne();
        return Optional.ofNullable(mapToSeat(record));
    }

    public void updateSeatStatus(Long seatId, String status) {
        dsl.update(table("seat"))
                .set(field("status"), status)
                .where(field("id").eq(seatId))
                .execute();
    }

    public void updateAvailableSeats(Long pricingId, int delta) {
        dsl.update(table("match_pricing"))
                .set(field("available_seats"), field("available_seats", Integer.class).plus(delta))
                .set(field("updated_at"), LocalDateTime.now())
                .where(field("id").eq(pricingId))
                .execute();
    }

    private SeatCategory mapToCategory(Record record) {
        if (record == null) {
            return null;
        }
        SeatCategory category = new SeatCategory();
        category.setId(record.get("id", Long.class));
        category.setStadiumId(record.get("stadium_id", Long.class));
        category.setName(record.get("name", String.class));
        category.setDescription(record.get("description", String.class));
        category.setTotalSeats(record.get("total_seats", Integer.class));
        category.setRowCount(record.get("row_count", Integer.class));
        category.setColumnCount(record.get("column_count", Integer.class));
        category.setCreatedAt(record.get("created_at", LocalDateTime.class));
        return category;
    }

    private MatchPricing mapToPricing(Record record) {
        if (record == null) {
            return null;
        }
        MatchPricing pricing = new MatchPricing();
        pricing.setId(record.get("id", Long.class));
        pricing.setMatchId(record.get("match_id", Long.class));
        pricing.setCategoryId(record.get("category_id", Long.class));
        pricing.setPrice(record.get("price", BigDecimal.class));
        pricing.setAvailableSeats(record.get("available_seats", Integer.class));
        pricing.setCategoryName(record.get("category_name", String.class));
        pricing.setCategoryDescription(record.get("category_description", String.class));
        pricing.setRowCount(record.get("row_count", Integer.class));
        pricing.setColumnCount(record.get("column_count", Integer.class));
        pricing.setCreatedAt(record.get("created_at", LocalDateTime.class));
        pricing.setUpdatedAt(record.get("updated_at", LocalDateTime.class));
        return pricing;
    }

    private Seat mapToSeat(Record record) {
        if (record == null) {
            return null;
        }
        Seat seat = new Seat();
        seat.setId(record.get("id", Long.class));
        seat.setCategoryId(record.get("category_id", Long.class));
        seat.setRowNumber(record.get("row_number", String.class));
        seat.setSeatNumber(record.get("seat_number", String.class));
        seat.setStatus(record.get("status", String.class));
        seat.setCreatedAt(record.get("created_at", LocalDateTime.class));
        return seat;
    }
}
