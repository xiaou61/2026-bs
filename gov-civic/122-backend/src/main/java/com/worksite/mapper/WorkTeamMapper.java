package com.worksite.mapper;

import com.worksite.entity.WorkTeam;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface WorkTeamMapper {
    @Select({
        "<script>",
        "SELECT * FROM work_team",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (team_no LIKE CONCAT('%',#{keyword},'%') OR team_name LIKE CONCAT('%',#{keyword},'%') OR leader_name LIKE CONCAT('%',#{keyword},'%') OR work_type LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<WorkTeam> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM work_team WHERE id = #{id}")
    WorkTeam selectById(Long id);

    @Insert("INSERT INTO work_team (team_no, team_name, leader_name, worker_count, work_type, contact_phone, status, created_time, updated_time) VALUES (#{teamNo}, #{teamName}, #{leaderName}, #{workerCount}, #{workType}, #{contactPhone}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(WorkTeam entity);

    @Update("UPDATE work_team SET team_no = #{teamNo}, team_name = #{teamName}, leader_name = #{leaderName}, worker_count = #{workerCount}, work_type = #{workType}, contact_phone = #{contactPhone}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(WorkTeam entity);

    @Delete("DELETE FROM work_team WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM work_team")
    long countAll();

    @Update("UPDATE work_team SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
