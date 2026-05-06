package com.outpatientexam.mapper;

import com.outpatientexam.entity.ExamReport;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ExamReportMapper {
    @Select({
        "<script>",
        "SELECT * FROM payment_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (payment_no LIKE CONCAT('%',#{keyword},'%') OR claim_no LIKE CONCAT('%',#{keyword},'%') OR payment_amount LIKE CONCAT('%',#{keyword},'%') OR payment_time LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ExamReport> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM payment_record WHERE id = #{id}")
    ExamReport selectById(Long id);

    @Insert("INSERT INTO payment_record (payment_no, claim_no, payment_amount, payment_time, operator_name, status, created_time, updated_time) VALUES (#{paymentNo}, #{claimNo}, #{paymentAmount}, #{paymentTime}, #{operatorName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ExamReport entity);

    @Update("UPDATE payment_record SET payment_no = #{paymentNo}, claim_no = #{claimNo}, payment_amount = #{paymentAmount}, payment_time = #{paymentTime}, operator_name = #{operatorName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ExamReport entity);

    @Delete("DELETE FROM payment_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM payment_record")
    long countAll();

    @Update("UPDATE payment_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}








