package com.accessibletravel.mapper;

import com.accessibletravel.entity.AssistTask;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AssistTaskMapper {
    @Select({
        "<script>",
        "SELECT * FROM assist_task",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (task_no LIKE CONCAT('%',#{keyword},'%') OR volunteer_no LIKE CONCAT('%',#{keyword},'%') OR task_type LIKE CONCAT('%',#{keyword},'%') OR executor_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AssistTask> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM assist_task WHERE id = #{id}")
    AssistTask selectById(Long id);

    @Insert("INSERT INTO assist_task (task_no, volunteer_no, task_type, execute_time, executor_name, status, created_time, updated_time) VALUES (#{taskNo}, #{volunteerNo}, #{taskType}, #{executeTime}, #{executorName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AssistTask entity);

    @Update("UPDATE assist_task SET task_no = #{taskNo}, volunteer_no = #{volunteerNo}, task_type = #{taskType}, execute_time = #{executeTime}, executor_name = #{executorName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AssistTask entity);

    @Delete("DELETE FROM assist_task WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM assist_task")
    long countAll();

    @Update("UPDATE assist_task SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}

