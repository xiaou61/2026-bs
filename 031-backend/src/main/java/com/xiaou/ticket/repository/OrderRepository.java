package com.xiaou.ticket.repository;

import com.xiaou.ticket.entity.TicketOrder;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.jooq.impl.DSL.*;

@Repository
public class OrderRepository {
    
    private final DSLContext dsl;
    
    public OrderRepository(DSLContext dsl) {
        this.dsl = dsl;
    }
    
    public TicketOrder save(TicketOrder order) {
        if (order.getId() == null) {
            Long id = dsl.insertInto(table("ticket_order"))
                    .set(field("order_no"), order.getOrderNo())
                    .set(field("user_id"), order.getUserId())
                    .set(field("match_id"), order.getMatchId())
                    .set(field("total_amount"), order.getTotalAmount())
                    .set(field("payment_amount"), order.getPaymentAmount())
                    .set(field("discount_amount"), order.getDiscountAmount())
                    .set(field("status"), order.getStatus())
                    .set(field("payment_method"), order.getPaymentMethod())
                    .returningResult(field("id", Long.class))
                    .fetchOne()
                    .value1();
            order.setId(id);
        } else {
            dsl.update(table("ticket_order"))
                    .set(field("status"), order.getStatus())
                    .set(field("payment_method"), order.getPaymentMethod())
                    .set(field("payment_time"), order.getPaymentTime())
                    .where(field("id").eq(order.getId()))
                    .execute();
        }
        return order;
    }
    
    public Optional<TicketOrder> findById(Long id) {
        Record record = dsl.select()
                .from(table("ticket_order"))
                .where(field("id").eq(id))
                .fetchOne();
        return Optional.ofNullable(mapToOrder(record));
    }
    
    public Optional<TicketOrder> findByOrderNo(String orderNo) {
        Record record = dsl.select()
                .from(table("ticket_order"))
                .where(field("order_no").eq(orderNo))
                .fetchOne();
        return Optional.ofNullable(mapToOrder(record));
    }
    
    public List<TicketOrder> findByUserId(Long userId) {
        return dsl.select()
                .from(table("ticket_order"))
                .where(field("user_id").eq(userId))
                .orderBy(field("created_at").desc())
                .fetch(this::mapToOrder);
    }
    
    public List<TicketOrder> findAll() {
        return dsl.select()
                .from(table("ticket_order"))
                .orderBy(field("created_at").desc())
                .fetch(this::mapToOrder);
    }
    
    private TicketOrder mapToOrder(Record record) {
        if (record == null) return null;
        TicketOrder order = new TicketOrder();
        order.setId(record.get("id", Long.class));
        order.setOrderNo(record.get("order_no", String.class));
        order.setUserId(record.get("user_id", Long.class));
        order.setMatchId(record.get("match_id", Long.class));
        order.setTotalAmount(record.get("total_amount", BigDecimal.class));
        order.setPaymentAmount(record.get("payment_amount", BigDecimal.class));
        order.setDiscountAmount(record.get("discount_amount", BigDecimal.class));
        order.setStatus(record.get("status", String.class));
        order.setPaymentMethod(record.get("payment_method", String.class));
        order.setPaymentTime(record.get("payment_time", LocalDateTime.class));
        order.setCreatedAt(record.get("created_at", LocalDateTime.class));
        order.setUpdatedAt(record.get("updated_at", LocalDateTime.class));
        return order;
    }
}
