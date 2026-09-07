package com.xiaou.ticket.repository;

import com.xiaou.ticket.entity.Ticket;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.table;

@Repository
public class TicketRepository {

    private final DSLContext dsl;

    public TicketRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Ticket save(Ticket ticket) {
        Long id = dsl.insertInto(table("ticket"))
                .set(field("ticket_no"), ticket.getTicketNo())
                .set(field("order_id"), ticket.getOrderId())
                .set(field("match_id"), ticket.getMatchId())
                .set(field("seat_id"), ticket.getSeatId())
                .set(field("category_id"), ticket.getCategoryId())
                .set(field("price"), ticket.getPrice())
                .set(field("status"), ticket.getStatus())
                .set(field("qr_code"), ticket.getQrCode())
                .returningResult(field("id", Long.class))
                .fetchOne()
                .value1();
        ticket.setId(id);
        return ticket;
    }

    public Optional<Ticket> findByTicketNo(String ticketNo) {
        return Optional.ofNullable(baseSelect()
                .where(field(name("t", "ticket_no")).eq(ticketNo))
                .fetchOne(this::mapToTicket));
    }

    public List<Ticket> findByOrderId(Long orderId) {
        return baseSelect()
                .where(field(name("t", "order_id")).eq(orderId))
                .orderBy(field(name("t", "id")).asc())
                .fetch(this::mapToTicket);
    }

    public List<Long> findActiveSeatIds(Long matchId, List<Long> seatIds) {
        if (seatIds == null || seatIds.isEmpty()) {
            return List.of();
        }
        return dsl.select(field("seat_id", Long.class))
                .from(table("ticket"))
                .where(field("match_id").eq(matchId))
                .and(field("seat_id").in(seatIds))
                .and(field("status").in("LOCKED", "VALID"))
                .fetch(field("seat_id", Long.class));
    }

    public Map<Long, String> findActiveStatusMap(Long matchId, Long categoryId) {
        return dsl.select(field("seat_id", Long.class), field("status", String.class))
                .from(table("ticket"))
                .where(field("match_id").eq(matchId))
                .and(field("category_id").eq(categoryId))
                .and(field("status").in("LOCKED", "VALID"))
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        record -> record.get("seat_id", Long.class),
                        record -> record.get("status", String.class),
                        (left, right) -> right
                ));
    }

    public void updateTicketStatus(Long ticketId, String status, String qrCode) {
        var update = dsl.update(table("ticket"))
                .set(field("status"), status);
        if (qrCode != null) {
            update.set(field("qr_code"), qrCode);
        }
        update.where(field("id").eq(ticketId)).execute();
    }

    private org.jooq.SelectJoinStep<Record> baseSelect() {
        Table<?> ticketTable = table(name("ticket")).as("t");
        Table<?> seatTable = table(name("seat")).as("s");
        Table<?> categoryTable = table(name("seat_category")).as("sc");
        Table<?> matchTable = table(name("match")).as("m");
        return dsl.select(ticketTable.asterisk())
                .select(
                        field(name("s", "row_number")).as("row_number"),
                        field(name("s", "seat_number")).as("seat_number"),
                        field(name("sc", "name")).as("category_name"),
                        field(name("m", "title")).as("match_title")
                )
                .from(ticketTable)
                .leftJoin(seatTable).on(field(name("t", "seat_id")).eq(field(name("s", "id"))))
                .leftJoin(categoryTable).on(field(name("t", "category_id")).eq(field(name("sc", "id"))))
                .leftJoin(matchTable).on(field(name("t", "match_id")).eq(field(name("m", "id"))));
    }

    private Ticket mapToTicket(Record record) {
        if (record == null) {
            return null;
        }
        Ticket ticket = new Ticket();
        ticket.setId(record.get("id", Long.class));
        ticket.setTicketNo(record.get("ticket_no", String.class));
        ticket.setOrderId(record.get("order_id", Long.class));
        ticket.setMatchId(record.get("match_id", Long.class));
        ticket.setSeatId(record.get("seat_id", Long.class));
        ticket.setCategoryId(record.get("category_id", Long.class));
        ticket.setPrice(record.get("price", java.math.BigDecimal.class));
        ticket.setStatus(record.get("status", String.class));
        ticket.setQrCode(record.get("qr_code", String.class));
        ticket.setCheckInTime(record.get("check_in_time", LocalDateTime.class));
        ticket.setSeatLabel(record.get("row_number", String.class) + "-" + record.get("seat_number", String.class));
        ticket.setCategoryName(record.get("category_name", String.class));
        ticket.setMatchTitle(record.get("match_title", String.class));
        ticket.setCreatedAt(record.get("created_at", LocalDateTime.class));
        return ticket;
    }
}
