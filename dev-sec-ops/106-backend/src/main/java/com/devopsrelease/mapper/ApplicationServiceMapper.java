package com.devopsrelease.mapper;

import com.devopsrelease.entity.ApplicationService;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ApplicationServiceMapper {
    @Select({
        "<script>",
        "SELECT * FROM application_service",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (service_name LIKE CONCAT('%',#{keyword},'%') OR service_code LIKE CONCAT('%',#{keyword},'%') OR repo_url LIKE CONCAT('%',#{keyword},'%') OR language LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ApplicationService> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM application_service WHERE id = #{id}")
    ApplicationService selectById(Long id);

    @Insert("INSERT INTO application_service (service_name, service_code, repo_url, language, owner_name, deploy_type, status, created_time, updated_time) VALUES (#{serviceName}, #{serviceCode}, #{repoUrl}, #{language}, #{ownerName}, #{deployType}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ApplicationService entity);

    @Update("UPDATE application_service SET service_name = #{serviceName}, service_code = #{serviceCode}, repo_url = #{repoUrl}, language = #{language}, owner_name = #{ownerName}, deploy_type = #{deployType}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ApplicationService entity);

    @Delete("DELETE FROM application_service WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM application_service")
    long countAll();


    @Update("UPDATE application_service SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);


}
