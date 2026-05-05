package com.twinpark.mapper;

import com.twinpark.entity.MaintenanceSchedule;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MaintenanceScheduleMapper {
    @Select({
        "<script>",
        "SELECT * FROM maintenance_schedule",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (schedule_no LIKE CONCAT('%',#{keyword},'%') OR device_name LIKE CONCAT('%',#{keyword},'%') OR maintenance_type LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<MaintenanceSchedule> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM maintenance_schedule WHERE id = #{id}")
    MaintenanceSchedule selectById(Long id);

    @Insert("INSERT INTO maintenance_schedule (schedule_no, device_name, maintenance_type, plan_time, owner_name, execute_result, status, created_time, updated_time) VALUES (#{scheduleNo}, #{deviceName}, #{maintenanceType}, #{planTime}, #{ownerName}, #{executeResult}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(MaintenanceSchedule entity);

    @Update("UPDATE maintenance_schedule SET schedule_no = #{scheduleNo}, device_name = #{deviceName}, maintenance_type = #{maintenanceType}, plan_time = #{planTime}, owner_name = #{ownerName}, execute_result = #{executeResult}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(MaintenanceSchedule entity);

    @Delete("DELETE FROM maintenance_schedule WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM maintenance_schedule")
    long countAll();

    @Update("UPDATE maintenance_schedule SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
