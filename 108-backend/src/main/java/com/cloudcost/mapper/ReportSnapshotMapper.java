package com.cloudcost.mapper;

import com.cloudcost.entity.ReportSnapshot;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ReportSnapshotMapper {
    @Select({
        "<script>",
        "SELECT * FROM report_snapshot",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (report_name LIKE CONCAT('%',#{keyword},'%') OR report_month LIKE CONCAT('%',#{keyword},'%') OR report_type LIKE CONCAT('%',#{keyword},'%') OR generated_by LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ReportSnapshot> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM report_snapshot WHERE id = #{id}")
    ReportSnapshot selectById(Long id);

    @Insert("INSERT INTO report_snapshot (report_name, report_month, report_type, generated_by, summary_text, status, created_time, updated_time) VALUES (#{reportName}, #{reportMonth}, #{reportType}, #{generatedBy}, #{summaryText}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ReportSnapshot entity);

    @Update("UPDATE report_snapshot SET report_name = #{reportName}, report_month = #{reportMonth}, report_type = #{reportType}, generated_by = #{generatedBy}, summary_text = #{summaryText}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ReportSnapshot entity);

    @Delete("DELETE FROM report_snapshot WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM report_snapshot")
    long countAll();

    @Update("UPDATE report_snapshot SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
