package com.smartwarehouse.mapper;

import com.smartwarehouse.entity.TaskRoute;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TaskRouteMapper {
    @Select({
        "<script>",
        "SELECT * FROM task_route",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (route_no LIKE CONCAT('%',#{keyword},'%') OR task_no LIKE CONCAT('%',#{keyword},'%') OR start_point LIKE CONCAT('%',#{keyword},'%') OR end_point LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<TaskRoute> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM task_route WHERE id = #{id}")
    TaskRoute selectById(Long id);

    @Insert("INSERT INTO task_route (route_no, task_no, start_point, end_point, distance_meter, estimate_seconds, congestion_level, status, created_time, updated_time) VALUES (#{routeNo}, #{taskNo}, #{startPoint}, #{endPoint}, #{distanceMeter}, #{estimateSeconds}, #{congestionLevel}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(TaskRoute entity);

    @Update("UPDATE task_route SET route_no = #{routeNo}, task_no = #{taskNo}, start_point = #{startPoint}, end_point = #{endPoint}, distance_meter = #{distanceMeter}, estimate_seconds = #{estimateSeconds}, congestion_level = #{congestionLevel}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(TaskRoute entity);

    @Delete("DELETE FROM task_route WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM task_route")
    long countAll();

    @Update("UPDATE task_route SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
