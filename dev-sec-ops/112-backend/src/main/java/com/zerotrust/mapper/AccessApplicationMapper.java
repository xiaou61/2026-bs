package com.zerotrust.mapper;

import com.zerotrust.entity.AccessApplication;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AccessApplicationMapper {
    @Select({
        "<script>",
        "SELECT * FROM access_application",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (application_no LIKE CONCAT('%',#{keyword},'%') OR device_name LIKE CONCAT('%',#{keyword},'%') OR employee_name LIKE CONCAT('%',#{keyword},'%') OR resource_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AccessApplication> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM access_application WHERE id = #{id}")
    AccessApplication selectById(Long id);

    @Insert("INSERT INTO access_application (application_no, device_name, employee_name, resource_name, reason_text, status, created_time, updated_time) VALUES (#{applicationNo}, #{deviceName}, #{employeeName}, #{resourceName}, #{reasonText}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AccessApplication entity);

    @Update("UPDATE access_application SET application_no = #{applicationNo}, device_name = #{deviceName}, employee_name = #{employeeName}, resource_name = #{resourceName}, reason_text = #{reasonText}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AccessApplication entity);

    @Delete("DELETE FROM access_application WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM access_application")
    long countAll();

    @Update("UPDATE access_application SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
