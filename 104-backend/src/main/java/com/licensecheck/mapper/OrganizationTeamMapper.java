package com.licensecheck.mapper;

import com.licensecheck.entity.OrganizationTeam;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrganizationTeamMapper {
    @Select({
        "<script>",
        "SELECT * FROM organization_team",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (team_name LIKE CONCAT('%',#{keyword},'%') OR leader_name LIKE CONCAT('%',#{keyword},'%') OR contact_phone LIKE CONCAT('%',#{keyword},'%') OR description LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<OrganizationTeam> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM organization_team WHERE id = #{id}")
    OrganizationTeam selectById(Long id);

    @Insert("INSERT INTO organization_team (team_name, leader_name, contact_phone, description, status, created_time, updated_time) VALUES (#{teamName}, #{leaderName}, #{contactPhone}, #{description}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(OrganizationTeam entity);

    @Update("UPDATE organization_team SET team_name = #{teamName}, leader_name = #{leaderName}, contact_phone = #{contactPhone}, description = #{description}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(OrganizationTeam entity);

    @Delete("DELETE FROM organization_team WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM organization_team")
    long countAll();


    @Update("UPDATE organization_team SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);


}
