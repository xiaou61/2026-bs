package com.homeenergy.mapper;

import com.homeenergy.entity.CarbonStatistic;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CarbonStatisticMapper {
    @Select({
        "<script>",
        "SELECT * FROM carbon_statistic",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (stat_no LIKE CONCAT('%',#{keyword},'%') OR home_no LIKE CONCAT('%',#{keyword},'%') OR stat_month LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<CarbonStatistic> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM carbon_statistic WHERE id = #{id}")
    CarbonStatistic selectById(Long id);

    @Insert("INSERT INTO carbon_statistic (stat_no, home_no, stat_month, energy_kwh, carbon_kg, year_rate, status, created_time, updated_time) VALUES (#{statNo}, #{homeNo}, #{statMonth}, #{energyKwh}, #{carbonKg}, #{yearRate}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CarbonStatistic entity);

    @Update("UPDATE carbon_statistic SET stat_no = #{statNo}, home_no = #{homeNo}, stat_month = #{statMonth}, energy_kwh = #{energyKwh}, carbon_kg = #{carbonKg}, year_rate = #{yearRate}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(CarbonStatistic entity);

    @Delete("DELETE FROM carbon_statistic WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM carbon_statistic")
    long countAll();

    @Update("UPDATE carbon_statistic SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
