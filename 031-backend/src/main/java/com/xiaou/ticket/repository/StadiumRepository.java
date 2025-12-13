package com.xiaou.ticket.repository;

import com.xiaou.ticket.entity.Stadium;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.jooq.impl.DSL.*;

@Repository
public class StadiumRepository {
    
    private final DSLContext dsl;
    
    public StadiumRepository(DSLContext dsl) {
        this.dsl = dsl;
    }
    
    public List<Stadium> findAll() {
        return dsl.select()
                .from(table("stadium"))
                .fetch(this::mapToStadium);
    }
    
    public Optional<Stadium> findById(Long id) {
        Record record = dsl.select()
                .from(table("stadium"))
                .where(field("id").eq(id))
                .fetchOne();
        return Optional.ofNullable(mapToStadium(record));
    }
    
    private Stadium mapToStadium(Record record) {
        if (record == null) return null;
        Stadium stadium = new Stadium();
        stadium.setId(record.get("id", Long.class));
        stadium.setName(record.get("name", String.class));
        stadium.setLocation(record.get("location", String.class));
        stadium.setCity(record.get("city", String.class));
        stadium.setCapacity(record.get("capacity", Integer.class));
        stadium.setDescription(record.get("description", String.class));
        stadium.setFacilities(record.get("facilities", String.class));
        stadium.setImageUrl(record.get("image_url", String.class));
        stadium.setStatus(record.get("status", String.class));
        stadium.setCreatedAt(record.get("created_at", LocalDateTime.class));
        stadium.setUpdatedAt(record.get("updated_at", LocalDateTime.class));
        return stadium;
    }
}
