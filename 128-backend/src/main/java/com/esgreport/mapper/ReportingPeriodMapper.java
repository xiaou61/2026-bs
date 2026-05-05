package com.esgreport.mapper;

import com.esgreport.entity.ReportingPeriod;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ReportingPeriodMapper {
    @Select({
        "<script>",
        "SELECT * FROM reporting_period",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (period_no LIKE CONCAT('%',#{keyword},'%') OR company_name LIKE CONCAT('%',#{keyword},'%') OR report_month LIKE CONCAT('%',#{keyword},'%') OR template_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ReportingPeriod> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM reporting_period WHERE id = #{id}")
    ReportingPeriod selectById(Long id);

    @Insert("INSERT INTO reporting_period (period_no, company_name, report_month, template_name, owner_name, status, created_time, updated_time) VALUES (#{periodNo}, #{companyName}, #{reportMonth}, #{templateName}, #{ownerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ReportingPeriod entity);

    @Update("UPDATE reporting_period SET period_no = #{periodNo}, company_name = #{companyName}, report_month = #{reportMonth}, template_name = #{templateName}, owner_name = #{ownerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ReportingPeriod entity);

    @Delete("DELETE FROM reporting_period WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM reporting_period")
    long countAll();

    @Update("UPDATE reporting_period SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
