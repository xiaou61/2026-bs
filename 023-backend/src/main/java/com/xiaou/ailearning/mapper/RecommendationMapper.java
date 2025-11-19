package com.xiaou.ailearning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.ailearning.entity.Recommendation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RecommendationMapper extends BaseMapper<Recommendation> {
    
    @Select("SELECT * FROM recommendation WHERE user_id = #{userId} ORDER BY recommendation_score DESC")
    List<Recommendation> selectByUserId(Long userId);
    
    @Select("SELECT * FROM recommendation WHERE content_type = #{contentType}")
    List<Recommendation> selectByContentType(Integer contentType);
    
    @Select("SELECT * FROM recommendation WHERE recommendation_type = #{recommendationType}")
    List<Recommendation> selectByRecommendationType(Integer recommendationType);
}