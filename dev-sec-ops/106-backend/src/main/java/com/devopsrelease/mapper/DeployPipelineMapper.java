package com.devopsrelease.mapper;

import com.devopsrelease.entity.DeployPipeline;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DeployPipelineMapper {
    @Select({
        "<script>",
        "SELECT * FROM deploy_pipeline",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (pipeline_name LIKE CONCAT('%',#{keyword},'%') OR branch_name LIKE CONCAT('%',#{keyword},'%') OR build_command LIKE CONCAT('%',#{keyword},'%') OR image_repo LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<DeployPipeline> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM deploy_pipeline WHERE id = #{id}")
    DeployPipeline selectById(Long id);

    @Insert("INSERT INTO deploy_pipeline (service_id, pipeline_name, branch_name, build_command, image_repo, status, created_time, updated_time) VALUES (#{serviceId}, #{pipelineName}, #{branchName}, #{buildCommand}, #{imageRepo}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(DeployPipeline entity);

    @Update("UPDATE deploy_pipeline SET service_id = #{serviceId}, pipeline_name = #{pipelineName}, branch_name = #{branchName}, build_command = #{buildCommand}, image_repo = #{imageRepo}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(DeployPipeline entity);

    @Delete("DELETE FROM deploy_pipeline WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM deploy_pipeline")
    long countAll();


    @Update("UPDATE deploy_pipeline SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);


}
