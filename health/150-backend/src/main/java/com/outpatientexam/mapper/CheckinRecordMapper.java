package com.outpatientexam.mapper;

import com.outpatientexam.entity.CheckinRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CheckinRecordMapper {
    @Select({
            "<script>",
            "SELECT * FROM checkin_record",
            "<where>",
            "<if test='keyword != null and keyword != \"\"'> AND (checkin_no LIKE CONCAT('%',#{keyword},'%') OR patient_name LIKE CONCAT('%',#{keyword},'%') OR checkin_method LIKE CONCAT('%',#{keyword},'%') OR checkin_window LIKE CONCAT('%',#{keyword},'%') OR checkin_time LIKE CONCAT('%',#{keyword},'%'))</if>",
            "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"
    })
    List<CheckinRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM checkin_record WHERE id = #{id}")
    CheckinRecord selectById(Long id);

    @Insert("INSERT INTO checkin_record (checkin_no, patient_name, checkin_method, checkin_window, checkin_time, status, created_time, updated_time) VALUES (#{checkinNo}, #{patientName}, #{checkinMethod}, #{checkinWindow}, #{checkinTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CheckinRecord entity);

    @Update("UPDATE checkin_record SET checkin_no = #{checkinNo}, patient_name = #{patientName}, checkin_method = #{checkinMethod}, checkin_window = #{checkinWindow}, checkin_time = #{checkinTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(CheckinRecord entity);

    @Delete("DELETE FROM checkin_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM checkin_record")
    long countAll();

    @Update("UPDATE checkin_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
