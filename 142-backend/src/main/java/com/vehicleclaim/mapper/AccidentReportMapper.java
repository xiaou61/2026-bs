package com.vehicleclaim.mapper;

import com.vehicleclaim.entity.AccidentReport;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AccidentReportMapper {
    @Select({
        "<script>",
        "SELECT * FROM accident_report",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (report_no LIKE CONCAT('%',#{keyword},'%') OR case_name LIKE CONCAT('%',#{keyword},'%') OR accident_type LIKE CONCAT('%',#{keyword},'%') OR reporter_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AccidentReport> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM accident_report WHERE id = #{id}")
    AccidentReport selectById(Long id);

    @Insert("INSERT INTO accident_report (report_no, case_name, accident_type, report_time, reporter_name, status, created_time, updated_time) VALUES (#{reportNo}, #{caseName}, #{accidentType}, #{reportTime}, #{reporterName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AccidentReport entity);

    @Update("UPDATE accident_report SET report_no = #{reportNo}, case_name = #{caseName}, accident_type = #{accidentType}, report_time = #{reportTime}, reporter_name = #{reporterName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AccidentReport entity);

    @Delete("DELETE FROM accident_report WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM accident_report")
    long countAll();

    @Update("UPDATE accident_report SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
