package com.twinpark.mapper;

import com.twinpark.entity.ParkBuilding;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ParkBuildingMapper {
    @Select({
        "<script>",
        "SELECT * FROM park_building",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (building_no LIKE CONCAT('%',#{keyword},'%') OR building_name LIKE CONCAT('%',#{keyword},'%') OR manager_name LIKE CONCAT('%',#{keyword},'%') OR twin_status LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ParkBuilding> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM park_building WHERE id = #{id}")
    ParkBuilding selectById(Long id);

    @Insert("INSERT INTO park_building (building_no, building_name, floor_count, area_size, manager_name, twin_status, status, created_time, updated_time) VALUES (#{buildingNo}, #{buildingName}, #{floorCount}, #{areaSize}, #{managerName}, #{twinStatus}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ParkBuilding entity);

    @Update("UPDATE park_building SET building_no = #{buildingNo}, building_name = #{buildingName}, floor_count = #{floorCount}, area_size = #{areaSize}, manager_name = #{managerName}, twin_status = #{twinStatus}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ParkBuilding entity);

    @Delete("DELETE FROM park_building WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM park_building")
    long countAll();

    @Update("UPDATE park_building SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
