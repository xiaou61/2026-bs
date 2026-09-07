package com.coldchain.mapper;

import com.coldchain.entity.GpsTrack;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GpsTrackMapper {
    @Select({
        "<script>",
        "SELECT * FROM gps_track",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (track_no LIKE CONCAT('%',#{keyword},'%') OR order_no LIKE CONCAT('%',#{keyword},'%') OR vehicle_no LIKE CONCAT('%',#{keyword},'%') OR location_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<GpsTrack> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM gps_track WHERE id = #{id}")
    GpsTrack selectById(Long id);

    @Insert("INSERT INTO gps_track (track_no, order_no, vehicle_no, location_name, longitude_text, latitude_text, speed_value, status, created_time, updated_time) VALUES (#{trackNo}, #{orderNo}, #{vehicleNo}, #{locationName}, #{longitudeText}, #{latitudeText}, #{speedValue}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(GpsTrack entity);

    @Update("UPDATE gps_track SET track_no = #{trackNo}, order_no = #{orderNo}, vehicle_no = #{vehicleNo}, location_name = #{locationName}, longitude_text = #{longitudeText}, latitude_text = #{latitudeText}, speed_value = #{speedValue}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(GpsTrack entity);

    @Delete("DELETE FROM gps_track WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM gps_track")
    long countAll();

    @Update("UPDATE gps_track SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

}
