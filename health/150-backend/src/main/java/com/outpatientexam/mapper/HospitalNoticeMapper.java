package com.outpatientexam.mapper;

import com.outpatientexam.entity.HospitalNotice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface HospitalNoticeMapper {
    @Select({
            "<script>",
            "SELECT * FROM hospital_notice",
            "<where>",
            "<if test='keyword != null and keyword != \"\"'> AND (notice_no LIKE CONCAT('%',#{keyword},'%') OR patient_name LIKE CONCAT('%',#{keyword},'%') OR notice_type LIKE CONCAT('%',#{keyword},'%') OR receiver_name LIKE CONCAT('%',#{keyword},'%'))</if>",
            "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"
    })
    List<HospitalNotice> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM hospital_notice WHERE id = #{id}")
    HospitalNotice selectById(Long id);

    @Insert("INSERT INTO hospital_notice (notice_no, patient_name, notice_type, notice_content, receiver_name, status, created_time, updated_time) VALUES (#{noticeNo}, #{patientName}, #{noticeType}, #{noticeContent}, #{receiverName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(HospitalNotice entity);

    @Update("UPDATE hospital_notice SET notice_no = #{noticeNo}, patient_name = #{patientName}, notice_type = #{noticeType}, notice_content = #{noticeContent}, receiver_name = #{receiverName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(HospitalNotice entity);

    @Delete("DELETE FROM hospital_notice WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM hospital_notice")
    long countAll();

    @Update("UPDATE hospital_notice SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
