package com.cloudcost.mapper;

import com.cloudcost.entity.IdleResource;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface IdleResourceMapper {
    @Select({
        "<script>",
        "SELECT * FROM idle_resource",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (resource_name LIKE CONCAT('%',#{keyword},'%') OR resource_type LIKE CONCAT('%',#{keyword},'%') OR account_name LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<IdleResource> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM idle_resource WHERE id = #{id}")
    IdleResource selectById(Long id);

    @Insert("INSERT INTO idle_resource (resource_name, resource_type, account_name, owner_name, idle_days, monthly_cost, detected_time, status, created_time, updated_time) VALUES (#{resourceName}, #{resourceType}, #{accountName}, #{ownerName}, #{idleDays}, #{monthlyCost}, #{detectedTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(IdleResource entity);

    @Update("UPDATE idle_resource SET resource_name = #{resourceName}, resource_type = #{resourceType}, account_name = #{accountName}, owner_name = #{ownerName}, idle_days = #{idleDays}, monthly_cost = #{monthlyCost}, detected_time = #{detectedTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(IdleResource entity);

    @Delete("DELETE FROM idle_resource WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM idle_resource")
    long countAll();

    @Update("UPDATE idle_resource SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
