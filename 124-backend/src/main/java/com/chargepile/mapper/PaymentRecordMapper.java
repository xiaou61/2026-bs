package com.chargepile.mapper;

import com.chargepile.entity.PaymentRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PaymentRecordMapper {
    @Select({
        "<script>",
        "SELECT * FROM payment_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (payment_no LIKE CONCAT('%',#{keyword},'%') OR session_no LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%') OR pay_method LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<PaymentRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM payment_record WHERE id = #{id}")
    PaymentRecord selectById(Long id);

    @Insert("INSERT INTO payment_record (payment_no, session_no, owner_name, pay_amount, pay_method, pay_time, status, created_time, updated_time) VALUES (#{paymentNo}, #{sessionNo}, #{ownerName}, #{payAmount}, #{payMethod}, #{payTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(PaymentRecord entity);

    @Update("UPDATE payment_record SET payment_no = #{paymentNo}, session_no = #{sessionNo}, owner_name = #{ownerName}, pay_amount = #{payAmount}, pay_method = #{payMethod}, pay_time = #{payTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(PaymentRecord entity);

    @Delete("DELETE FROM payment_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM payment_record")
    long countAll();

    @Update("UPDATE payment_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
