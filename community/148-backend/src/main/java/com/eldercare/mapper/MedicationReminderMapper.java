package com.eldercare.mapper;

import com.eldercare.entity.MedicationReminder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MedicationReminderMapper {
    @Select({
        "<script>",
        "SELECT * FROM medication_reminder",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (reminder_no LIKE CONCAT('%',#{keyword},'%') OR elder_name LIKE CONCAT('%',#{keyword},'%') OR reminder_type LIKE CONCAT('%',#{keyword},'%') OR receiver_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<MedicationReminder> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM medication_reminder WHERE id = #{id}")
    MedicationReminder selectById(Long id);

    @Insert("INSERT INTO medication_reminder (reminder_no, elder_name, reminder_type, reminder_time, receiver_name, status, created_time, updated_time) VALUES (#{reminderNo}, #{elderName}, #{reminderType}, #{reminderTime}, #{receiverName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(MedicationReminder entity);

    @Update("UPDATE medication_reminder SET reminder_no = #{reminderNo}, elder_name = #{elderName}, reminder_type = #{reminderType}, reminder_time = #{reminderTime}, receiver_name = #{receiverName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(MedicationReminder entity);

    @Delete("DELETE FROM medication_reminder WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM medication_reminder")
    long countAll();

    @Update("UPDATE medication_reminder SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
