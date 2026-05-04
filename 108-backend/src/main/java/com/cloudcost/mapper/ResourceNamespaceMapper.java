package com.cloudcost.mapper;

import com.cloudcost.entity.ResourceNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ResourceNamespaceMapper {
    @Select({
        "<script>",
        "SELECT * FROM resource_namespace",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (namespace_name LIKE CONCAT('%',#{keyword},'%') OR namespace_code LIKE CONCAT('%',#{keyword},'%') OR business_line LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ResourceNamespace> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM resource_namespace WHERE id = #{id}")
    ResourceNamespace selectById(Long id);

    @Insert("INSERT INTO resource_namespace (namespace_name, namespace_code, account_name, business_line, environment_name, owner_name, status, created_time, updated_time) VALUES (#{namespaceName}, #{namespaceCode}, #{accountName}, #{businessLine}, #{environmentName}, #{ownerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ResourceNamespace entity);

    @Update("UPDATE resource_namespace SET namespace_name = #{namespaceName}, namespace_code = #{namespaceCode}, account_name = #{accountName}, business_line = #{businessLine}, environment_name = #{environmentName}, owner_name = #{ownerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ResourceNamespace entity);

    @Delete("DELETE FROM resource_namespace WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM resource_namespace")
    long countAll();

    @Update("UPDATE resource_namespace SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
