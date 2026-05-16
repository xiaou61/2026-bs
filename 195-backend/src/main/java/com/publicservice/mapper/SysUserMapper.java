package com.publicservice.mapper;

import com.publicservice.entity.SysUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SysUserMapper {
    @Select("SELECT * FROM sys_user WHERE username = #{username}")
    SysUser selectByUsername(String username);

    @Select("SELECT * FROM sys_user WHERE id = #{id}")
    SysUser selectById(Long id);

    @Select({"<script>", "SELECT * FROM sys_user", "<where>", "<if test='keyword != null and keyword != \"\"'> AND (username LIKE CONCAT('%',#{keyword},'%') OR nickname LIKE CONCAT('%',#{keyword},'%') OR role LIKE CONCAT('%',#{keyword},'%') OR department LIKE CONCAT('%',#{keyword},'%'))</if>", "<if test='status != null'> AND status = #{status}</if>", "</where>", "ORDER BY id DESC", "</script>"})
    List<SysUser> selectPage(@Param("keyword") String keyword, @Param("status") Integer status);

    @Insert("INSERT INTO sys_user (username,password,nickname,role,department,phone,email,status,created_time,updated_time) VALUES (#{username},#{password},#{nickname},#{role},#{department},#{phone},#{email},#{status},NOW(),NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SysUser entity);

    @Update("UPDATE sys_user SET nickname=#{nickname},role=#{role},department=#{department},phone=#{phone},email=#{email},status=#{status},updated_time=NOW() WHERE id=#{id}")
    int update(SysUser entity);

    @Delete("DELETE FROM sys_user WHERE id=#{id}")
    int deleteById(Long id);

    @Update("UPDATE sys_user SET status=#{status},updated_time=NOW() WHERE id=#{id}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
