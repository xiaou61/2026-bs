package com.housekeeping.mapper;

import com.housekeeping.entity.ServiceCatalog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ServiceCatalogMapper {
    @Select({"<script>", "SELECT * FROM service_catalog", "<where>", "<if test='keyword != null and keyword != \"\"'> AND (record_no LIKE CONCAT('%',#{keyword},'%') OR record_name LIKE CONCAT('%',#{keyword},'%') OR category LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>", "<if test='status != null and status != \"\"'> AND status = #{status}</if>", "</where>", "ORDER BY id DESC", "</script>"})
    List<ServiceCatalog> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Insert("INSERT INTO service_catalog (record_no,record_name,category,owner_name,plan_time,status,remark,created_time,updated_time) VALUES (#{recordNo},#{recordName},#{category},#{ownerName},#{planTime},#{status},#{remark},NOW(),NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ServiceCatalog entity);

    @Update("UPDATE service_catalog SET record_no=#{recordNo},record_name=#{recordName},category=#{category},owner_name=#{ownerName},plan_time=#{planTime},status=#{status},remark=#{remark},updated_time=NOW() WHERE id=#{id}")
    int update(ServiceCatalog entity);

    @Delete("DELETE FROM service_catalog WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT * FROM service_catalog WHERE id = #{id}")
    ServiceCatalog selectById(Long id);

    @Select("SELECT COUNT(*) FROM service_catalog")
    long countAll();

    @Update("UPDATE service_catalog SET status=#{status},updated_time=NOW() WHERE id=#{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
