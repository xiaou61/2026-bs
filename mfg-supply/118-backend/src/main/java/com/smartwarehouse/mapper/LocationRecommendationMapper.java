package com.smartwarehouse.mapper;

import com.smartwarehouse.entity.LocationRecommendation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface LocationRecommendationMapper {
    @Select({
        "<script>",
        "SELECT * FROM location_recommendation",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (recommend_no LIKE CONCAT('%',#{keyword},'%') OR item_name LIKE CONCAT('%',#{keyword},'%') OR location_no LIKE CONCAT('%',#{keyword},'%') OR reason_text LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<LocationRecommendation> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM location_recommendation WHERE id = #{id}")
    LocationRecommendation selectById(Long id);

    @Insert("INSERT INTO location_recommendation (recommend_no, item_name, location_no, match_score, reason_text, operator_name, status, created_time, updated_time) VALUES (#{recommendNo}, #{itemName}, #{locationNo}, #{matchScore}, #{reasonText}, #{operatorName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(LocationRecommendation entity);

    @Update("UPDATE location_recommendation SET recommend_no = #{recommendNo}, item_name = #{itemName}, location_no = #{locationNo}, match_score = #{matchScore}, reason_text = #{reasonText}, operator_name = #{operatorName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(LocationRecommendation entity);

    @Delete("DELETE FROM location_recommendation WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM location_recommendation")
    long countAll();

    @Update("UPDATE location_recommendation SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
