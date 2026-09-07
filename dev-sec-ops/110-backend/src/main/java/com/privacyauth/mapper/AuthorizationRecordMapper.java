package com.privacyauth.mapper;

import com.privacyauth.entity.AuthorizationRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AuthorizationRecordMapper {
    @Select({
        "<script>",
        "SELECT * FROM authorization_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (auth_no LIKE CONCAT('%',#{keyword},'%') OR subject_name LIKE CONCAT('%',#{keyword},'%') OR purpose_name LIKE CONCAT('%',#{keyword},'%') OR channel_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AuthorizationRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM authorization_record WHERE id = #{id}")
    AuthorizationRecord selectById(Long id);

    @Insert("INSERT INTO authorization_record (auth_no, subject_name, purpose_name, channel_name, grant_time, expire_time, status, created_time, updated_time) VALUES (#{authNo}, #{subjectName}, #{purposeName}, #{channelName}, #{grantTime}, #{expireTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AuthorizationRecord entity);

    @Update("UPDATE authorization_record SET auth_no = #{authNo}, subject_name = #{subjectName}, purpose_name = #{purposeName}, channel_name = #{channelName}, grant_time = #{grantTime}, expire_time = #{expireTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AuthorizationRecord entity);

    @Delete("DELETE FROM authorization_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM authorization_record")
    long countAll();

    @Update("UPDATE authorization_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
