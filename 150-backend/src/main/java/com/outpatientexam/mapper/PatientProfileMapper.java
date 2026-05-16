package com.outpatientexam.mapper;

import com.outpatientexam.entity.PatientProfile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PatientProfileMapper {
    @Select({
            "<script>",
            "SELECT * FROM patient_profile",
            "<where>",
            "<if test='keyword != null and keyword != \"\"'> AND (patient_no LIKE CONCAT('%',#{keyword},'%') OR patient_name LIKE CONCAT('%',#{keyword},'%') OR id_card LIKE CONCAT('%',#{keyword},'%') OR visit_card_no LIKE CONCAT('%',#{keyword},'%'))</if>",
            "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"
    })
    List<PatientProfile> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM patient_profile WHERE id = #{id}")
    PatientProfile selectById(Long id);

    @Insert("INSERT INTO patient_profile (patient_no, patient_name, id_card, phone, visit_card_no, status, created_time, updated_time) VALUES (#{patientNo}, #{patientName}, #{idCard}, #{phone}, #{visitCardNo}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(PatientProfile entity);

    @Update("UPDATE patient_profile SET patient_no = #{patientNo}, patient_name = #{patientName}, id_card = #{idCard}, phone = #{phone}, visit_card_no = #{visitCardNo}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(PatientProfile entity);

    @Delete("DELETE FROM patient_profile WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM patient_profile")
    long countAll();

    @Update("UPDATE patient_profile SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
