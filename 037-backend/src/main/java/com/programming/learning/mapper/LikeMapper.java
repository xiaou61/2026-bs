package com.programming.learning.mapper;

import com.programming.learning.entity.Like;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 点赞Mapper接口
 */
@Mapper
public interface LikeMapper {

    int insert(Like like);

    int delete(@Param("userId") Long userId, @Param("targetType") String targetType, @Param("targetId") Long targetId);

    Like selectByUserAndTarget(@Param("userId") Long userId, @Param("targetType") String targetType, @Param("targetId") Long targetId);

    Long countByTarget(@Param("targetType") String targetType, @Param("targetId") Long targetId);
}
