package com.privacyauth.mapper;

import com.privacyauth.entity.AccessGrant;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AccessGrantMapper {
    @Select({
        "<script>",
        "SELECT * FROM access_grant",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (grant_no LIKE CONCAT('%',#{keyword},'%') OR application_no LIKE CONCAT('%',#{keyword},'%') OR grantee_name LIKE CONCAT('%',#{keyword},'%') OR status LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AccessGrant> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM access_grant WHERE id = #{id}")
    AccessGrant selectById(Long id);

    @Insert("INSERT INTO access_grant (grant_no, application_no, grantee_name, data_scope, expire_time, status, created_time, updated_time) VALUES (#{grantNo}, #{applicationNo}, #{granteeName}, #{dataScope}, #{expireTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AccessGrant entity);

    @Update("UPDATE access_grant SET grant_no = #{grantNo}, application_no = #{applicationNo}, grantee_name = #{granteeName}, data_scope = #{dataScope}, expire_time = #{expireTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AccessGrant entity);

    @Delete("DELETE FROM access_grant WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM access_grant")
    long countAll();

    @Update("UPDATE access_grant SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
