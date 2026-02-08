package com.movie.mapper;

import com.movie.entity.Favorite;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface FavoriteMapper {
    List<Favorite> selectByUserId(Long userId);
    Favorite selectByUserAndMovie(@Param("userId") Long userId, @Param("movieId") Long movieId);
    int insert(Favorite favorite);
    int deleteByUserAndMovie(@Param("userId") Long userId, @Param("movieId") Long movieId);
}
