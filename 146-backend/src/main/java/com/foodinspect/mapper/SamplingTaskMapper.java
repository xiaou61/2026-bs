package com.foodinspect.mapper;

import com.foodinspect.entity.SamplingTask;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SamplingTaskMapper {
    @Select({
        "<script>",
        "SELECT * FROM sampling_task",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (task_no LIKE CONCAT('%',#{keyword},'%') OR task_title LIKE CONCAT('%',#{keyword},'%') OR sampling_location LIKE CONCAT('%',#{keyword},'%') OR inspector_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<SamplingTask> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM sampling_task WHERE id = #{id}")
    SamplingTask selectById(Long id);

    @Insert("INSERT INTO sampling_task (task_no, task_title, sampling_location, sampling_time, inspector_name, status, created_time, updated_time) VALUES (#{taskNo}, #{taskTitle}, #{samplingLocation}, #{samplingTime}, #{inspectorName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SamplingTask entity);

    @Update("UPDATE sampling_task SET task_no = #{taskNo}, task_title = #{taskTitle}, sampling_location = #{samplingLocation}, sampling_time = #{samplingTime}, inspector_name = #{inspectorName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(SamplingTask entity);

    @Delete("DELETE FROM sampling_task WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM sampling_task")
    long countAll();

    @Update("UPDATE sampling_task SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}






