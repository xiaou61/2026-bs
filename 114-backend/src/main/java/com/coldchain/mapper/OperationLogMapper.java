package com.coldchain.mapper;

import com.coldchain.entity.OperationLog;
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
        "<if test='keyword != null and keyword != \"\"'> AND (operator_name LIKE CONCAT('%',#{keyword},'%') OR module_name LIKE CONCAT('%',#{keyword},'%') OR action_type LIKE CONCAT('%',#{keyword},'%') OR target_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<OperationLog> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM operation_log WHERE id = #{id}")
    OperationLog selectById(Long id);

    @Insert("INSERT INTO operation_log (operator_name, module_name, action_type, target_name, detail, ip_address, status, created_time, updated_time) VALUES (#{operatorName}, #{moduleName}, #{actionType}, #{targetName}, #{detail}, #{ipAddress}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(OperationLog entity);

    @Update("UPDATE operation_log SET operator_name = #{operatorName}, module_name = #{moduleName}, action_type = #{actionType}, target_name = #{targetName}, detail = #{detail}, ip_address = #{ipAddress}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(OperationLog entity);

    @Delete("DELETE FROM operation_log WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM operation_log")
    long countAll();

    @Update("UPDATE operation_log SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

}
