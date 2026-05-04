package com.devopsrelease.mapper;

import com.devopsrelease.entity.OperationLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OperationLogMapper {
    @Select({
        "<script>",
        "SELECT * FROM operation_log",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (username LIKE CONCAT('%',#{keyword},'%') OR action LIKE CONCAT('%',#{keyword},'%') OR target_type LIKE CONCAT('%',#{keyword},'%') OR detail LIKE CONCAT('%',#{keyword},'%'))</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<OperationLog> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM operation_log WHERE id = #{id}")
    OperationLog selectById(Long id);

    @Insert("INSERT INTO operation_log (username, action, target_type, target_id, detail, ip_address, created_time, updated_time) VALUES (#{username}, #{action}, #{targetType}, #{targetId}, #{detail}, #{ipAddress}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(OperationLog entity);

    @Update("UPDATE operation_log SET username = #{username}, action = #{action}, target_type = #{targetType}, target_id = #{targetId}, detail = #{detail}, ip_address = #{ipAddress}, updated_time = NOW() WHERE id = #{id}")
    int update(OperationLog entity);

    @Delete("DELETE FROM operation_log WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM operation_log")
    long countAll();


}
