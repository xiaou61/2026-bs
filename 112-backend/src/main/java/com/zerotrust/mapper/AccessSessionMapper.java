package com.zerotrust.mapper;

import com.zerotrust.entity.AccessSession;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AccessSessionMapper {
    @Select({
        "<script>",
        "SELECT * FROM access_session",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (session_no LIKE CONCAT('%',#{keyword},'%') OR device_name LIKE CONCAT('%',#{keyword},'%') OR employee_name LIKE CONCAT('%',#{keyword},'%') OR resource_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AccessSession> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM access_session WHERE id = #{id}")
    AccessSession selectById(Long id);

    @Insert("INSERT INTO access_session (session_no, device_name, employee_name, resource_name, source_ip, login_time, status, created_time, updated_time) VALUES (#{sessionNo}, #{deviceName}, #{employeeName}, #{resourceName}, #{sourceIp}, #{loginTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AccessSession entity);

    @Update("UPDATE access_session SET session_no = #{sessionNo}, device_name = #{deviceName}, employee_name = #{employeeName}, resource_name = #{resourceName}, source_ip = #{sourceIp}, login_time = #{loginTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AccessSession entity);

    @Delete("DELETE FROM access_session WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM access_session")
    long countAll();

    @Update("UPDATE access_session SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
