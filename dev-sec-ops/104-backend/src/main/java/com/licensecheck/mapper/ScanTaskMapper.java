package com.licensecheck.mapper;

import com.licensecheck.entity.ScanTask;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ScanTaskMapper {
    @Select({
        "<script>",
        "SELECT * FROM scan_task",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (task_no LIKE CONCAT('%',#{keyword},'%') OR branch_name LIKE CONCAT('%',#{keyword},'%') OR trigger_type LIKE CONCAT('%',#{keyword},'%') OR scan_mode LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ScanTask> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM scan_task WHERE id = #{id}")
    ScanTask selectById(Long id);

    @Insert("INSERT INTO scan_task (task_no, repository_id, branch_name, trigger_type, scan_mode, status, started_at, finished_at, summary, created_time, updated_time) VALUES (#{taskNo}, #{repositoryId}, #{branchName}, #{triggerType}, #{scanMode}, #{status}, #{startedAt}, #{finishedAt}, #{summary}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ScanTask entity);

    @Update("UPDATE scan_task SET task_no = #{taskNo}, repository_id = #{repositoryId}, branch_name = #{branchName}, trigger_type = #{triggerType}, scan_mode = #{scanMode}, status = #{status}, started_at = #{startedAt}, finished_at = #{finishedAt}, summary = #{summary}, updated_time = NOW() WHERE id = #{id}")
    int update(ScanTask entity);

    @Delete("DELETE FROM scan_task WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM scan_task")
    long countAll();


    @Update("UPDATE scan_task SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);


}
