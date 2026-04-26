package com.xiaou.ticket.repository;

import com.xiaou.ticket.entity.TicketOrder;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.table;

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
                    .set(field("payment_time"), order.getPaymentTime())
                    .returningResult(field("id", Long.class))
                    .fetchOne()
                    .value1();
            order.setId(id);
        } else {
            dsl.update(table("ticket_order"))
                    .set(field("status"), order.getStatus())
                    .set(field("payment_method"), order.getPaymentMethod())
                    .set(field("payment_time"), order.getPaymentTime())
                    .set(field("updated_at"), LocalDateTime.now())
                    .where(field("id").eq(order.getId()))
                    .execute();
        }
        return findById(order.getId()).orElse(order);
    }

    public Optional<TicketOrder> findById(Long id) {
        return Optional.ofNullable(baseSelect()
                .where(field(name("o", "id")).eq(id))
                .fetchOne(this::mapToOrder));
    }

    public Optional<TicketOrder> findByOrderNo(String orderNo) {
        return Optional.ofNullable(baseSelect()
                .where(field(name("o", "order_no")).eq(orderNo))
                .fetchOne(this::mapToOrder));
    }

    public List<TicketOrder> findByUserId(Long userId) {
        return baseSelect()
                .where(field(name("o", "user_id")).eq(userId))
                .orderBy(field(name("o", "created_at")).desc())
                .fetch(this::mapToOrder);
    }

    public List<TicketOrder> findAll() {
        return baseSelect()
                .orderBy(field(name("o", "created_at")).desc())
                .fetch(this::mapToOrder);
    }

    private org.jooq.SelectJoinStep<Record> baseSelect() {
        Table<?> orderTable = table(name("ticket_order")).as("o");
        Table<?> matchTable = table(name("match")).as("m");
        Table<?> ticketCountTable = dsl.select(
                        field(name("ticket", "order_id")).as("order_id"),
                        count().as("ticket_count")
                )
                .from(table(name("ticket")))
                .groupBy(field(name("ticket", "order_id")))
                .asTable("tc");
        return dsl.select(orderTable.asterisk())
                .select(
                        field(name("m", "title")).as("match_title"),
                        field(name("tc", "ticket_count"), Integer.class).as("ticket_count")
                )
                .from(orderTable)
                .leftJoin(matchTable).on(field(name("o", "match_id")).eq(field(name("m", "id"))))
                .leftJoin(ticketCountTable).on(field(name("o", "id")).eq(field(name("tc", "order_id"))));
    }

    private TicketOrder mapToOrder(Record record) {
        if (record == null) {
            return null;
        }
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
        order.setMatchTitle(record.get("match_title", String.class));
        order.setTicketCount(record.get("ticket_count", Integer.class));
        order.setCreatedAt(record.get("created_at", LocalDateTime.class));
        order.setUpdatedAt(record.get("updated_at", LocalDateTime.class));
        return order;
    }
}
