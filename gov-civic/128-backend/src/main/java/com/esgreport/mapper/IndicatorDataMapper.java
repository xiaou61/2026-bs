package com.esgreport.mapper;

import com.esgreport.entity.IndicatorData;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface IndicatorDataMapper {
    @Select({
        "<script>",
        "SELECT * FROM indicator_data",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (data_no LIKE CONCAT('%',#{keyword},'%') OR indicator_name LIKE CONCAT('%',#{keyword},'%') OR company_name LIKE CONCAT('%',#{keyword},'%') OR unit_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<IndicatorData> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM indicator_data WHERE id = #{id}")
    IndicatorData selectById(Long id);

    @Insert("INSERT INTO indicator_data (data_no, indicator_name, company_name, data_value, unit_name, status, created_time, updated_time) VALUES (#{dataNo}, #{indicatorName}, #{companyName}, #{dataValue}, #{unitName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(IndicatorData entity);

    @Update("UPDATE indicator_data SET data_no = #{dataNo}, indicator_name = #{indicatorName}, company_name = #{companyName}, data_value = #{dataValue}, unit_name = #{unitName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(IndicatorData entity);

    @Delete("DELETE FROM indicator_data WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM indicator_data")
    long countAll();

    @Update("UPDATE indicator_data SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
