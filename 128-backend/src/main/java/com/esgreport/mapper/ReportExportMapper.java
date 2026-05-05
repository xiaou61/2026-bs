package com.esgreport.mapper;

import com.esgreport.entity.ReportExport;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ReportExportMapper {
    @Select({
        "<script>",
        "SELECT * FROM report_export",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (export_no LIKE CONCAT('%',#{keyword},'%') OR company_name LIKE CONCAT('%',#{keyword},'%') OR report_month LIKE CONCAT('%',#{keyword},'%') OR format_type LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ReportExport> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM report_export WHERE id = #{id}")
    ReportExport selectById(Long id);

    @Insert("INSERT INTO report_export (export_no, company_name, report_month, format_type, operator_name, status, created_time, updated_time) VALUES (#{exportNo}, #{companyName}, #{reportMonth}, #{formatType}, #{operatorName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ReportExport entity);

    @Update("UPDATE report_export SET export_no = #{exportNo}, company_name = #{companyName}, report_month = #{reportMonth}, format_type = #{formatType}, operator_name = #{operatorName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ReportExport entity);

    @Delete("DELETE FROM report_export WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM report_export")
    long countAll();

    @Update("UPDATE report_export SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
