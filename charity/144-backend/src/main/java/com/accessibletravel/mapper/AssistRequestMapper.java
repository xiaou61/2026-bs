package com.accessibletravel.mapper;

import com.accessibletravel.entity.AssistRequest;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AssistRequestMapper {
    @Select({
        "<script>",
        "SELECT * FROM assist_request",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (request_no LIKE CONCAT('%',#{keyword},'%') OR request_title LIKE CONCAT('%',#{keyword},'%') OR departure_point LIKE CONCAT('%',#{keyword},'%') OR destination LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AssistRequest> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM assist_request WHERE id = #{id}")
    AssistRequest selectById(Long id);

    @Insert("INSERT INTO assist_request (request_no, request_title, departure_point, request_time, destination, status, created_time, updated_time) VALUES (#{requestNo}, #{requestTitle}, #{departurePoint}, #{requestTime}, #{destination}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AssistRequest entity);

    @Update("UPDATE assist_request SET request_no = #{requestNo}, request_title = #{requestTitle}, departure_point = #{departurePoint}, request_time = #{requestTime}, destination = #{destination}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AssistRequest entity);

    @Delete("DELETE FROM assist_request WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM assist_request")
    long countAll();

    @Update("UPDATE assist_request SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}

