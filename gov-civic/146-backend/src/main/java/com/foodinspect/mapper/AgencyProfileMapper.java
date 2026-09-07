package com.foodinspect.mapper;

import com.foodinspect.entity.AgencyProfile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AgencyProfileMapper {
    @Select({
        "<script>",
        "SELECT * FROM agency_profile",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (agency_no LIKE CONCAT('%',#{keyword},'%') OR agency_name LIKE CONCAT('%',#{keyword},'%') OR qualification_level LIKE CONCAT('%',#{keyword},'%') OR specialty_area LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AgencyProfile> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM agency_profile WHERE id = #{id}")
    AgencyProfile selectById(Long id);

    @Insert("INSERT INTO agency_profile (agency_no, agency_name, qualification_level, entry_date, specialty_area, status, created_time, updated_time) VALUES (#{agencyNo}, #{agencyName}, #{qualificationLevel}, #{entryDate}, #{specialtyArea}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AgencyProfile entity);

    @Update("UPDATE agency_profile SET agency_no = #{agencyNo}, agency_name = #{agencyName}, qualification_level = #{qualificationLevel}, entry_date = #{entryDate}, specialty_area = #{specialtyArea}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AgencyProfile entity);

    @Delete("DELETE FROM agency_profile WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM agency_profile")
    long countAll();

    @Update("UPDATE agency_profile SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}






