package com.folksong.platform.repository;

import com.folksong.platform.entity.Favorite;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends CrudRepository<Favorite, Long> {
    
    Optional<Favorite> findByUserIdAndSongId(Long userId, Long songId);
    
    boolean existsByUserIdAndSongId(Long userId, Long songId);
    
    void deleteByUserIdAndSongId(Long userId, Long songId);
    
    @Query("SELECT * FROM favorites WHERE user_id = :userId ORDER BY create_time DESC LIMIT :limit OFFSET :offset")
    List<Favorite> findByUserIdWithPage(@Param("userId") Long userId, @Param("limit") Integer limit, @Param("offset") Integer offset);
    
    @Query("SELECT COUNT(*) FROM favorites WHERE user_id = :userId")
    Long countByUserId(@Param("userId") Long userId);
}
