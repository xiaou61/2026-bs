package com.foodinspect.mapper;

import com.foodinspect.entity.RecheckApplication;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RecheckApplicationMapper {
    @Select({
        "<script>",
        "SELECT * FROM recheck_application",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (application_no LIKE CONCAT('%',#{keyword},'%') OR food_name LIKE CONCAT('%',#{keyword},'%') OR recheck_reason LIKE CONCAT('%',#{keyword},'%') OR applicant_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<RecheckApplication> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM recheck_application WHERE id = #{id}")
    RecheckApplication selectById(Long id);

    @Insert("INSERT INTO recheck_application (application_no, food_name, recheck_reason, application_time, applicant_name, status, created_time, updated_time) VALUES (#{applicationNo}, #{foodName}, #{recheckReason}, #{applicationTime}, #{applicantName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RecheckApplication entity);

    @Update("UPDATE recheck_application SET application_no = #{applicationNo}, food_name = #{foodName}, recheck_reason = #{recheckReason}, application_time = #{applicationTime}, applicant_name = #{applicantName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(RecheckApplication entity);

    @Delete("DELETE FROM recheck_application WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM recheck_application")
    long countAll();

    @Update("UPDATE recheck_application SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}






