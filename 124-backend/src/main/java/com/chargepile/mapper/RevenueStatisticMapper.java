package com.chargepile.mapper;

import com.chargepile.entity.RevenueStatistic;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RevenueStatisticMapper {
    @Select({
        "<script>",
        "SELECT * FROM revenue_statistic",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (stat_no LIKE CONCAT('%',#{keyword},'%') OR station_name LIKE CONCAT('%',#{keyword},'%') OR stat_date LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<RevenueStatistic> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM revenue_statistic WHERE id = #{id}")
    RevenueStatistic selectById(Long id);

    @Insert("INSERT INTO revenue_statistic (stat_no, station_name, stat_date, order_count, energy_kwh, revenue_amount, status, created_time, updated_time) VALUES (#{statNo}, #{stationName}, #{statDate}, #{orderCount}, #{energyKwh}, #{revenueAmount}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RevenueStatistic entity);

    @Update("UPDATE revenue_statistic SET stat_no = #{statNo}, station_name = #{stationName}, stat_date = #{statDate}, order_count = #{orderCount}, energy_kwh = #{energyKwh}, revenue_amount = #{revenueAmount}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(RevenueStatistic entity);

    @Delete("DELETE FROM revenue_statistic WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM revenue_statistic")
    long countAll();

    @Update("UPDATE revenue_statistic SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
