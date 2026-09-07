package com.cloudcost.mapper;

import com.cloudcost.entity.CostBill;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CostBillMapper {
    @Select({
        "<script>",
        "SELECT * FROM cost_bill",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (bill_no LIKE CONCAT('%',#{keyword},'%') OR account_name LIKE CONCAT('%',#{keyword},'%') OR bill_month LIKE CONCAT('%',#{keyword},'%') OR currency LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<CostBill> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM cost_bill WHERE id = #{id}")
    CostBill selectById(Long id);

    @Insert("INSERT INTO cost_bill (bill_no, account_name, bill_month, currency, total_amount, pay_amount, status, created_time, updated_time) VALUES (#{billNo}, #{accountName}, #{billMonth}, #{currency}, #{totalAmount}, #{payAmount}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CostBill entity);

    @Update("UPDATE cost_bill SET bill_no = #{billNo}, account_name = #{accountName}, bill_month = #{billMonth}, currency = #{currency}, total_amount = #{totalAmount}, pay_amount = #{payAmount}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(CostBill entity);

    @Delete("DELETE FROM cost_bill WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM cost_bill")
    long countAll();

    @Update("UPDATE cost_bill SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
