package com.privacyauth.mapper;

import com.privacyauth.entity.RevocationRequest;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RevocationRequestMapper {
    @Select({
        "<script>",
        "SELECT * FROM revocation_request",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (request_no LIKE CONCAT('%',#{keyword},'%') OR subject_name LIKE CONCAT('%',#{keyword},'%') OR auth_no LIKE CONCAT('%',#{keyword},'%') OR reason_text LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<RevocationRequest> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM revocation_request WHERE id = #{id}")
    RevocationRequest selectById(Long id);

    @Insert("INSERT INTO revocation_request (request_no, subject_name, auth_no, reason_text, request_time, status, created_time, updated_time) VALUES (#{requestNo}, #{subjectName}, #{authNo}, #{reasonText}, #{requestTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RevocationRequest entity);

    @Update("UPDATE revocation_request SET request_no = #{requestNo}, subject_name = #{subjectName}, auth_no = #{authNo}, reason_text = #{reasonText}, request_time = #{requestTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(RevocationRequest entity);

    @Delete("DELETE FROM revocation_request WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM revocation_request")
    long countAll();

    @Update("UPDATE revocation_request SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
