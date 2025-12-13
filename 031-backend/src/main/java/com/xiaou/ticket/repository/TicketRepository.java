package com.xiaou.ticket.repository;

import com.xiaou.ticket.entity.Ticket;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.jooq.impl.DSL.*;

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
        Record record = dsl.select()
                .from(table("ticket"))
                .where(field("ticket_no").eq(ticketNo))
                .fetchOne();
        return Optional.ofNullable(mapToTicket(record));
    }
    
    public List<Ticket> findByOrderId(Long orderId) {
        return dsl.select()
                .from(table("ticket"))
                .where(field("order_id").eq(orderId))
                .fetch(this::mapToTicket);
    }
    
    private Ticket mapToTicket(Record record) {
        if (record == null) return null;
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
        ticket.setCreatedAt(record.get("created_at", LocalDateTime.class));
        return ticket;
    }
}
