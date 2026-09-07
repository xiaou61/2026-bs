package com.homeenergy.mapper;

import com.homeenergy.entity.HomeProfile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface HomeProfileMapper {
    @Select({
        "<script>",
        "SELECT * FROM home_profile",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (home_no LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%') OR city_name LIKE CONCAT('%',#{keyword},'%') OR community_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<HomeProfile> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM home_profile WHERE id = #{id}")
    HomeProfile selectById(Long id);

    @Insert("INSERT INTO home_profile (home_no, owner_name, city_name, community_name, area_size, member_count, status, created_time, updated_time) VALUES (#{homeNo}, #{ownerName}, #{cityName}, #{communityName}, #{areaSize}, #{memberCount}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(HomeProfile entity);

    @Update("UPDATE home_profile SET home_no = #{homeNo}, owner_name = #{ownerName}, city_name = #{cityName}, community_name = #{communityName}, area_size = #{areaSize}, member_count = #{memberCount}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(HomeProfile entity);

    @Delete("DELETE FROM home_profile WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM home_profile")
    long countAll();

    @Update("UPDATE home_profile SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
