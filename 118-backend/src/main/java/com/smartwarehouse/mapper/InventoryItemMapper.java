package com.smartwarehouse.mapper;

import com.smartwarehouse.entity.InventoryItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface InventoryItemMapper {
    @Select({
        "<script>",
        "SELECT * FROM inventory_item",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (sku_no LIKE CONCAT('%',#{keyword},'%') OR item_name LIKE CONCAT('%',#{keyword},'%') OR batch_no LIKE CONCAT('%',#{keyword},'%') OR location_no LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<InventoryItem> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM inventory_item WHERE id = #{id}")
    InventoryItem selectById(Long id);

    @Insert("INSERT INTO inventory_item (sku_no, item_name, batch_no, location_no, quantity, turnover_level, status, created_time, updated_time) VALUES (#{skuNo}, #{itemName}, #{batchNo}, #{locationNo}, #{quantity}, #{turnoverLevel}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(InventoryItem entity);

    @Update("UPDATE inventory_item SET sku_no = #{skuNo}, item_name = #{itemName}, batch_no = #{batchNo}, location_no = #{locationNo}, quantity = #{quantity}, turnover_level = #{turnoverLevel}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(InventoryItem entity);

    @Delete("DELETE FROM inventory_item WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM inventory_item")
    long countAll();

    @Update("UPDATE inventory_item SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
