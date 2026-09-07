package com.outpatientexam.mapper;

import com.outpatientexam.entity.ExamDepartment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ExamDepartmentMapper {
    @Select({
            "<script>",
            "SELECT * FROM exam_department",
            "<where>",
            "<if test='keyword != null and keyword != \"\"'> AND (department_no LIKE CONCAT('%',#{keyword},'%') OR department_name LIKE CONCAT('%',#{keyword},'%') OR device_name LIKE CONCAT('%',#{keyword},'%') OR waiting_area LIKE CONCAT('%',#{keyword},'%'))</if>",
            "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"
    })
    List<ExamDepartment> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM exam_department WHERE id = #{id}")
    ExamDepartment selectById(Long id);

    @Insert("INSERT INTO exam_department (department_no, department_name, device_name, open_time, waiting_area, status, created_time, updated_time) VALUES (#{departmentNo}, #{departmentName}, #{deviceName}, #{openTime}, #{waitingArea}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ExamDepartment entity);

    @Update("UPDATE exam_department SET department_no = #{departmentNo}, department_name = #{departmentName}, device_name = #{deviceName}, open_time = #{openTime}, waiting_area = #{waitingArea}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ExamDepartment entity);

    @Delete("DELETE FROM exam_department WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM exam_department")
    long countAll();

    @Update("UPDATE exam_department SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
