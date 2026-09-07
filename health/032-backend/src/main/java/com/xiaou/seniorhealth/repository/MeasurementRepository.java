package com.xiaou.seniorhealth.repository;

import com.xiaou.seniorhealth.domain.Measurement;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeasurementRepository extends CrudRepository<Measurement, Long> {
    @Query("select * from measurement where elder_id = :elderId order by measured_at desc limit :size")
    List<Measurement> findLatestByElder(@Param("elderId") Long elderId, @Param("size") int size);
    @Query("select * from measurement where elder_id = :elderId and type = :type order by measured_at desc limit :size")
    List<Measurement> findLatestByElderAndType(@Param("elderId") Long elderId, @Param("type") String type, @Param("size") int size);
}
