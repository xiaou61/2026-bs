package com.devopsrelease.mapper;

import com.devopsrelease.entity.VersionArtifact;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface VersionArtifactMapper {
    @Select({
        "<script>",
        "SELECT * FROM version_artifact",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (version_no LIKE CONCAT('%',#{keyword},'%') OR artifact_name LIKE CONCAT('%',#{keyword},'%') OR image_tag LIKE CONCAT('%',#{keyword},'%') OR checksum LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<VersionArtifact> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM version_artifact WHERE id = #{id}")
    VersionArtifact selectById(Long id);

    @Insert("INSERT INTO version_artifact (service_id, version_no, artifact_name, image_tag, checksum, build_time, status, created_time, updated_time) VALUES (#{serviceId}, #{versionNo}, #{artifactName}, #{imageTag}, #{checksum}, #{buildTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(VersionArtifact entity);

    @Update("UPDATE version_artifact SET service_id = #{serviceId}, version_no = #{versionNo}, artifact_name = #{artifactName}, image_tag = #{imageTag}, checksum = #{checksum}, build_time = #{buildTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(VersionArtifact entity);

    @Delete("DELETE FROM version_artifact WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM version_artifact")
    long countAll();


    @Update("UPDATE version_artifact SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);


}
