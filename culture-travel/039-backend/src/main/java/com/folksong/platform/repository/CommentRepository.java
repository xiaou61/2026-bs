package com.folksong.platform.repository;

import com.folksong.platform.entity.Comment;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    
    @Query("SELECT * FROM comments WHERE song_id = :songId AND status = 1 ORDER BY create_time DESC LIMIT :limit OFFSET :offset")
    List<Comment> findBySongIdWithPage(@Param("songId") Long songId, @Param("limit") Integer limit, @Param("offset") Integer offset);
    
    @Query("SELECT COUNT(*) FROM comments WHERE song_id = :songId AND status = 1")
    Long countBySongId(@Param("songId") Long songId);
    
    @Query("SELECT * FROM comments WHERE song_id = :songId AND parent_id IS NULL AND status = 1 ORDER BY create_time DESC LIMIT :limit OFFSET :offset")
    List<Comment> findRootCommentsBySongId(@Param("songId") Long songId, @Param("limit") Integer limit, @Param("offset") Integer offset);
    
    @Query("SELECT * FROM comments WHERE parent_id = :parentId AND status = 1 ORDER BY create_time ASC")
    List<Comment> findRepliesByParentId(@Param("parentId") Long parentId);
    
    @Query("SELECT * FROM comments WHERE user_id = :userId ORDER BY create_time DESC LIMIT :limit OFFSET :offset")
    List<Comment> findByUserIdWithPage(@Param("userId") Long userId, @Param("limit") Integer limit, @Param("offset") Integer offset);
    
    @Query("SELECT COUNT(*) FROM comments WHERE user_id = :userId")
    Long countByUserId(@Param("userId") Long userId);
    
    @Modifying
    @Query("UPDATE comments SET status = :status WHERE id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);
    
    @Query("SELECT * FROM comments ORDER BY create_time DESC LIMIT :limit OFFSET :offset")
    List<Comment> findAllWithPage(@Param("limit") Integer limit, @Param("offset") Integer offset);
}
