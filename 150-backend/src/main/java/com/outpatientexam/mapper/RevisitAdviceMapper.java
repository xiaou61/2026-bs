package com.outpatientexam.mapper;

import com.outpatientexam.entity.RevisitAdvice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RevisitAdviceMapper {
    @Select({
            "<script>",
            "SELECT * FROM revisit_advice",
            "<where>",
            "<if test='keyword != null and keyword != \"\"'> AND (advice_no LIKE CONCAT('%',#{keyword},'%') OR patient_name LIKE CONCAT('%',#{keyword},'%') OR advice_subject LIKE CONCAT('%',#{keyword},'%') OR advice_type LIKE CONCAT('%',#{keyword},'%'))</if>",
            "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"
    })
    List<RevisitAdvice> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM revisit_advice WHERE id = #{id}")
    RevisitAdvice selectById(Long id);

    @Insert("INSERT INTO revisit_advice (advice_no, patient_name, advice_subject, advice_type, advice_time, status, created_time, updated_time) VALUES (#{adviceNo}, #{patientName}, #{adviceSubject}, #{adviceType}, #{adviceTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RevisitAdvice entity);

    @Update("UPDATE revisit_advice SET advice_no = #{adviceNo}, patient_name = #{patientName}, advice_subject = #{adviceSubject}, advice_type = #{adviceType}, advice_time = #{adviceTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(RevisitAdvice entity);

    @Delete("DELETE FROM revisit_advice WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM revisit_advice")
    long countAll();

    @Update("UPDATE revisit_advice SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
