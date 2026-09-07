package com.folksong.platform.repository;

import com.folksong.platform.entity.FolkSong;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FolkSongRepository extends CrudRepository<FolkSong, Long> {
    
    @Query("SELECT * FROM folk_songs WHERE status = 1 AND audit_status = 1 ORDER BY create_time DESC LIMIT :limit OFFSET :offset")
    List<FolkSong> findApprovedWithPage(@Param("limit") Integer limit, @Param("offset") Integer offset);
    
    @Query("SELECT COUNT(*) FROM folk_songs WHERE status = 1 AND audit_status = 1")
    Long countApproved();
    
    @Query("SELECT * FROM folk_songs WHERE category_id = :categoryId AND status = 1 AND audit_status = 1 ORDER BY create_time DESC LIMIT :limit OFFSET :offset")
    List<FolkSong> findByCategoryIdWithPage(@Param("categoryId") Long categoryId, @Param("limit") Integer limit, @Param("offset") Integer offset);
    
    @Query("SELECT COUNT(*) FROM folk_songs WHERE category_id = :categoryId AND status = 1 AND audit_status = 1")
    Long countByCategoryId(@Param("categoryId") Long categoryId);
    
    @Query("SELECT * FROM folk_songs WHERE user_id = :userId ORDER BY create_time DESC LIMIT :limit OFFSET :offset")
    List<FolkSong> findByUserIdWithPage(@Param("userId") Long userId, @Param("limit") Integer limit, @Param("offset") Integer offset);
    
    @Query("SELECT COUNT(*) FROM folk_songs WHERE user_id = :userId")
    Long countByUserId(@Param("userId") Long userId);
    
    @Query("SELECT * FROM folk_songs WHERE (title LIKE :keyword OR content LIKE :keyword OR lyrics LIKE :keyword) AND status = 1 AND audit_status = 1 ORDER BY create_time DESC LIMIT :limit OFFSET :offset")
    List<FolkSong> searchByKeyword(@Param("keyword") String keyword, @Param("limit") Integer limit, @Param("offset") Integer offset);
    
    @Query("SELECT COUNT(*) FROM folk_songs WHERE (title LIKE :keyword OR content LIKE :keyword OR lyrics LIKE :keyword) AND status = 1 AND audit_status = 1")
    Long countByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT * FROM folk_songs WHERE region = :region AND status = 1 AND audit_status = 1 ORDER BY create_time DESC LIMIT :limit OFFSET :offset")
    List<FolkSong> findByRegionWithPage(@Param("region") String region, @Param("limit") Integer limit, @Param("offset") Integer offset);
    
    @Query("SELECT * FROM folk_songs WHERE ethnic = :ethnic AND status = 1 AND audit_status = 1 ORDER BY create_time DESC LIMIT :limit OFFSET :offset")
    List<FolkSong> findByEthnicWithPage(@Param("ethnic") String ethnic, @Param("limit") Integer limit, @Param("offset") Integer offset);
    
    @Query("SELECT * FROM folk_songs WHERE status = 1 AND audit_status = 1 ORDER BY view_count DESC LIMIT :limit")
    List<FolkSong> findHot(@Param("limit") Integer limit);
    
    @Query("SELECT * FROM folk_songs WHERE status = 1 AND audit_status = 1 ORDER BY create_time DESC LIMIT :limit")
    List<FolkSong> findLatest(@Param("limit") Integer limit);
    
    @Modifying
    @Query("UPDATE folk_songs SET view_count = view_count + 1 WHERE id = :id")
    void incrementViewCount(@Param("id") Long id);
    
    @Modifying
    @Query("UPDATE folk_songs SET like_count = like_count + :delta WHERE id = :id")
    void updateLikeCount(@Param("id") Long id, @Param("delta") Integer delta);
    
    @Modifying
    @Query("UPDATE folk_songs SET collect_count = collect_count + :delta WHERE id = :id")
    void updateCollectCount(@Param("id") Long id, @Param("delta") Integer delta);
    
    @Modifying
    @Query("UPDATE folk_songs SET comment_count = comment_count + :delta WHERE id = :id")
    void updateCommentCount(@Param("id") Long id, @Param("delta") Integer delta);
    
    @Modifying
    @Query("UPDATE folk_songs SET audit_status = :auditStatus WHERE id = :id")
    void updateAuditStatus(@Param("id") Long id, @Param("auditStatus") Integer auditStatus);
    
    @Query("SELECT * FROM folk_songs WHERE audit_status = :auditStatus ORDER BY create_time DESC LIMIT :limit OFFSET :offset")
    List<FolkSong> findByAuditStatusWithPage(@Param("auditStatus") Integer auditStatus, @Param("limit") Integer limit, @Param("offset") Integer offset);
    
    @Query("SELECT COUNT(*) FROM folk_songs WHERE audit_status = :auditStatus")
    Long countByAuditStatus(@Param("auditStatus") Integer auditStatus);
    
    @Query("SELECT * FROM folk_songs ORDER BY create_time DESC LIMIT :limit OFFSET :offset")
    List<FolkSong> findAllWithPage(@Param("limit") Integer limit, @Param("offset") Integer offset);
}
