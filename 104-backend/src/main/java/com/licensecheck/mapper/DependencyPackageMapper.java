package com.licensecheck.mapper;

import com.licensecheck.entity.DependencyPackage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DependencyPackageMapper {
    @Select({
        "<script>",
        "SELECT * FROM dependency_package",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (package_name LIKE CONCAT('%',#{keyword},'%') OR package_version LIKE CONCAT('%',#{keyword},'%') OR package_manager LIKE CONCAT('%',#{keyword},'%') OR license_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<DependencyPackage> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM dependency_package WHERE id = #{id}")
    DependencyPackage selectById(Long id);

    @Insert("INSERT INTO dependency_package (repository_id, package_name, package_version, package_manager, license_name, scope_name, source_url, status, created_time, updated_time) VALUES (#{repositoryId}, #{packageName}, #{packageVersion}, #{packageManager}, #{licenseName}, #{scopeName}, #{sourceUrl}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(DependencyPackage entity);

    @Update("UPDATE dependency_package SET repository_id = #{repositoryId}, package_name = #{packageName}, package_version = #{packageVersion}, package_manager = #{packageManager}, license_name = #{licenseName}, scope_name = #{scopeName}, source_url = #{sourceUrl}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(DependencyPackage entity);

    @Delete("DELETE FROM dependency_package WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM dependency_package")
    long countAll();


    @Update("UPDATE dependency_package SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);


}
