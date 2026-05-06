package com.topicselect.mapper;

import com.topicselect.entity.SelectionReview;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SelectionReviewMapper {
    @Select({
        "<script>",
        "SELECT * FROM invoice_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (invoice_no LIKE CONCAT('%',#{keyword},'%') OR claim_no LIKE CONCAT('%',#{keyword},'%') OR invoice_type LIKE CONCAT('%',#{keyword},'%') OR invoice_amount LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<SelectionReview> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM invoice_record WHERE id = #{id}")
    SelectionReview selectById(Long id);

    @Insert("INSERT INTO invoice_record (invoice_no, claim_no, invoice_type, invoice_amount, issuer_name, status, created_time, updated_time) VALUES (#{invoiceNo}, #{claimNo}, #{invoiceType}, #{invoiceAmount}, #{issuerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SelectionReview entity);

    @Update("UPDATE invoice_record SET invoice_no = #{invoiceNo}, claim_no = #{claimNo}, invoice_type = #{invoiceType}, invoice_amount = #{invoiceAmount}, issuer_name = #{issuerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(SelectionReview entity);

    @Delete("DELETE FROM invoice_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM invoice_record")
    long countAll();

    @Update("UPDATE invoice_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}

