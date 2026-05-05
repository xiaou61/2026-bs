package com.worksite.mapper;

import com.worksite.entity.SafetyInspector;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SafetyInspectorMapper {
    @Select({
        "<script>",
        "SELECT * FROM safety_inspector",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (inspector_no LIKE CONCAT('%',#{keyword},'%') OR inspector_name LIKE CONCAT('%',#{keyword},'%') OR certificate_no LIKE CONCAT('%',#{keyword},'%') OR team_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<SafetyInspector> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM safety_inspector WHERE id = #{id}")
    SafetyInspector selectById(Long id);

    @Insert("INSERT INTO safety_inspector (inspector_no, inspector_name, certificate_no, team_name, phone, specialty, status, created_time, updated_time) VALUES (#{inspectorNo}, #{inspectorName}, #{certificateNo}, #{teamName}, #{phone}, #{specialty}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SafetyInspector entity);

    @Update("UPDATE safety_inspector SET inspector_no = #{inspectorNo}, inspector_name = #{inspectorName}, certificate_no = #{certificateNo}, team_name = #{teamName}, phone = #{phone}, specialty = #{specialty}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(SafetyInspector entity);

    @Delete("DELETE FROM safety_inspector WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM safety_inspector")
    long countAll();

    @Update("UPDATE safety_inspector SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
