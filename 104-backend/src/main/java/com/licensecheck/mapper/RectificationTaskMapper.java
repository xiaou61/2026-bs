package com.licensecheck.mapper;

import com.licensecheck.entity.RectificationTask;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RectificationTaskMapper {
    @Select({
        "<script>",
        "SELECT * FROM rectification_task",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (assignee LIKE CONCAT('%',#{keyword},'%') OR action_plan LIKE CONCAT('%',#{keyword},'%') OR progress_note LIKE CONCAT('%',#{keyword},'%') OR status LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<RectificationTask> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM rectification_task WHERE id = #{id}")
    RectificationTask selectById(Long id);

    @Insert("INSERT INTO rectification_task (issue_id, assignee, action_plan, progress_note, status, due_date, finished_at, created_time, updated_time) VALUES (#{issueId}, #{assignee}, #{actionPlan}, #{progressNote}, #{status}, #{dueDate}, #{finishedAt}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RectificationTask entity);

    @Update("UPDATE rectification_task SET issue_id = #{issueId}, assignee = #{assignee}, action_plan = #{actionPlan}, progress_note = #{progressNote}, status = #{status}, due_date = #{dueDate}, finished_at = #{finishedAt}, updated_time = NOW() WHERE id = #{id}")
    int update(RectificationTask entity);

    @Delete("DELETE FROM rectification_task WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM rectification_task")
    long countAll();


    @Update("UPDATE rectification_task SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);


}
