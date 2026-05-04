package com.zerotrust.mapper;

import com.zerotrust.entity.IdentityProvider;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface IdentityProviderMapper {
    @Select({
        "<script>",
        "SELECT * FROM identity_provider",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (provider_name LIKE CONCAT('%',#{keyword},'%') OR provider_code LIKE CONCAT('%',#{keyword},'%') OR sync_mode LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<IdentityProvider> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM identity_provider WHERE id = #{id}")
    IdentityProvider selectById(Long id);

    @Insert("INSERT INTO identity_provider (provider_name, provider_code, sync_mode, endpoint_url, owner_name, status, created_time, updated_time) VALUES (#{providerName}, #{providerCode}, #{syncMode}, #{endpointUrl}, #{ownerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(IdentityProvider entity);

    @Update("UPDATE identity_provider SET provider_name = #{providerName}, provider_code = #{providerCode}, sync_mode = #{syncMode}, endpoint_url = #{endpointUrl}, owner_name = #{ownerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(IdentityProvider entity);

    @Delete("DELETE FROM identity_provider WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM identity_provider")
    long countAll();

    @Update("UPDATE identity_provider SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
