package com.vehicleclaim.mapper;

import com.vehicleclaim.entity.ClaimApplication;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ClaimApplicationMapper {
    @Select({
        "<script>",
        "SELECT * FROM claim_application",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (application_no LIKE CONCAT('%',#{keyword},'%') OR case_name LIKE CONCAT('%',#{keyword},'%') OR report_channel LIKE CONCAT('%',#{keyword},'%') OR accident_location LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ClaimApplication> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM claim_application WHERE id = #{id}")
    ClaimApplication selectById(Long id);

    @Insert("INSERT INTO claim_application (application_no, case_name, report_channel, application_time, accident_location, status, created_time, updated_time) VALUES (#{applicationNo}, #{caseName}, #{reportChannel}, #{applicationTime}, #{accidentLocation}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ClaimApplication entity);

    @Update("UPDATE claim_application SET application_no = #{applicationNo}, case_name = #{caseName}, report_channel = #{reportChannel}, application_time = #{applicationTime}, accident_location = #{accidentLocation}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ClaimApplication entity);

    @Delete("DELETE FROM claim_application WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM claim_application")
    long countAll();

    @Update("UPDATE claim_application SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
