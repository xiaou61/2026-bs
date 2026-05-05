package com.esgreport.mapper;

import com.esgreport.entity.IndicatorLibrary;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface IndicatorLibraryMapper {
    @Select({
        "<script>",
        "SELECT * FROM indicator_library",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (indicator_no LIKE CONCAT('%',#{keyword},'%') OR indicator_name LIKE CONCAT('%',#{keyword},'%') OR dimension_name LIKE CONCAT('%',#{keyword},'%') OR caliber_text LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<IndicatorLibrary> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM indicator_library WHERE id = #{id}")
    IndicatorLibrary selectById(Long id);

    @Insert("INSERT INTO indicator_library (indicator_no, indicator_name, dimension_name, caliber_text, weight_value, status, created_time, updated_time) VALUES (#{indicatorNo}, #{indicatorName}, #{dimensionName}, #{caliberText}, #{weightValue}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(IndicatorLibrary entity);

    @Update("UPDATE indicator_library SET indicator_no = #{indicatorNo}, indicator_name = #{indicatorName}, dimension_name = #{dimensionName}, caliber_text = #{caliberText}, weight_value = #{weightValue}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(IndicatorLibrary entity);

    @Delete("DELETE FROM indicator_library WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM indicator_library")
    long countAll();

    @Update("UPDATE indicator_library SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
