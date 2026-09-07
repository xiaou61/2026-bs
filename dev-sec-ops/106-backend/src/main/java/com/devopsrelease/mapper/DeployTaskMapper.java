package com.devopsrelease.mapper;

import com.devopsrelease.entity.DeployTask;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DeployTaskMapper {
    @Select({
        "<script>",
        "SELECT * FROM deploy_task",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (task_name LIKE CONCAT('%',#{keyword},'%') OR executor LIKE CONCAT('%',#{keyword},'%') OR status LIKE CONCAT('%',#{keyword},'%') OR started_at LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<DeployTask> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM deploy_task WHERE id = #{id}")
    DeployTask selectById(Long id);

    @Insert("INSERT INTO deploy_task (order_id, service_id, env_id, task_name, executor, status, started_at, finished_at, log_summary, created_time, updated_time) VALUES (#{orderId}, #{serviceId}, #{envId}, #{taskName}, #{executor}, #{status}, #{startedAt}, #{finishedAt}, #{logSummary}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(DeployTask entity);

    @Update("UPDATE deploy_task SET order_id = #{orderId}, service_id = #{serviceId}, env_id = #{envId}, task_name = #{taskName}, executor = #{executor}, status = #{status}, started_at = #{startedAt}, finished_at = #{finishedAt}, log_summary = #{logSummary}, updated_time = NOW() WHERE id = #{id}")
    int update(DeployTask entity);

    @Delete("DELETE FROM deploy_task WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM deploy_task")
    long countAll();


    @Update("UPDATE deploy_task SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);


}
