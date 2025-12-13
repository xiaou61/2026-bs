package com.xiaou.seniorhealth.repository;

import com.xiaou.seniorhealth.domain.Notification;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
    @Query("select * from notification where user_id = :uid order by created_at desc limit :size")
    List<Notification> findLatestByUser(@Param("uid") Long uid, @Param("size") int size);
}
