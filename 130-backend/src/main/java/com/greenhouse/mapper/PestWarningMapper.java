package com.greenhouse.mapper;

import com.greenhouse.entity.PestWarning;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PestWarningMapper {
    @Select({
        "<script>",
        "SELECT * FROM pest_warning",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (warning_no LIKE CONCAT('%',#{keyword},'%') OR greenhouse_no LIKE CONCAT('%',#{keyword},'%') OR pest_type LIKE CONCAT('%',#{keyword},'%') OR warning_level LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<PestWarning> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM pest_warning WHERE id = #{id}")
    PestWarning selectById(Long id);

    @Insert("INSERT INTO pest_warning (warning_no, greenhouse_no, pest_type, warning_level, handler_name, status, created_time, updated_time) VALUES (#{warningNo}, #{greenhouseNo}, #{pestType}, #{warningLevel}, #{handlerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(PestWarning entity);

    @Update("UPDATE pest_warning SET warning_no = #{warningNo}, greenhouse_no = #{greenhouseNo}, pest_type = #{pestType}, warning_level = #{warningLevel}, handler_name = #{handlerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(PestWarning entity);

    @Delete("DELETE FROM pest_warning WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM pest_warning")
    long countAll();

    @Update("UPDATE pest_warning SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
