package com.econtract.mapper;

import com.econtract.entity.ExpirationReminder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ExpirationReminderMapper {
    @Select({
        "<script>",
        "SELECT * FROM expiration_reminder",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (reminder_no LIKE CONCAT('%',#{keyword},'%') OR contract_title LIKE CONCAT('%',#{keyword},'%') OR counterparty_name LIKE CONCAT('%',#{keyword},'%') OR reminder_method LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ExpirationReminder> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM expiration_reminder WHERE id = #{id}")
    ExpirationReminder selectById(Long id);

    @Insert("INSERT INTO expiration_reminder (reminder_no, contract_title, counterparty_name, expiry_date, reminder_method, status, created_time, updated_time) VALUES (#{reminderNo}, #{contractTitle}, #{counterpartyName}, #{expiryDate}, #{reminderMethod}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ExpirationReminder entity);

    @Update("UPDATE expiration_reminder SET reminder_no = #{reminderNo}, contract_title = #{contractTitle}, counterparty_name = #{counterpartyName}, expiry_date = #{expiryDate}, reminder_method = #{reminderMethod}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ExpirationReminder entity);

    @Delete("DELETE FROM expiration_reminder WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM expiration_reminder")
    long countAll();

    @Update("UPDATE expiration_reminder SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}



