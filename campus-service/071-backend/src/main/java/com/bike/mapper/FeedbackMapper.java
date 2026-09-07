package com.bike.mapper;

import com.bike.entity.Feedback;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface FeedbackMapper {
    List<Feedback> findList(@Param("status") Integer status, @Param("type") Integer type);
    List<Feedback> findByUserId(@Param("userId") Long userId);
    Feedback findById(@Param("id") Long id);
    int insert(Feedback feedback);
    int updateReply(@Param("id") Long id, @Param("reply") String reply);
}
