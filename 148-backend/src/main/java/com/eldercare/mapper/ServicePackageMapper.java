package com.eldercare.mapper;

import com.eldercare.entity.ServicePackage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ServicePackageMapper {
    @Select({
        "<script>",
        "SELECT * FROM service_package",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (package_no LIKE CONCAT('%',#{keyword},'%') OR package_name LIKE CONCAT('%',#{keyword},'%') OR service_category LIKE CONCAT('%',#{keyword},'%') OR target_group LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ServicePackage> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM service_package WHERE id = #{id}")
    ServicePackage selectById(Long id);

    @Insert("INSERT INTO service_package (package_no, package_name, service_category, target_group, service_cycle, status, created_time, updated_time) VALUES (#{packageNo}, #{packageName}, #{serviceCategory}, #{targetGroup}, #{serviceCycle}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ServicePackage entity);

    @Update("UPDATE service_package SET package_no = #{packageNo}, package_name = #{packageName}, service_category = #{serviceCategory}, target_group = #{targetGroup}, service_cycle = #{serviceCycle}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ServicePackage entity);

    @Delete("DELETE FROM service_package WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM service_package")
    long countAll();

    @Update("UPDATE service_package SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
