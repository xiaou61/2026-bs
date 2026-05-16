package com.econtract.mapper;

import com.econtract.entity.SealApplication;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SealApplicationMapper {
    @Select({
        "<script>",
        "SELECT * FROM seal_application",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (application_no LIKE CONCAT('%',#{keyword},'%') OR contract_title LIKE CONCAT('%',#{keyword},'%') OR seal_type LIKE CONCAT('%',#{keyword},'%') OR applicant_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<SealApplication> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM seal_application WHERE id = #{id}")
    SealApplication selectById(Long id);

    @Insert("INSERT INTO seal_application (application_no, contract_title, seal_type, applicant_name, use_date, status, created_time, updated_time) VALUES (#{applicationNo}, #{contractTitle}, #{sealType}, #{applicantName}, #{useDate}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SealApplication entity);

    @Update("UPDATE seal_application SET application_no = #{applicationNo}, contract_title = #{contractTitle}, seal_type = #{sealType}, applicant_name = #{applicantName}, use_date = #{useDate}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(SealApplication entity);

    @Delete("DELETE FROM seal_application WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM seal_application")
    long countAll();

    @Update("UPDATE seal_application SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}



