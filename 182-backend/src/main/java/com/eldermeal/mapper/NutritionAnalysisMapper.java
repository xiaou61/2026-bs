package com.eldermeal.mapper;

import com.eldermeal.entity.NutritionAnalysis;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface NutritionAnalysisMapper {
    @Select({"<script>", "SELECT * FROM nutrition_analysis", "<where>", "<if test='keyword != null and keyword != \"\"'> AND (record_no LIKE CONCAT('%',#{keyword},'%') OR record_name LIKE CONCAT('%',#{keyword},'%') OR category LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>", "<if test='status != null and status != \"\"'> AND status = #{status}</if>", "</where>", "ORDER BY id DESC", "</script>"})
    List<NutritionAnalysis> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Insert("INSERT INTO nutrition_analysis (record_no,record_name,category,owner_name,plan_time,status,remark,created_time,updated_time) VALUES (#{recordNo},#{recordName},#{category},#{ownerName},#{planTime},#{status},#{remark},NOW(),NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(NutritionAnalysis entity);

    @Update("UPDATE nutrition_analysis SET record_no=#{recordNo},record_name=#{recordName},category=#{category},owner_name=#{ownerName},plan_time=#{planTime},status=#{status},remark=#{remark},updated_time=NOW() WHERE id=#{id}")
    int update(NutritionAnalysis entity);

    @Delete("DELETE FROM nutrition_analysis WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM nutrition_analysis")
    long countAll();

    @Update("UPDATE nutrition_analysis SET status=#{status},updated_time=NOW() WHERE id=#{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
