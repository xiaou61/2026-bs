package com.chargepile.mapper;

import com.chargepile.entity.ChargingSession;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ChargingSessionMapper {
    @Select({
        "<script>",
        "SELECT * FROM charging_session",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (session_no LIKE CONCAT('%',#{keyword},'%') OR appointment_no LIKE CONCAT('%',#{keyword},'%') OR pile_no LIKE CONCAT('%',#{keyword},'%') OR start_time LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ChargingSession> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM charging_session WHERE id = #{id}")
    ChargingSession selectById(Long id);

    @Insert("INSERT INTO charging_session (session_no, appointment_no, pile_no, start_time, energy_kwh, charge_amount, status, created_time, updated_time) VALUES (#{sessionNo}, #{appointmentNo}, #{pileNo}, #{startTime}, #{energyKwh}, #{chargeAmount}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ChargingSession entity);

    @Update("UPDATE charging_session SET session_no = #{sessionNo}, appointment_no = #{appointmentNo}, pile_no = #{pileNo}, start_time = #{startTime}, energy_kwh = #{energyKwh}, charge_amount = #{chargeAmount}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ChargingSession entity);

    @Delete("DELETE FROM charging_session WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM charging_session")
    long countAll();

    @Update("UPDATE charging_session SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
