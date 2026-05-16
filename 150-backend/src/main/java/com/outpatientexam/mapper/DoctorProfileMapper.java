package com.outpatientexam.mapper;

import com.outpatientexam.entity.DoctorProfile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DoctorProfileMapper {
    @Select({
            "<script>",
            "SELECT * FROM doctor_profile",
            "<where>",
            "<if test='keyword != null and keyword != \"\"'> AND (doctor_no LIKE CONCAT('%',#{keyword},'%') OR doctor_name LIKE CONCAT('%',#{keyword},'%') OR phone LIKE CONCAT('%',#{keyword},'%') OR department_name LIKE CONCAT('%',#{keyword},'%') OR title_level LIKE CONCAT('%',#{keyword},'%'))</if>",
            "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"
    })
    List<DoctorProfile> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM doctor_profile WHERE id = #{id}")
    DoctorProfile selectById(Long id);

    @Insert("INSERT INTO doctor_profile (doctor_no, doctor_name, phone, department_name, title_level, status, created_time, updated_time) VALUES (#{doctorNo}, #{doctorName}, #{phone}, #{departmentName}, #{titleLevel}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(DoctorProfile entity);

    @Update("UPDATE doctor_profile SET doctor_no = #{doctorNo}, doctor_name = #{doctorName}, phone = #{phone}, department_name = #{departmentName}, title_level = #{titleLevel}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(DoctorProfile entity);

    @Delete("DELETE FROM doctor_profile WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM doctor_profile")
    long countAll();

    @Update("UPDATE doctor_profile SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
