package com.folksong.platform.repository;

import com.folksong.platform.entity.Like;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LikeRepository extends CrudRepository<Like, Long> {
    
    Optional<Like> findByUserIdAndSongId(Long userId, Long songId);
    
    boolean existsByUserIdAndSongId(Long userId, Long songId);
    
    void deleteByUserIdAndSongId(Long userId, Long songId);
}
