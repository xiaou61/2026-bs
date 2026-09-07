package com.xiaou.ailearning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.ailearning.entity.KnowledgePoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface KnowledgePointMapper extends BaseMapper<KnowledgePoint> {
    
    @Select("SELECT * FROM knowledge_point WHERE course_id = #{courseId}")
    List<KnowledgePoint> selectByCourseId(Long courseId);
    
    @Select("SELECT * FROM knowledge_point WHERE parent_id = #{parentId}")
    List<KnowledgePoint> selectByParentId(Long parentId);
    
    @Select("SELECT * FROM knowledge_point WHERE keywords LIKE CONCAT('%', #{keyword}, '%')")
    List<KnowledgePoint> selectByKeyword(String keyword);
}