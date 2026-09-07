package com.chargepile.mapper;

import com.chargepile.entity.ElectricityPrice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ElectricityPriceMapper {
    @Select({
        "<script>",
        "SELECT * FROM electricity_price",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (price_no LIKE CONCAT('%',#{keyword},'%') OR station_name LIKE CONCAT('%',#{keyword},'%') OR period_type LIKE CONCAT('%',#{keyword},'%') OR effective_date LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ElectricityPrice> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM electricity_price WHERE id = #{id}")
    ElectricityPrice selectById(Long id);

    @Insert("INSERT INTO electricity_price (price_no, station_name, period_type, electric_price, service_fee, effective_date, status, created_time, updated_time) VALUES (#{priceNo}, #{stationName}, #{periodType}, #{electricPrice}, #{serviceFee}, #{effectiveDate}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ElectricityPrice entity);

    @Update("UPDATE electricity_price SET price_no = #{priceNo}, station_name = #{stationName}, period_type = #{periodType}, electric_price = #{electricPrice}, service_fee = #{serviceFee}, effective_date = #{effectiveDate}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ElectricityPrice entity);

    @Delete("DELETE FROM electricity_price WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM electricity_price")
    long countAll();

    @Update("UPDATE electricity_price SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
