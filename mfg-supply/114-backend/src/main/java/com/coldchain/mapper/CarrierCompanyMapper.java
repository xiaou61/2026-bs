package com.coldchain.mapper;

import com.coldchain.entity.CarrierCompany;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CarrierCompanyMapper {
    @Select({
        "<script>",
        "SELECT * FROM carrier_company",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (company_name LIKE CONCAT('%',#{keyword},'%') OR company_code LIKE CONCAT('%',#{keyword},'%') OR contact_name LIKE CONCAT('%',#{keyword},'%') OR license_no LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<CarrierCompany> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM carrier_company WHERE id = #{id}")
    CarrierCompany selectById(Long id);

    @Insert("INSERT INTO carrier_company (company_name, company_code, license_no, contact_name, contact_phone, vehicle_count, status, created_time, updated_time) VALUES (#{companyName}, #{companyCode}, #{licenseNo}, #{contactName}, #{contactPhone}, #{vehicleCount}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CarrierCompany entity);

    @Update("UPDATE carrier_company SET company_name = #{companyName}, company_code = #{companyCode}, license_no = #{licenseNo}, contact_name = #{contactName}, contact_phone = #{contactPhone}, vehicle_count = #{vehicleCount}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(CarrierCompany entity);

    @Delete("DELETE FROM carrier_company WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM carrier_company")
    long countAll();

    @Update("UPDATE carrier_company SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

}
