package com.eldercare.mapper;

import com.eldercare.entity.CaregiverProfile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CaregiverProfileMapper {
    @Select({
        "<script>",
        "SELECT * FROM caregiver_profile",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (caregiver_no LIKE CONCAT('%',#{keyword},'%') OR caregiver_name LIKE CONCAT('%',#{keyword},'%') OR phone_number LIKE CONCAT('%',#{keyword},'%') OR organization_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<CaregiverProfile> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM caregiver_profile WHERE id = #{id}")
    CaregiverProfile selectById(Long id);

    @Insert("INSERT INTO caregiver_profile (caregiver_no, caregiver_name, phone_number, caregiver_level, organization_name, status, created_time, updated_time) VALUES (#{caregiverNo}, #{caregiverName}, #{phoneNumber}, #{caregiverLevel}, #{organizationName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CaregiverProfile entity);

    @Update("UPDATE caregiver_profile SET caregiver_no = #{caregiverNo}, caregiver_name = #{caregiverName}, phone_number = #{phoneNumber}, caregiver_level = #{caregiverLevel}, organization_name = #{organizationName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(CaregiverProfile entity);

    @Delete("DELETE FROM caregiver_profile WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM caregiver_profile")
    long countAll();

    @Update("UPDATE caregiver_profile SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
