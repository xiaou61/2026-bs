package com.cloudcost.mapper;

import com.cloudcost.entity.OptimizationAdvice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OptimizationAdviceMapper {
    @Select({
        "<script>",
        "SELECT * FROM optimization_advice",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (advice_no LIKE CONCAT('%',#{keyword},'%') OR resource_name LIKE CONCAT('%',#{keyword},'%') OR advice_type LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<OptimizationAdvice> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM optimization_advice WHERE id = #{id}")
    OptimizationAdvice selectById(Long id);

    @Insert("INSERT INTO optimization_advice (advice_no, resource_name, advice_type, expected_saving, owner_name, generated_time, status, created_time, updated_time) VALUES (#{adviceNo}, #{resourceName}, #{adviceType}, #{expectedSaving}, #{ownerName}, #{generatedTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(OptimizationAdvice entity);

    @Update("UPDATE optimization_advice SET advice_no = #{adviceNo}, resource_name = #{resourceName}, advice_type = #{adviceType}, expected_saving = #{expectedSaving}, owner_name = #{ownerName}, generated_time = #{generatedTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(OptimizationAdvice entity);

    @Delete("DELETE FROM optimization_advice WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM optimization_advice")
    long countAll();

    @Update("UPDATE optimization_advice SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
