package com.livecommerce.mapper;

import com.livecommerce.entity.RefundRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RefundRecordMapper {
    @Select({
        "<script>",
        "SELECT * FROM refund_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (refund_no LIKE CONCAT('%',#{keyword},'%') OR ticket_no LIKE CONCAT('%',#{keyword},'%') OR order_no LIKE CONCAT('%',#{keyword},'%') OR audit_user LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<RefundRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM refund_record WHERE id = #{id}")
    RefundRecord selectById(Long id);

    @Insert("INSERT INTO refund_record (refund_no, ticket_no, order_no, refund_amount, reason_text, audit_user, status, created_time, updated_time) VALUES (#{refundNo}, #{ticketNo}, #{orderNo}, #{refundAmount}, #{reasonText}, #{auditUser}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RefundRecord entity);

    @Update("UPDATE refund_record SET refund_no = #{refundNo}, ticket_no = #{ticketNo}, order_no = #{orderNo}, refund_amount = #{refundAmount}, reason_text = #{reasonText}, audit_user = #{auditUser}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(RefundRecord entity);

    @Delete("DELETE FROM refund_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM refund_record")
    long countAll();

    @Update("UPDATE refund_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
