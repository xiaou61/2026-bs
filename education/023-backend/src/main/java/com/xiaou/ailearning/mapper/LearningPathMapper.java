package com.xiaou.ailearning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.ailearning.entity.LearningPath;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LearningPathMapper extends BaseMapper<LearningPath> {
    
    @Select("SELECT * FROM learning_path WHERE user_id = #{userId} AND is_active = true")
    List<LearningPath> selectActivePathsByUserId(Long userId);
    
    @Select("SELECT * FROM learning_path WHERE user_id = #{userId}")
    List<LearningPath> selectByUserId(Long userId);
    
    @Select("SELECT * FROM learning_path WHERE target_knowledge_point_id = #{knowledgePointId}")
    List<LearningPath> selectByTargetKnowledgePoint(Long knowledgePointId);
}