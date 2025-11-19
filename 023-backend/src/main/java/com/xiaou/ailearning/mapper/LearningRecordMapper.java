package com.xiaou.ailearning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.ailearning.entity.LearningRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LearningRecordMapper extends BaseMapper<LearningRecord> {
    
    @Select("SELECT * FROM learning_record WHERE user_id = #{userId}")
    List<LearningRecord> selectByUserId(Long userId);
    
    @Select("SELECT * FROM learning_record WHERE course_id = #{courseId}")
    List<LearningRecord> selectByCourseId(Long courseId);
    
    @Select("SELECT * FROM learning_record WHERE knowledge_point_id = #{knowledgePointId}")
    List<LearningRecord> selectByKnowledgePointId(Long knowledgePointId);
    
    @Select("SELECT * FROM learning_record WHERE user_id = #{userId} AND course_id = #{courseId}")
    List<LearningRecord> selectByUserAndCourse(Long userId, Long courseId);
}