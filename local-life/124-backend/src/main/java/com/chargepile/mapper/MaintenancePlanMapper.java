package com.chargepile.mapper;

import com.chargepile.entity.MaintenancePlan;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MaintenancePlanMapper {
    @Select({
        "<script>",
        "SELECT * FROM maintenance_plan",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (plan_no LIKE CONCAT('%',#{keyword},'%') OR station_name LIKE CONCAT('%',#{keyword},'%') OR pile_no LIKE CONCAT('%',#{keyword},'%') OR maintenance_type LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<MaintenancePlan> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM maintenance_plan WHERE id = #{id}")
    MaintenancePlan selectById(Long id);

    @Insert("INSERT INTO maintenance_plan (plan_no, station_name, pile_no, maintenance_type, owner_name, plan_time, status, created_time, updated_time) VALUES (#{planNo}, #{stationName}, #{pileNo}, #{maintenanceType}, #{ownerName}, #{planTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(MaintenancePlan entity);

    @Update("UPDATE maintenance_plan SET plan_no = #{planNo}, station_name = #{stationName}, pile_no = #{pileNo}, maintenance_type = #{maintenanceType}, owner_name = #{ownerName}, plan_time = #{planTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(MaintenancePlan entity);

    @Delete("DELETE FROM maintenance_plan WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM maintenance_plan")
    long countAll();

    @Update("UPDATE maintenance_plan SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
