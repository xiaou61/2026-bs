package com.greenhouse.mapper;

import com.greenhouse.entity.DiseaseDiagnosis;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DiseaseDiagnosisMapper {
    @Select({
        "<script>",
        "SELECT * FROM disease_diagnosis",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (diagnosis_no LIKE CONCAT('%',#{keyword},'%') OR batch_no LIKE CONCAT('%',#{keyword},'%') OR disease_name LIKE CONCAT('%',#{keyword},'%') OR suggestion_text LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<DiseaseDiagnosis> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM disease_diagnosis WHERE id = #{id}")
    DiseaseDiagnosis selectById(Long id);

    @Insert("INSERT INTO disease_diagnosis (diagnosis_no, batch_no, disease_name, suggestion_text, expert_name, status, created_time, updated_time) VALUES (#{diagnosisNo}, #{batchNo}, #{diseaseName}, #{suggestionText}, #{expertName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(DiseaseDiagnosis entity);

    @Update("UPDATE disease_diagnosis SET diagnosis_no = #{diagnosisNo}, batch_no = #{batchNo}, disease_name = #{diseaseName}, suggestion_text = #{suggestionText}, expert_name = #{expertName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(DiseaseDiagnosis entity);

    @Delete("DELETE FROM disease_diagnosis WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM disease_diagnosis")
    long countAll();

    @Update("UPDATE disease_diagnosis SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
