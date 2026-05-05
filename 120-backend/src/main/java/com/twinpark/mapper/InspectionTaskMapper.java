package com.twinpark.mapper;

import com.twinpark.entity.InspectionTask;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface InspectionTaskMapper {
    @Select({
        "<script>",
        "SELECT * FROM inspection_task",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (task_no LIKE CONCAT('%',#{keyword},'%') OR route_name LIKE CONCAT('%',#{keyword},'%') OR inspector_name LIKE CONCAT('%',#{keyword},'%') OR priority_level LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<InspectionTask> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM inspection_task WHERE id = #{id}")
    InspectionTask selectById(Long id);

    @Insert("INSERT INTO inspection_task (task_no, route_name, inspector_name, plan_time, priority_level, point_total, status, created_time, updated_time) VALUES (#{taskNo}, #{routeName}, #{inspectorName}, #{planTime}, #{priorityLevel}, #{pointTotal}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(InspectionTask entity);

    @Update("UPDATE inspection_task SET task_no = #{taskNo}, route_name = #{routeName}, inspector_name = #{inspectorName}, plan_time = #{planTime}, priority_level = #{priorityLevel}, point_total = #{pointTotal}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(InspectionTask entity);

    @Delete("DELETE FROM inspection_task WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM inspection_task")
    long countAll();

    @Update("UPDATE inspection_task SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
