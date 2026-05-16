package com.accessibletravel.mapper;

import com.accessibletravel.entity.RoutePlan;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RoutePlanMapper {
    @Select({
        "<script>",
        "SELECT * FROM route_plan",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (plan_no LIKE CONCAT('%',#{keyword},'%') OR volunteer_no LIKE CONCAT('%',#{keyword},'%') OR route_type LIKE CONCAT('%',#{keyword},'%') OR route_detail LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<RoutePlan> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM route_plan WHERE id = #{id}")
    RoutePlan selectById(Long id);

    @Insert("INSERT INTO route_plan (plan_no, volunteer_no, route_type, route_detail, generate_time, status, created_time, updated_time) VALUES (#{planNo}, #{volunteerNo}, #{routeType}, #{routeDetail}, #{generateTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RoutePlan entity);

    @Update("UPDATE route_plan SET plan_no = #{planNo}, volunteer_no = #{volunteerNo}, route_type = #{routeType}, route_detail = #{routeDetail}, generate_time = #{generateTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(RoutePlan entity);

    @Delete("DELETE FROM route_plan WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM route_plan")
    long countAll();

    @Update("UPDATE route_plan SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}

