package com.livecommerce.mapper;

import com.livecommerce.entity.ProductSelection;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProductSelectionMapper {
    @Select({
        "<script>",
        "SELECT * FROM product_selection",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (selection_no LIKE CONCAT('%',#{keyword},'%') OR product_name LIKE CONCAT('%',#{keyword},'%') OR brand_name LIKE CONCAT('%',#{keyword},'%') OR category_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ProductSelection> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM product_selection WHERE id = #{id}")
    ProductSelection selectById(Long id);

    @Insert("INSERT INTO product_selection (selection_no, product_name, brand_name, category_name, supply_price, commission_rate, status, created_time, updated_time) VALUES (#{selectionNo}, #{productName}, #{brandName}, #{categoryName}, #{supplyPrice}, #{commissionRate}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ProductSelection entity);

    @Update("UPDATE product_selection SET selection_no = #{selectionNo}, product_name = #{productName}, brand_name = #{brandName}, category_name = #{categoryName}, supply_price = #{supplyPrice}, commission_rate = #{commissionRate}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ProductSelection entity);

    @Delete("DELETE FROM product_selection WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM product_selection")
    long countAll();

    @Update("UPDATE product_selection SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
