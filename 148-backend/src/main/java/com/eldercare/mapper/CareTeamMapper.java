package com.eldercare.mapper;

import com.eldercare.entity.CareTeam;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CareTeamMapper {
    @Select({
        "<script>",
        "SELECT * FROM care_team",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (team_no LIKE CONCAT('%',#{keyword},'%') OR team_name LIKE CONCAT('%',#{keyword},'%') OR service_expertise LIKE CONCAT('%',#{keyword},'%') OR service_area LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<CareTeam> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM care_team WHERE id = #{id}")
    CareTeam selectById(Long id);

    @Insert("INSERT INTO care_team (team_no, team_name, service_expertise, build_time, service_area, status, created_time, updated_time) VALUES (#{teamNo}, #{teamName}, #{serviceExpertise}, #{buildTime}, #{serviceArea}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CareTeam entity);

    @Update("UPDATE care_team SET team_no = #{teamNo}, team_name = #{teamName}, service_expertise = #{serviceExpertise}, build_time = #{buildTime}, service_area = #{serviceArea}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(CareTeam entity);

    @Delete("DELETE FROM care_team WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM care_team")
    long countAll();

    @Update("UPDATE care_team SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
