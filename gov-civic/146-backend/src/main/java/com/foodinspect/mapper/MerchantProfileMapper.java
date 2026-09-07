package com.foodinspect.mapper;

import com.foodinspect.entity.MerchantProfile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MerchantProfileMapper {
    @Select({
        "<script>",
        "SELECT * FROM merchant_profile",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (merchant_no LIKE CONCAT('%',#{keyword},'%') OR merchant_name LIKE CONCAT('%',#{keyword},'%') OR contact_name LIKE CONCAT('%',#{keyword},'%') OR business_address LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<MerchantProfile> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM merchant_profile WHERE id = #{id}")
    MerchantProfile selectById(Long id);

    @Insert("INSERT INTO merchant_profile (merchant_no, merchant_name, contact_name, contact_phone, business_address, status, created_time, updated_time) VALUES (#{merchantNo}, #{merchantName}, #{contactName}, #{contactPhone}, #{businessAddress}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(MerchantProfile entity);

    @Update("UPDATE merchant_profile SET merchant_no = #{merchantNo}, merchant_name = #{merchantName}, contact_name = #{contactName}, contact_phone = #{contactPhone}, business_address = #{businessAddress}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(MerchantProfile entity);

    @Delete("DELETE FROM merchant_profile WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM merchant_profile")
    long countAll();

    @Update("UPDATE merchant_profile SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}






