package com.privacyauth.mapper;

import com.privacyauth.entity.AccessLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AccessLogMapper {
    @Select({
        "<script>",
        "SELECT * FROM access_log",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (log_no LIKE CONCAT('%',#{keyword},'%') OR operator_name LIKE CONCAT('%',#{keyword},'%') OR subject_name LIKE CONCAT('%',#{keyword},'%') OR access_result LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AccessLog> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM access_log WHERE id = #{id}")
    AccessLog selectById(Long id);

    @Insert("INSERT INTO access_log (log_no, operator_name, subject_name, item_name, access_result, access_time, status, created_time, updated_time) VALUES (#{logNo}, #{operatorName}, #{subjectName}, #{itemName}, #{accessResult}, #{accessTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AccessLog entity);

    @Update("UPDATE access_log SET log_no = #{logNo}, operator_name = #{operatorName}, subject_name = #{subjectName}, item_name = #{itemName}, access_result = #{accessResult}, access_time = #{accessTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AccessLog entity);

    @Delete("DELETE FROM access_log WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM access_log")
    long countAll();

    @Update("UPDATE access_log SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
