package com.eldercare.mapper;

import com.eldercare.entity.ElderProfile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ElderProfileMapper {
    @Select({
        "<script>",
        "SELECT * FROM elder_profile",
        "<where>",
        "<if test='keyword != null && keyword != \"\"'> AND (elder_no LIKE CONCAT('%',#{keyword},'%') OR elder_name LIKE CONCAT('%',#{keyword},'%') OR age_group LIKE CONCAT('%',#{keyword},'%') OR home_address LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null && status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ElderProfile> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM elder_profile WHERE id = #{id}")
    ElderProfile selectById(Long id);

    @Insert("INSERT INTO elder_profile (elder_no, elder_name, age_group, home_address, care_level, status, created_time, updated_time) VALUES (#{elderNo}, #{elderName}, #{ageGroup}, #{homeAddress}, #{careLevel}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ElderProfile entity);

    @Update("UPDATE elder_profile SET elder_no = #{elderNo}, elder_name = #{elderName}, age_group = #{ageGroup}, home_address = #{homeAddress}, care_level = #{careLevel}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ElderProfile entity);

    @Delete("DELETE FROM elder_profile WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM elder_profile")
    long countAll();

    @Update("UPDATE elder_profile SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
