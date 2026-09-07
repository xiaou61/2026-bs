package com.examguard.mapper;

import com.examguard.entity.WarningRule;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface WarningRuleMapper {
    @Select({
        "<script>",
        "SELECT * FROM paper_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (paper_no LIKE CONCAT('%',#{keyword},'%') OR project_no LIKE CONCAT('%',#{keyword},'%') OR paper_title LIKE CONCAT('%',#{keyword},'%') OR journal_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<WarningRule> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM paper_record WHERE id = #{id}")
    WarningRule selectById(Long id);

    @Insert("INSERT INTO paper_record (paper_no, project_no, paper_title, journal_name, publish_time, status, created_time, updated_time) VALUES (#{paperNo}, #{projectNo}, #{paperTitle}, #{journalName}, #{publishTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(WarningRule entity);

    @Update("UPDATE paper_record SET paper_no = #{paperNo}, project_no = #{projectNo}, paper_title = #{paperTitle}, journal_name = #{journalName}, publish_time = #{publishTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(WarningRule entity);

    @Delete("DELETE FROM paper_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM paper_record")
    long countAll();

    @Update("UPDATE paper_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}


