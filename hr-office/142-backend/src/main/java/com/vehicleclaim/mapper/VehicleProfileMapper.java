package com.vehicleclaim.mapper;

import com.vehicleclaim.entity.VehicleProfile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface VehicleProfileMapper {
    @Select({
        "<script>",
        "SELECT * FROM vehicle_profile",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (vehicle_no LIKE CONCAT('%',#{keyword},'%') OR plate_number LIKE CONCAT('%',#{keyword},'%') OR vin_code LIKE CONCAT('%',#{keyword},'%') OR vehicle_model LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<VehicleProfile> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM vehicle_profile WHERE id = #{id}")
    VehicleProfile selectById(Long id);

    @Insert("INSERT INTO vehicle_profile (vehicle_no, plate_number, vin_code, vehicle_model, usage_type, status, created_time, updated_time) VALUES (#{vehicleNo}, #{plateNumber}, #{vinCode}, #{vehicleModel}, #{usageType}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(VehicleProfile entity);

    @Update("UPDATE vehicle_profile SET vehicle_no = #{vehicleNo}, plate_number = #{plateNumber}, vin_code = #{vinCode}, vehicle_model = #{vehicleModel}, usage_type = #{usageType}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(VehicleProfile entity);

    @Delete("DELETE FROM vehicle_profile WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM vehicle_profile")
    long countAll();

    @Update("UPDATE vehicle_profile SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
