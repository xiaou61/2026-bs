package com.inventory.mapper;

import com.inventory.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("<script>" +
            "select * from sys_user where 1=1 " +
            "<if test='username!=null and username!=\"\"'>and username like concat('%',#{username},'%')</if> " +
            "<if test='role!=null and role!=\"\"'>and role=#{role}</if> " +
            "<if test='status!=null'>and status=#{status}</if> " +
            "order by id desc" +
            "</script>")
    List<User> selectPageList(@Param("username") String username, @Param("role") String role, @Param("status") Integer status);

    @Select("select * from sys_user where id=#{id}")
    User selectById(@Param("id") Long id);

    @Select("select * from sys_user where username=#{username}")
    User selectByUsername(@Param("username") String username);

    @Select("select count(1) from sys_user")
    Long countAll();

    @Insert("insert into sys_user(username,password,nickname,phone,email,role,status) values(#{username},#{password},#{nickname},#{phone},#{email},#{role},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Update("update sys_user set username=#{username},password=#{password},nickname=#{nickname},phone=#{phone},email=#{email},role=#{role},status=#{status} where id=#{id}")
    int update(User user);

    @Update("update sys_user set nickname=#{nickname},phone=#{phone},email=#{email} where id=#{id}")
    int updateProfile(User user);

    @Update("update sys_user set password=#{password} where id=#{id}")
    int updatePassword(@Param("id") Long id, @Param("password") String password);

    @Update("update sys_user set status=#{status} where id=#{id}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    @Update("update sys_user set last_login_time=now() where id=#{id}")
    int updateLastLoginTime(@Param("id") Long id);

    @Delete("delete from sys_user where id=#{id}")
    int deleteById(@Param("id") Long id);
}
