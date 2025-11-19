package com.xiaou.ailearning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.ailearning.entity.UserBehavior;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserBehaviorMapper extends BaseMapper<UserBehavior> {
    
    @Select("SELECT * FROM user_behavior WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<UserBehavior> selectByUserId(Long userId);
    
    @Select("SELECT * FROM user_behavior WHERE behavior_type = #{behaviorType}")
    List<UserBehavior> selectByBehaviorType(String behaviorType);
    
    @Select("SELECT * FROM user_behavior WHERE session_id = #{sessionId}")
    List<UserBehavior> selectBySessionId(String sessionId);
}