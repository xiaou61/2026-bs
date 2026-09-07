package com.accessibletravel.mapper;

import com.accessibletravel.entity.AccessibleRoute;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AccessibleRouteMapper {
    @Select({
        "<script>",
        "SELECT * FROM accessible_route",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (route_no LIKE CONCAT('%',#{keyword},'%') OR route_name LIKE CONCAT('%',#{keyword},'%') OR route_type LIKE CONCAT('%',#{keyword},'%') OR travel_scenario LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AccessibleRoute> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM accessible_route WHERE id = #{id}")
    AccessibleRoute selectById(Long id);

    @Insert("INSERT INTO accessible_route (route_no, route_name, route_type, travel_scenario, suggested_duration, status, created_time, updated_time) VALUES (#{routeNo}, #{routeName}, #{routeType}, #{travelScenario}, #{suggestedDuration}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AccessibleRoute entity);

    @Update("UPDATE accessible_route SET route_no = #{routeNo}, route_name = #{routeName}, route_type = #{routeType}, travel_scenario = #{travelScenario}, suggested_duration = #{suggestedDuration}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AccessibleRoute entity);

    @Delete("DELETE FROM accessible_route WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM accessible_route")
    long countAll();

    @Update("UPDATE accessible_route SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}

