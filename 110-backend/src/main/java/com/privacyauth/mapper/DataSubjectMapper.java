package com.privacyauth.mapper;

import com.privacyauth.entity.DataSubject;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DataSubjectMapper {
    @Select({
        "<script>",
        "SELECT * FROM data_subject",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (subject_name LIKE CONCAT('%',#{keyword},'%') OR subject_code LIKE CONCAT('%',#{keyword},'%') OR phone LIKE CONCAT('%',#{keyword},'%') OR email LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<DataSubject> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM data_subject WHERE id = #{id}")
    DataSubject selectById(Long id);

    @Insert("INSERT INTO data_subject (subject_name, subject_code, identity_type, phone, email, region_name, status, created_time, updated_time) VALUES (#{subjectName}, #{subjectCode}, #{identityType}, #{phone}, #{email}, #{regionName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(DataSubject entity);

    @Update("UPDATE data_subject SET subject_name = #{subjectName}, subject_code = #{subjectCode}, identity_type = #{identityType}, phone = #{phone}, email = #{email}, region_name = #{regionName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(DataSubject entity);

    @Delete("DELETE FROM data_subject WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM data_subject")
    long countAll();

    @Update("UPDATE data_subject SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
