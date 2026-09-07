package com.outpatientexam.mapper;

import com.outpatientexam.entity.ReportDelivery;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ReportDeliveryMapper {
    @Select({
            "<script>",
            "SELECT * FROM report_delivery",
            "<where>",
            "<if test='keyword != null and keyword != \"\"'> AND (delivery_no LIKE CONCAT('%',#{keyword},'%') OR patient_name LIKE CONCAT('%',#{keyword},'%') OR delivery_method LIKE CONCAT('%',#{keyword},'%') OR receiver_name LIKE CONCAT('%',#{keyword},'%'))</if>",
            "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"
    })
    List<ReportDelivery> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM report_delivery WHERE id = #{id}")
    ReportDelivery selectById(Long id);

    @Insert("INSERT INTO report_delivery (delivery_no, patient_name, delivery_method, delivery_time, receiver_name, status, created_time, updated_time) VALUES (#{deliveryNo}, #{patientName}, #{deliveryMethod}, #{deliveryTime}, #{receiverName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ReportDelivery entity);

    @Update("UPDATE report_delivery SET delivery_no = #{deliveryNo}, patient_name = #{patientName}, delivery_method = #{deliveryMethod}, delivery_time = #{deliveryTime}, receiver_name = #{receiverName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ReportDelivery entity);

    @Delete("DELETE FROM report_delivery WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM report_delivery")
    long countAll();

    @Update("UPDATE report_delivery SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
