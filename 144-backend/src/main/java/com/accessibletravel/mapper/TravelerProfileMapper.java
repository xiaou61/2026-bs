package com.accessibletravel.mapper;

import com.accessibletravel.entity.TravelerProfile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TravelerProfileMapper {
    @Select({
        "<script>",
        "SELECT * FROM traveler_profile",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (traveler_no LIKE CONCAT('%',#{keyword},'%') OR traveler_name LIKE CONCAT('%',#{keyword},'%') OR phone LIKE CONCAT('%',#{keyword},'%') OR assistance_need LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<TravelerProfile> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM traveler_profile WHERE id = #{id}")
    TravelerProfile selectById(Long id);

    @Insert("INSERT INTO traveler_profile (traveler_no, traveler_name, phone, assistance_need, travel_preference, status, created_time, updated_time) VALUES (#{travelerNo}, #{travelerName}, #{phone}, #{assistanceNeed}, #{travelPreference}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(TravelerProfile entity);

    @Update("UPDATE traveler_profile SET traveler_no = #{travelerNo}, traveler_name = #{travelerName}, phone = #{phone}, assistance_need = #{assistanceNeed}, travel_preference = #{travelPreference}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(TravelerProfile entity);

    @Delete("DELETE FROM traveler_profile WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM traveler_profile")
    long countAll();

    @Update("UPDATE traveler_profile SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}

