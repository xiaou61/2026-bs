package com.accessibletravel.mapper;

import com.accessibletravel.entity.VolunteerProfile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface VolunteerProfileMapper {
    @Select({
        "<script>",
        "SELECT * FROM volunteer_profile",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (volunteer_no LIKE CONCAT('%',#{keyword},'%') OR volunteer_name LIKE CONCAT('%',#{keyword},'%') OR service_expertise LIKE CONCAT('%',#{keyword},'%') OR service_area LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<VolunteerProfile> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM volunteer_profile WHERE id = #{id}")
    VolunteerProfile selectById(Long id);

    @Insert("INSERT INTO volunteer_profile (volunteer_no, volunteer_name, service_expertise, join_time, service_area, status, created_time, updated_time) VALUES (#{volunteerNo}, #{volunteerName}, #{serviceExpertise}, #{joinTime}, #{serviceArea}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(VolunteerProfile entity);

    @Update("UPDATE volunteer_profile SET volunteer_no = #{volunteerNo}, volunteer_name = #{volunteerName}, service_expertise = #{serviceExpertise}, join_time = #{joinTime}, service_area = #{serviceArea}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(VolunteerProfile entity);

    @Delete("DELETE FROM volunteer_profile WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM volunteer_profile")
    long countAll();

    @Update("UPDATE volunteer_profile SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}

