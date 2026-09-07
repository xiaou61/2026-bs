package com.greenhouse.mapper;

import com.greenhouse.entity.GreenhouseProfile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GreenhouseProfileMapper {
    @Select({
        "<script>",
        "SELECT * FROM greenhouse_profile",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (greenhouse_no LIKE CONCAT('%',#{keyword},'%') OR greenhouse_name LIKE CONCAT('%',#{keyword},'%') OR base_name LIKE CONCAT('%',#{keyword},'%') OR manager_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<GreenhouseProfile> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM greenhouse_profile WHERE id = #{id}")
    GreenhouseProfile selectById(Long id);

    @Insert("INSERT INTO greenhouse_profile (greenhouse_no, greenhouse_name, base_name, area_size, manager_name, status, created_time, updated_time) VALUES (#{greenhouseNo}, #{greenhouseName}, #{baseName}, #{areaSize}, #{managerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(GreenhouseProfile entity);

    @Update("UPDATE greenhouse_profile SET greenhouse_no = #{greenhouseNo}, greenhouse_name = #{greenhouseName}, base_name = #{baseName}, area_size = #{areaSize}, manager_name = #{managerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(GreenhouseProfile entity);

    @Delete("DELETE FROM greenhouse_profile WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM greenhouse_profile")
    long countAll();

    @Update("UPDATE greenhouse_profile SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
