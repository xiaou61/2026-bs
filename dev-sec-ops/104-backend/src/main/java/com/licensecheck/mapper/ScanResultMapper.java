package com.licensecheck.mapper;

import com.licensecheck.entity.ScanResult;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ScanResultMapper {
    @Select({
        "<script>",
        "SELECT * FROM scan_result",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (package_name LIKE CONCAT('%',#{keyword},'%') OR package_version LIKE CONCAT('%',#{keyword},'%') OR license_name LIKE CONCAT('%',#{keyword},'%') OR risk_level LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ScanResult> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM scan_result WHERE id = #{id}")
    ScanResult selectById(Long id);

    @Insert("INSERT INTO scan_result (task_id, package_name, package_version, license_name, risk_level, rule_matched, evidence, status, created_time, updated_time) VALUES (#{taskId}, #{packageName}, #{packageVersion}, #{licenseName}, #{riskLevel}, #{ruleMatched}, #{evidence}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ScanResult entity);

    @Update("UPDATE scan_result SET task_id = #{taskId}, package_name = #{packageName}, package_version = #{packageVersion}, license_name = #{licenseName}, risk_level = #{riskLevel}, rule_matched = #{ruleMatched}, evidence = #{evidence}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ScanResult entity);

    @Delete("DELETE FROM scan_result WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM scan_result")
    long countAll();


    @Update("UPDATE scan_result SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);


}
