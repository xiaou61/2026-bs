package com.smartwarehouse.mapper;

import com.smartwarehouse.entity.AgvVehicle;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AgvVehicleMapper {
    @Select({
        "<script>",
        "SELECT * FROM agv_vehicle",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (agv_no LIKE CONCAT('%',#{keyword},'%') OR agv_model LIKE CONCAT('%',#{keyword},'%') OR current_zone LIKE CONCAT('%',#{keyword},'%') OR status LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AgvVehicle> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM agv_vehicle WHERE id = #{id}")
    AgvVehicle selectById(Long id);

    @Insert("INSERT INTO agv_vehicle (agv_no, agv_model, current_zone, battery_level, load_weight, task_count, status, created_time, updated_time) VALUES (#{agvNo}, #{agvModel}, #{currentZone}, #{batteryLevel}, #{loadWeight}, #{taskCount}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AgvVehicle entity);

    @Update("UPDATE agv_vehicle SET agv_no = #{agvNo}, agv_model = #{agvModel}, current_zone = #{currentZone}, battery_level = #{batteryLevel}, load_weight = #{loadWeight}, task_count = #{taskCount}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AgvVehicle entity);

    @Delete("DELETE FROM agv_vehicle WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM agv_vehicle")
    long countAll();

    @Update("UPDATE agv_vehicle SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
