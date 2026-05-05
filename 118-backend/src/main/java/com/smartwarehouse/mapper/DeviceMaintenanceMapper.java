package com.smartwarehouse.mapper;

import com.smartwarehouse.entity.DeviceMaintenance;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DeviceMaintenanceMapper {
    @Select({
        "<script>",
        "SELECT * FROM device_maintenance",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (maintenance_no LIKE CONCAT('%',#{keyword},'%') OR device_no LIKE CONCAT('%',#{keyword},'%') OR fault_type LIKE CONCAT('%',#{keyword},'%') OR handler_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<DeviceMaintenance> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM device_maintenance WHERE id = #{id}")
    DeviceMaintenance selectById(Long id);

    @Insert("INSERT INTO device_maintenance (maintenance_no, device_no, device_type, fault_type, plan_time, handler_name, status, created_time, updated_time) VALUES (#{maintenanceNo}, #{deviceNo}, #{deviceType}, #{faultType}, #{planTime}, #{handlerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(DeviceMaintenance entity);

    @Update("UPDATE device_maintenance SET maintenance_no = #{maintenanceNo}, device_no = #{deviceNo}, device_type = #{deviceType}, fault_type = #{faultType}, plan_time = #{planTime}, handler_name = #{handlerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(DeviceMaintenance entity);

    @Delete("DELETE FROM device_maintenance WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM device_maintenance")
    long countAll();

    @Update("UPDATE device_maintenance SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
