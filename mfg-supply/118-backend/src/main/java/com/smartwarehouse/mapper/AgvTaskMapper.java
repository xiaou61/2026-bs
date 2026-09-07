package com.smartwarehouse.mapper;

import com.smartwarehouse.entity.AgvTask;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AgvTaskMapper {
    @Select({
        "<script>",
        "SELECT * FROM agv_task",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (task_no LIKE CONCAT('%',#{keyword},'%') OR task_type LIKE CONCAT('%',#{keyword},'%') OR agv_no LIKE CONCAT('%',#{keyword},'%') OR source_location LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AgvTask> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM agv_task WHERE id = #{id}")
    AgvTask selectById(Long id);

    @Insert("INSERT INTO agv_task (task_no, task_type, agv_no, source_location, target_location, priority_level, status, created_time, updated_time) VALUES (#{taskNo}, #{taskType}, #{agvNo}, #{sourceLocation}, #{targetLocation}, #{priorityLevel}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AgvTask entity);

    @Update("UPDATE agv_task SET task_no = #{taskNo}, task_type = #{taskType}, agv_no = #{agvNo}, source_location = #{sourceLocation}, target_location = #{targetLocation}, priority_level = #{priorityLevel}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AgvTask entity);

    @Delete("DELETE FROM agv_task WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM agv_task")
    long countAll();

    @Update("UPDATE agv_task SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
