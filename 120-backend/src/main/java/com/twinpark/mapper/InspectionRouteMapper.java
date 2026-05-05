package com.twinpark.mapper;

import com.twinpark.entity.InspectionRoute;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface InspectionRouteMapper {
    @Select({
        "<script>",
        "SELECT * FROM inspection_route",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (route_no LIKE CONCAT('%',#{keyword},'%') OR route_name LIKE CONCAT('%',#{keyword},'%') OR building_name LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<InspectionRoute> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM inspection_route WHERE id = #{id}")
    InspectionRoute selectById(Long id);

    @Insert("INSERT INTO inspection_route (route_no, route_name, building_name, point_count, estimate_minutes, owner_name, status, created_time, updated_time) VALUES (#{routeNo}, #{routeName}, #{buildingName}, #{pointCount}, #{estimateMinutes}, #{ownerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(InspectionRoute entity);

    @Update("UPDATE inspection_route SET route_no = #{routeNo}, route_name = #{routeName}, building_name = #{buildingName}, point_count = #{pointCount}, estimate_minutes = #{estimateMinutes}, owner_name = #{ownerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(InspectionRoute entity);

    @Delete("DELETE FROM inspection_route WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM inspection_route")
    long countAll();

    @Update("UPDATE inspection_route SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
