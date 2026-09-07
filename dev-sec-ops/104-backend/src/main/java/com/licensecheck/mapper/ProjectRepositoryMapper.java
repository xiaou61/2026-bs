package com.licensecheck.mapper;

import com.licensecheck.entity.ProjectRepository;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProjectRepositoryMapper {
    @Select({
        "<script>",
        "SELECT * FROM project_repository",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (repo_name LIKE CONCAT('%',#{keyword},'%') OR repo_code LIKE CONCAT('%',#{keyword},'%') OR owner_team LIKE CONCAT('%',#{keyword},'%') OR git_url LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ProjectRepository> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM project_repository WHERE id = #{id}")
    ProjectRepository selectById(Long id);

    @Insert("INSERT INTO project_repository (repo_name, repo_code, owner_team, git_url, language, risk_level, status, last_scan_time, created_time, updated_time) VALUES (#{repoName}, #{repoCode}, #{ownerTeam}, #{gitUrl}, #{language}, #{riskLevel}, #{status}, #{lastScanTime}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ProjectRepository entity);

    @Update("UPDATE project_repository SET repo_name = #{repoName}, repo_code = #{repoCode}, owner_team = #{ownerTeam}, git_url = #{gitUrl}, language = #{language}, risk_level = #{riskLevel}, status = #{status}, last_scan_time = #{lastScanTime}, updated_time = NOW() WHERE id = #{id}")
    int update(ProjectRepository entity);

    @Delete("DELETE FROM project_repository WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM project_repository")
    long countAll();


    @Update("UPDATE project_repository SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);


}
