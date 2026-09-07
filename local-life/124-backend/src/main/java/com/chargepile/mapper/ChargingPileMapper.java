package com.chargepile.mapper;

import com.chargepile.entity.ChargingPile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ChargingPileMapper {
    @Select({
        "<script>",
        "SELECT * FROM charging_pile",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (pile_no LIKE CONCAT('%',#{keyword},'%') OR station_name LIKE CONCAT('%',#{keyword},'%') OR connector_type LIKE CONCAT('%',#{keyword},'%') OR position_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ChargingPile> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM charging_pile WHERE id = #{id}")
    ChargingPile selectById(Long id);

    @Insert("INSERT INTO charging_pile (pile_no, station_name, power_kw, connector_type, price_per_kwh, position_name, status, created_time, updated_time) VALUES (#{pileNo}, #{stationName}, #{powerKw}, #{connectorType}, #{pricePerKwh}, #{positionName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ChargingPile entity);

    @Update("UPDATE charging_pile SET pile_no = #{pileNo}, station_name = #{stationName}, power_kw = #{powerKw}, connector_type = #{connectorType}, price_per_kwh = #{pricePerKwh}, position_name = #{positionName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ChargingPile entity);

    @Delete("DELETE FROM charging_pile WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM charging_pile")
    long countAll();

    @Update("UPDATE charging_pile SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
