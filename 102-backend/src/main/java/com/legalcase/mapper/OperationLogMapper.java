package com.legalcase.mapper;

import com.legalcase.entity.OperationLog;
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
    @Select({"<script>",
            "SELECT * FROM operation_log",
            "<where>",
            "<if test=\"keyword != null and keyword != \'\'\"> AND (username LIKE CONCAT('%',#{keyword},'%') OR module_name LIKE CONCAT('%',#{keyword},'%') OR action_type LIKE CONCAT('%',#{keyword},'%') OR description LIKE CONCAT('%',#{keyword},'%')) </if>",
            "<if test=\"moduleName != null and moduleName != \'\'\"> AND module_name = #{moduleName} </if>",
            "<if test=\"role != null and role != \'\'\"> AND role = #{role} </if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"})
    List<OperationLog> selectPage(@Param("keyword") String keyword, @Param("moduleName") String moduleName, @Param("role") String role);

    @Select("SELECT * FROM operation_log WHERE id=#{id}")
    OperationLog selectById(Long id);

    @Insert("INSERT INTO operation_log(user_id, username, role, module_name, action_type, description, create_time) VALUES(#{userId}, #{username}, #{role}, #{moduleName}, #{actionType}, #{description}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(OperationLog entity);

    @Update("UPDATE operation_log SET user_id=#{userId}, username=#{username}, role=#{role}, module_name=#{moduleName}, action_type=#{actionType}, description=#{description}, create_time=#{createTime} WHERE id=#{id}")
    int updateById(OperationLog entity);

    @Delete("DELETE FROM operation_log WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM operation_log")
    long countAll();
}
