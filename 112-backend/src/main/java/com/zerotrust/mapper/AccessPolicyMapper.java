package com.zerotrust.mapper;

import com.zerotrust.entity.AccessPolicy;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AccessPolicyMapper {
    @Select({
        "<script>",
        "SELECT * FROM access_policy",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (policy_name LIKE CONCAT('%',#{keyword},'%') OR policy_code LIKE CONCAT('%',#{keyword},'%') OR resource_name LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AccessPolicy> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM access_policy WHERE id = #{id}")
    AccessPolicy selectById(Long id);

    @Insert("INSERT INTO access_policy (policy_name, policy_code, resource_name, condition_text, owner_name, status, created_time, updated_time) VALUES (#{policyName}, #{policyCode}, #{resourceName}, #{conditionText}, #{ownerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AccessPolicy entity);

    @Update("UPDATE access_policy SET policy_name = #{policyName}, policy_code = #{policyCode}, resource_name = #{resourceName}, condition_text = #{conditionText}, owner_name = #{ownerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AccessPolicy entity);

    @Delete("DELETE FROM access_policy WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM access_policy")
    long countAll();

    @Update("UPDATE access_policy SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
