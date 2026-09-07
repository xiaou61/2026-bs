package com.homeenergy.mapper;

import com.homeenergy.entity.ElectricityBill;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ElectricityBillMapper {
    @Select({
        "<script>",
        "SELECT * FROM electricity_bill",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (bill_no LIKE CONCAT('%',#{keyword},'%') OR home_no LIKE CONCAT('%',#{keyword},'%') OR bill_month LIKE CONCAT('%',#{keyword},'%') OR pay_method LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ElectricityBill> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM electricity_bill WHERE id = #{id}")
    ElectricityBill selectById(Long id);

    @Insert("INSERT INTO electricity_bill (bill_no, home_no, bill_month, energy_kwh, bill_amount, pay_method, status, created_time, updated_time) VALUES (#{billNo}, #{homeNo}, #{billMonth}, #{energyKwh}, #{billAmount}, #{payMethod}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ElectricityBill entity);

    @Update("UPDATE electricity_bill SET bill_no = #{billNo}, home_no = #{homeNo}, bill_month = #{billMonth}, energy_kwh = #{energyKwh}, bill_amount = #{billAmount}, pay_method = #{payMethod}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ElectricityBill entity);

    @Delete("DELETE FROM electricity_bill WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM electricity_bill")
    long countAll();

    @Update("UPDATE electricity_bill SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
