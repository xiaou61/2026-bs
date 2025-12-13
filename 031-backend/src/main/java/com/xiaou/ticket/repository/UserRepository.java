package com.xiaou.ticket.repository;

import com.xiaou.ticket.entity.User;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.jooq.impl.DSL.*;

@Repository
public class UserRepository {
    
    private final DSLContext dsl;
    
    public UserRepository(DSLContext dsl) {
        this.dsl = dsl;
    }
    
    public Optional<User> findByUsername(String username) {
        Record record = dsl.select()
                .from(table("user"))
                .where(field("username").eq(username))
                .fetchOne();
        return Optional.ofNullable(mapToUser(record));
    }
    
    public Optional<User> findById(Long id) {
        Record record = dsl.select()
                .from(table("user"))
                .where(field("id").eq(id))
                .fetchOne();
        return Optional.ofNullable(mapToUser(record));
    }
    
    public User save(User user) {
        if (user.getId() == null) {
            Long id = dsl.insertInto(table("user"))
                    .set(field("username"), user.getUsername())
                    .set(field("password"), user.getPassword())
                    .set(field("phone"), user.getPhone())
                    .set(field("email"), user.getEmail())
                    .set(field("real_name"), user.getRealName())
                    .set(field("id_card"), user.getIdCard())
                    .set(field("role"), user.getRole())
                    .set(field("balance"), user.getBalance())
                    .set(field("status"), user.getStatus())
                    .returningResult(field("id", Long.class))
                    .fetchOne()
                    .value1();
            user.setId(id);
        } else {
            dsl.update(table("user"))
                    .set(field("phone"), user.getPhone())
                    .set(field("email"), user.getEmail())
                    .set(field("real_name"), user.getRealName())
                    .set(field("id_card"), user.getIdCard())
                    .set(field("balance"), user.getBalance())
                    .set(field("status"), user.getStatus())
                    .where(field("id").eq(user.getId()))
                    .execute();
        }
        return user;
    }
    
    public List<User> findAll() {
        return dsl.select()
                .from(table("user"))
                .fetch(this::mapToUser);
    }
    
    public void deleteById(Long id) {
        dsl.deleteFrom(table("user"))
                .where(field("id").eq(id))
                .execute();
    }
    
    private User mapToUser(Record record) {
        if (record == null) return null;
        User user = new User();
        user.setId(record.get("id", Long.class));
        user.setUsername(record.get("username", String.class));
        user.setPassword(record.get("password", String.class));
        user.setPhone(record.get("phone", String.class));
        user.setEmail(record.get("email", String.class));
        user.setRealName(record.get("real_name", String.class));
        user.setIdCard(record.get("id_card", String.class));
        user.setRole(record.get("role", String.class));
        user.setBalance(record.get("balance", java.math.BigDecimal.class));
        user.setStatus(record.get("status", String.class));
        user.setCreatedAt(record.get("created_at", LocalDateTime.class));
        user.setUpdatedAt(record.get("updated_at", LocalDateTime.class));
        return user;
    }
}
