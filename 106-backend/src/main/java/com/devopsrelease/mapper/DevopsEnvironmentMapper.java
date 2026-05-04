package com.devopsrelease.mapper;

import com.devopsrelease.entity.DevopsEnvironment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DevopsEnvironmentMapper {
    @Select({
        "<script>",
        "SELECT * FROM devops_environment",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (env_name LIKE CONCAT('%',#{keyword},'%') OR env_code LIKE CONCAT('%',#{keyword},'%') OR cluster_name LIKE CONCAT('%',#{keyword},'%') OR namespace_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<DevopsEnvironment> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM devops_environment WHERE id = #{id}")
    DevopsEnvironment selectById(Long id);

    @Insert("INSERT INTO devops_environment (env_name, env_code, cluster_name, namespace_name, owner_name, status, created_time, updated_time) VALUES (#{envName}, #{envCode}, #{clusterName}, #{namespaceName}, #{ownerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(DevopsEnvironment entity);

    @Update("UPDATE devops_environment SET env_name = #{envName}, env_code = #{envCode}, cluster_name = #{clusterName}, namespace_name = #{namespaceName}, owner_name = #{ownerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(DevopsEnvironment entity);

    @Delete("DELETE FROM devops_environment WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM devops_environment")
    long countAll();


    @Update("UPDATE devops_environment SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);


}
