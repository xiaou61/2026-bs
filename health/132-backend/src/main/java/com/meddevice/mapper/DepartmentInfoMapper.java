package com.meddevice.mapper;

import com.meddevice.entity.DepartmentInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DepartmentInfoMapper {
    @Select({
        "<script>",
        "SELECT * FROM department_info",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (dept_no LIKE CONCAT('%',#{keyword},'%') OR dept_name LIKE CONCAT('%',#{keyword},'%') OR floor_name LIKE CONCAT('%',#{keyword},'%') OR contact_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<DepartmentInfo> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM department_info WHERE id = #{id}")
    DepartmentInfo selectById(Long id);

    @Insert("INSERT INTO department_info (dept_no, dept_name, floor_name, contact_name, phone_number, status, created_time, updated_time) VALUES (#{deptNo}, #{deptName}, #{floorName}, #{contactName}, #{phoneNumber}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(DepartmentInfo entity);

    @Update("UPDATE department_info SET dept_no = #{deptNo}, dept_name = #{deptName}, floor_name = #{floorName}, contact_name = #{contactName}, phone_number = #{phoneNumber}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(DepartmentInfo entity);

    @Delete("DELETE FROM department_info WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM department_info")
    long countAll();

    @Update("UPDATE department_info SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
