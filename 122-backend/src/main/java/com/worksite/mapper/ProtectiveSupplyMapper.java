package com.worksite.mapper;

import com.worksite.entity.ProtectiveSupply;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProtectiveSupplyMapper {
    @Select({
        "<script>",
        "SELECT * FROM protective_supply",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (supply_no LIKE CONCAT('%',#{keyword},'%') OR supply_name LIKE CONCAT('%',#{keyword},'%') OR supply_type LIKE CONCAT('%',#{keyword},'%') OR warehouse_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ProtectiveSupply> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM protective_supply WHERE id = #{id}")
    ProtectiveSupply selectById(Long id);

    @Insert("INSERT INTO protective_supply (supply_no, supply_name, supply_type, stock_qty, safe_qty, warehouse_name, status, created_time, updated_time) VALUES (#{supplyNo}, #{supplyName}, #{supplyType}, #{stockQty}, #{safeQty}, #{warehouseName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ProtectiveSupply entity);

    @Update("UPDATE protective_supply SET supply_no = #{supplyNo}, supply_name = #{supplyName}, supply_type = #{supplyType}, stock_qty = #{stockQty}, safe_qty = #{safeQty}, warehouse_name = #{warehouseName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ProtectiveSupply entity);

    @Delete("DELETE FROM protective_supply WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM protective_supply")
    long countAll();

    @Update("UPDATE protective_supply SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
