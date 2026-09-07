package com.chargepile.mapper;

import com.chargepile.entity.UserVehicle;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserVehicleMapper {
    @Select({
        "<script>",
        "SELECT * FROM user_vehicle",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (vehicle_no LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%') OR plate_no LIKE CONCAT('%',#{keyword},'%') OR vehicle_model LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<UserVehicle> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM user_vehicle WHERE id = #{id}")
    UserVehicle selectById(Long id);

    @Insert("INSERT INTO user_vehicle (vehicle_no, owner_name, plate_no, battery_capacity, vehicle_model, phone, status, created_time, updated_time) VALUES (#{vehicleNo}, #{ownerName}, #{plateNo}, #{batteryCapacity}, #{vehicleModel}, #{phone}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(UserVehicle entity);

    @Update("UPDATE user_vehicle SET vehicle_no = #{vehicleNo}, owner_name = #{ownerName}, plate_no = #{plateNo}, battery_capacity = #{batteryCapacity}, vehicle_model = #{vehicleModel}, phone = #{phone}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(UserVehicle entity);

    @Delete("DELETE FROM user_vehicle WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM user_vehicle")
    long countAll();

    @Update("UPDATE user_vehicle SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
