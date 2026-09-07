package com.licensecheck.mapper;

import com.licensecheck.entity.RiskIssue;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RiskIssueMapper {
    @Select({
        "<script>",
        "SELECT * FROM risk_issue",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (issue_no LIKE CONCAT('%',#{keyword},'%') OR issue_title LIKE CONCAT('%',#{keyword},'%') OR severity LIKE CONCAT('%',#{keyword},'%') OR issue_type LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<RiskIssue> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM risk_issue WHERE id = #{id}")
    RiskIssue selectById(Long id);

    @Insert("INSERT INTO risk_issue (issue_no, repository_id, result_id, issue_title, severity, issue_type, owner_name, status, due_date, description, created_time, updated_time) VALUES (#{issueNo}, #{repositoryId}, #{resultId}, #{issueTitle}, #{severity}, #{issueType}, #{ownerName}, #{status}, #{dueDate}, #{description}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RiskIssue entity);

    @Update("UPDATE risk_issue SET issue_no = #{issueNo}, repository_id = #{repositoryId}, result_id = #{resultId}, issue_title = #{issueTitle}, severity = #{severity}, issue_type = #{issueType}, owner_name = #{ownerName}, status = #{status}, due_date = #{dueDate}, description = #{description}, updated_time = NOW() WHERE id = #{id}")
    int update(RiskIssue entity);

    @Delete("DELETE FROM risk_issue WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM risk_issue")
    long countAll();


    @Update("UPDATE risk_issue SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);


}
