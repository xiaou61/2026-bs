package com.xiaou.seniorhealth.repository;

import com.xiaou.seniorhealth.domain.Elder;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ElderRepository extends CrudRepository<Elder, Long> {
    @Query("select * from elder where user_id = :userId limit 1")
    Optional<Elder> findByUserId(@Param("userId") Long userId);
}
