package com.programming.learning.mapper;

import com.programming.learning.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收藏Mapper接口
 */
@Mapper
public interface FavoriteMapper {

    int insert(Favorite favorite);

    int delete(@Param("userId") Long userId, @Param("targetType") String targetType, @Param("targetId") Long targetId);

    Favorite selectByUserAndTarget(@Param("userId") Long userId, @Param("targetType") String targetType, @Param("targetId") Long targetId);

    List<Favorite> selectByUserId(@Param("userId") Long userId, @Param("targetType") String targetType);

    Long countByTarget(@Param("targetType") String targetType, @Param("targetId") Long targetId);
}
