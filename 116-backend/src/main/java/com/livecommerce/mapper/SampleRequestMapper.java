package com.livecommerce.mapper;

import com.livecommerce.entity.SampleRequest;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SampleRequestMapper {
    @Select({
        "<script>",
        "SELECT * FROM sample_request",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (request_no LIKE CONCAT('%',#{keyword},'%') OR product_name LIKE CONCAT('%',#{keyword},'%') OR supplier_name LIKE CONCAT('%',#{keyword},'%') OR receiver_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<SampleRequest> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM sample_request WHERE id = #{id}")
    SampleRequest selectById(Long id);

    @Insert("INSERT INTO sample_request (request_no, product_name, supplier_name, receiver_name, express_no, request_date, status, created_time, updated_time) VALUES (#{requestNo}, #{productName}, #{supplierName}, #{receiverName}, #{expressNo}, #{requestDate}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SampleRequest entity);

    @Update("UPDATE sample_request SET request_no = #{requestNo}, product_name = #{productName}, supplier_name = #{supplierName}, receiver_name = #{receiverName}, express_no = #{expressNo}, request_date = #{requestDate}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(SampleRequest entity);

    @Delete("DELETE FROM sample_request WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM sample_request")
    long countAll();

    @Update("UPDATE sample_request SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
