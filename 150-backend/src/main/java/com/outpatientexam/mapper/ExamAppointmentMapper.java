package com.outpatientexam.mapper;

import com.outpatientexam.entity.ExamAppointment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ExamAppointmentMapper {
    @Select({
            "<script>",
            "SELECT * FROM exam_appointment",
            "<where>",
            "<if test='keyword != null and keyword != \"\"'> AND (appointment_no LIKE CONCAT('%',#{keyword},'%') OR item_name LIKE CONCAT('%',#{keyword},'%') OR appointment_date LIKE CONCAT('%',#{keyword},'%') OR time_slot LIKE CONCAT('%',#{keyword},'%') OR doctor_name LIKE CONCAT('%',#{keyword},'%'))</if>",
            "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"
    })
    List<ExamAppointment> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM exam_appointment WHERE id = #{id}")
    ExamAppointment selectById(Long id);

    @Insert("INSERT INTO exam_appointment (appointment_no, item_name, appointment_date, time_slot, doctor_name, status, created_time, updated_time) VALUES (#{appointmentNo}, #{itemName}, #{appointmentDate}, #{timeSlot}, #{doctorName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ExamAppointment entity);

    @Update("UPDATE exam_appointment SET appointment_no = #{appointmentNo}, item_name = #{itemName}, appointment_date = #{appointmentDate}, time_slot = #{timeSlot}, doctor_name = #{doctorName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ExamAppointment entity);

    @Delete("DELETE FROM exam_appointment WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM exam_appointment")
    long countAll();

    @Update("UPDATE exam_appointment SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
