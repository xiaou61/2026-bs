package com.xiaou.ailearning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.ailearning.entity.KnowledgeRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface KnowledgeRelationMapper extends BaseMapper<KnowledgeRelation> {
    
    @Select("SELECT * FROM knowledge_relation WHERE from_knowledge_id = #{knowledgeId}")
    List<KnowledgeRelation> selectRelationsByFromId(Long knowledgeId);
    
    @Select("SELECT * FROM knowledge_relation WHERE to_knowledge_id = #{knowledgeId}")
    List<KnowledgeRelation> selectRelationsByToId(Long knowledgeId);
    
    @Select("SELECT * FROM knowledge_relation WHERE relation_type = #{relationType}")
    List<KnowledgeRelation> selectByRelationType(Integer relationType);
}