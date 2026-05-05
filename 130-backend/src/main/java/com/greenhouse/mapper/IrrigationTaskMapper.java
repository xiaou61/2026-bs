package com.greenhouse.mapper;

import com.greenhouse.entity.IrrigationTask;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface IrrigationTaskMapper {
    @Select({
        "<script>",
        "SELECT * FROM irrigation_task",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (task_no LIKE CONCAT('%',#{keyword},'%') OR greenhouse_no LIKE CONCAT('%',#{keyword},'%') OR task_time LIKE CONCAT('%',#{keyword},'%') OR executor_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<IrrigationTask> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM irrigation_task WHERE id = #{id}")
    IrrigationTask selectById(Long id);

    @Insert("INSERT INTO irrigation_task (task_no, greenhouse_no, task_time, water_amount, executor_name, status, created_time, updated_time) VALUES (#{taskNo}, #{greenhouseNo}, #{taskTime}, #{waterAmount}, #{executorName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(IrrigationTask entity);

    @Update("UPDATE irrigation_task SET task_no = #{taskNo}, greenhouse_no = #{greenhouseNo}, task_time = #{taskTime}, water_amount = #{waterAmount}, executor_name = #{executorName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(IrrigationTask entity);

    @Delete("DELETE FROM irrigation_task WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM irrigation_task")
    long countAll();

    @Update("UPDATE irrigation_task SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
