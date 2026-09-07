package com.privacyauth.mapper;

import com.privacyauth.entity.AuthorizationScope;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AuthorizationScopeMapper {
    @Select({
        "<script>",
        "SELECT * FROM authorization_scope",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (auth_no LIKE CONCAT('%',#{keyword},'%') OR item_name LIKE CONCAT('%',#{keyword},'%') OR scope_type LIKE CONCAT('%',#{keyword},'%') OR required_flag LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AuthorizationScope> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM authorization_scope WHERE id = #{id}")
    AuthorizationScope selectById(Long id);

    @Insert("INSERT INTO authorization_scope (auth_no, item_name, scope_type, required_flag, description_text, status, created_time, updated_time) VALUES (#{authNo}, #{itemName}, #{scopeType}, #{requiredFlag}, #{descriptionText}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AuthorizationScope entity);

    @Update("UPDATE authorization_scope SET auth_no = #{authNo}, item_name = #{itemName}, scope_type = #{scopeType}, required_flag = #{requiredFlag}, description_text = #{descriptionText}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AuthorizationScope entity);

    @Delete("DELETE FROM authorization_scope WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM authorization_scope")
    long countAll();

    @Update("UPDATE authorization_scope SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
