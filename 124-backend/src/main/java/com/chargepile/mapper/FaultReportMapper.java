package com.chargepile.mapper;

import com.chargepile.entity.FaultReport;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FaultReportMapper {
    @Select({
        "<script>",
        "SELECT * FROM fault_report",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (fault_no LIKE CONCAT('%',#{keyword},'%') OR pile_no LIKE CONCAT('%',#{keyword},'%') OR fault_type LIKE CONCAT('%',#{keyword},'%') OR severity_level LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<FaultReport> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM fault_report WHERE id = #{id}")
    FaultReport selectById(Long id);

    @Insert("INSERT INTO fault_report (fault_no, pile_no, fault_type, severity_level, reporter_name, report_time, status, created_time, updated_time) VALUES (#{faultNo}, #{pileNo}, #{faultType}, #{severityLevel}, #{reporterName}, #{reportTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(FaultReport entity);

    @Update("UPDATE fault_report SET fault_no = #{faultNo}, pile_no = #{pileNo}, fault_type = #{faultType}, severity_level = #{severityLevel}, reporter_name = #{reporterName}, report_time = #{reportTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(FaultReport entity);

    @Delete("DELETE FROM fault_report WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM fault_report")
    long countAll();

    @Update("UPDATE fault_report SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
