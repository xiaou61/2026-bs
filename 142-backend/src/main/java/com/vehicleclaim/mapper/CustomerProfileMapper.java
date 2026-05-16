package com.vehicleclaim.mapper;

import com.vehicleclaim.entity.CustomerProfile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CustomerProfileMapper {
    @Select({
        "<script>",
        "SELECT * FROM customer_profile",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (customer_no LIKE CONCAT('%',#{keyword},'%') OR customer_name LIKE CONCAT('%',#{keyword},'%') OR id_card_no LIKE CONCAT('%',#{keyword},'%') OR phone LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<CustomerProfile> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM customer_profile WHERE id = #{id}")
    CustomerProfile selectById(Long id);

    @Insert("INSERT INTO customer_profile (customer_no, customer_name, id_card_no, phone, customer_type, status, created_time, updated_time) VALUES (#{customerNo}, #{customerName}, #{idCardNo}, #{phone}, #{customerType}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CustomerProfile entity);

    @Update("UPDATE customer_profile SET customer_no = #{customerNo}, customer_name = #{customerName}, id_card_no = #{idCardNo}, phone = #{phone}, customer_type = #{customerType}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(CustomerProfile entity);

    @Delete("DELETE FROM customer_profile WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM customer_profile")
    long countAll();

    @Update("UPDATE customer_profile SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
