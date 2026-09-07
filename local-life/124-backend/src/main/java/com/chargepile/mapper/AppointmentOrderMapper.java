package com.chargepile.mapper;

import com.chargepile.entity.AppointmentOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AppointmentOrderMapper {
    @Select({
        "<script>",
        "SELECT * FROM appointment_order",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (appointment_no LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%') OR station_name LIKE CONCAT('%',#{keyword},'%') OR pile_no LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AppointmentOrder> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM appointment_order WHERE id = #{id}")
    AppointmentOrder selectById(Long id);

    @Insert("INSERT INTO appointment_order (appointment_no, owner_name, station_name, pile_no, appointment_time, duration_minute, status, created_time, updated_time) VALUES (#{appointmentNo}, #{ownerName}, #{stationName}, #{pileNo}, #{appointmentTime}, #{durationMinute}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AppointmentOrder entity);

    @Update("UPDATE appointment_order SET appointment_no = #{appointmentNo}, owner_name = #{ownerName}, station_name = #{stationName}, pile_no = #{pileNo}, appointment_time = #{appointmentTime}, duration_minute = #{durationMinute}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AppointmentOrder entity);

    @Delete("DELETE FROM appointment_order WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM appointment_order")
    long countAll();

    @Update("UPDATE appointment_order SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
