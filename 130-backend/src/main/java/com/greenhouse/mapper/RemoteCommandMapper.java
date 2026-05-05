package com.greenhouse.mapper;

import com.greenhouse.entity.RemoteCommand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RemoteCommandMapper {
    @Select({
        "<script>",
        "SELECT * FROM remote_command",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (command_no LIKE CONCAT('%',#{keyword},'%') OR device_no LIKE CONCAT('%',#{keyword},'%') OR command_type LIKE CONCAT('%',#{keyword},'%') OR param_text LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<RemoteCommand> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM remote_command WHERE id = #{id}")
    RemoteCommand selectById(Long id);

    @Insert("INSERT INTO remote_command (command_no, device_no, command_type, param_text, operator_name, status, created_time, updated_time) VALUES (#{commandNo}, #{deviceNo}, #{commandType}, #{paramText}, #{operatorName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RemoteCommand entity);

    @Update("UPDATE remote_command SET command_no = #{commandNo}, device_no = #{deviceNo}, command_type = #{commandType}, param_text = #{paramText}, operator_name = #{operatorName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(RemoteCommand entity);

    @Delete("DELETE FROM remote_command WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM remote_command")
    long countAll();

    @Update("UPDATE remote_command SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
