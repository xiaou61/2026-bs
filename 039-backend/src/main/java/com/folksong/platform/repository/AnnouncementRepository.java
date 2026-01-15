package com.folksong.platform.repository;

import com.folksong.platform.entity.Announcement;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnnouncementRepository extends CrudRepository<Announcement, Long> {
    
    @Query("SELECT * FROM announcements WHERE status = 1 ORDER BY create_time DESC LIMIT :limit OFFSET :offset")
    List<Announcement> findActiveWithPage(@Param("limit") Integer limit, @Param("offset") Integer offset);
    
    @Query("SELECT COUNT(*) FROM announcements WHERE status = 1")
    Long countActive();
    
    @Query("SELECT * FROM announcements ORDER BY create_time DESC LIMIT :limit OFFSET :offset")
    List<Announcement> findAllWithPage(@Param("limit") Integer limit, @Param("offset") Integer offset);
    
    @Query("SELECT * FROM announcements WHERE status = 1 ORDER BY create_time DESC LIMIT :limit")
    List<Announcement> findLatest(@Param("limit") Integer limit);
}
