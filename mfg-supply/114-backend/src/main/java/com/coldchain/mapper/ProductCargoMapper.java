package com.coldchain.mapper;

import com.coldchain.entity.ProductCargo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProductCargoMapper {
    @Select({
        "<script>",
        "SELECT * FROM product_cargo",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (cargo_name LIKE CONCAT('%',#{keyword},'%') OR cargo_no LIKE CONCAT('%',#{keyword},'%') OR category_name LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ProductCargo> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM product_cargo WHERE id = #{id}")
    ProductCargo selectById(Long id);

    @Insert("INSERT INTO product_cargo (cargo_name, cargo_no, category_name, owner_name, temperature_range, cargo_weight, status, created_time, updated_time) VALUES (#{cargoName}, #{cargoNo}, #{categoryName}, #{ownerName}, #{temperatureRange}, #{cargoWeight}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ProductCargo entity);

    @Update("UPDATE product_cargo SET cargo_name = #{cargoName}, cargo_no = #{cargoNo}, category_name = #{categoryName}, owner_name = #{ownerName}, temperature_range = #{temperatureRange}, cargo_weight = #{cargoWeight}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ProductCargo entity);

    @Delete("DELETE FROM product_cargo WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM product_cargo")
    long countAll();

    @Update("UPDATE product_cargo SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

}
