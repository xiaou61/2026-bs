package com.smartwarehouse.mapper;

import com.smartwarehouse.entity.WarehouseZone;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface WarehouseZoneMapper {
    @Select({
        "<script>",
        "SELECT * FROM warehouse_zone",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (zone_no LIKE CONCAT('%',#{keyword},'%') OR zone_name LIKE CONCAT('%',#{keyword},'%') OR temperature_type LIKE CONCAT('%',#{keyword},'%') OR manager_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<WarehouseZone> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM warehouse_zone WHERE id = #{id}")
    WarehouseZone selectById(Long id);

    @Insert("INSERT INTO warehouse_zone (zone_no, zone_name, temperature_type, aisle_count, manager_name, capacity_rate, status, created_time, updated_time) VALUES (#{zoneNo}, #{zoneName}, #{temperatureType}, #{aisleCount}, #{managerName}, #{capacityRate}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(WarehouseZone entity);

    @Update("UPDATE warehouse_zone SET zone_no = #{zoneNo}, zone_name = #{zoneName}, temperature_type = #{temperatureType}, aisle_count = #{aisleCount}, manager_name = #{managerName}, capacity_rate = #{capacityRate}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(WarehouseZone entity);

    @Delete("DELETE FROM warehouse_zone WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM warehouse_zone")
    long countAll();

    @Update("UPDATE warehouse_zone SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
