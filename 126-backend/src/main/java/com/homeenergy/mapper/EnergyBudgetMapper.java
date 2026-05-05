package com.homeenergy.mapper;

import com.homeenergy.entity.EnergyBudget;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EnergyBudgetMapper {
    @Select({
        "<script>",
        "SELECT * FROM energy_budget",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (budget_no LIKE CONCAT('%',#{keyword},'%') OR home_no LIKE CONCAT('%',#{keyword},'%') OR budget_month LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<EnergyBudget> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM energy_budget WHERE id = #{id}")
    EnergyBudget selectById(Long id);

    @Insert("INSERT INTO energy_budget (budget_no, home_no, budget_month, budget_kwh, budget_amount, owner_name, status, created_time, updated_time) VALUES (#{budgetNo}, #{homeNo}, #{budgetMonth}, #{budgetKwh}, #{budgetAmount}, #{ownerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(EnergyBudget entity);

    @Update("UPDATE energy_budget SET budget_no = #{budgetNo}, home_no = #{homeNo}, budget_month = #{budgetMonth}, budget_kwh = #{budgetKwh}, budget_amount = #{budgetAmount}, owner_name = #{ownerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(EnergyBudget entity);

    @Delete("DELETE FROM energy_budget WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM energy_budget")
    long countAll();

    @Update("UPDATE energy_budget SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
