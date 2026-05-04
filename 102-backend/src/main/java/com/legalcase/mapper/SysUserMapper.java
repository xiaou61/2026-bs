package com.legalcase.mapper;

import com.legalcase.entity.SysUser;
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
    @Select({"<script>",
            "SELECT * FROM sys_user",
            "<where>",
            "<if test=\"keyword != null and keyword != \'\'\"> AND (username LIKE CONCAT('%',#{keyword},'%') OR nickname LIKE CONCAT('%',#{keyword},'%') OR phone LIKE CONCAT('%',#{keyword},'%') OR email LIKE CONCAT('%',#{keyword},'%')) </if>",
            "<if test=\"role != null and role != \'\'\"> AND role = #{role} </if>",
            "<if test=\"status != null\"> AND status = #{status} </if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"})
    List<SysUser> selectPage(@Param("keyword") String keyword, @Param("role") String role, @Param("status") Integer status);

    @Select("SELECT * FROM sys_user WHERE id=#{id}")
    SysUser selectById(Long id);

    @Insert("INSERT INTO sys_user(username, password, nickname, role, org_name, phone, email, status, create_time, update_time) VALUES(#{username}, #{password}, #{nickname}, #{role}, #{orgName}, #{phone}, #{email}, #{status}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SysUser entity);

    @Update("UPDATE sys_user SET username=#{username}, password=#{password}, nickname=#{nickname}, role=#{role}, org_name=#{orgName}, phone=#{phone}, email=#{email}, status=#{status}, create_time=#{createTime}, update_time=#{updateTime} WHERE id=#{id}")
    int updateById(SysUser entity);

    @Delete("DELETE FROM sys_user WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM sys_user")
    long countAll();

    @Select("SELECT * FROM sys_user WHERE username=#{username}")
    SysUser selectByUsername(String username);
}
