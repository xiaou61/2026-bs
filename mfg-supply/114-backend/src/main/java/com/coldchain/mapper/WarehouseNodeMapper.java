package com.coldchain.mapper;

import com.coldchain.entity.WarehouseNode;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface WarehouseNodeMapper {
    @Select({
        "<script>",
        "SELECT * FROM warehouse_node",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (node_name LIKE CONCAT('%',#{keyword},'%') OR node_code LIKE CONCAT('%',#{keyword},'%') OR node_type LIKE CONCAT('%',#{keyword},'%') OR region_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<WarehouseNode> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM warehouse_node WHERE id = #{id}")
    WarehouseNode selectById(Long id);

    @Insert("INSERT INTO warehouse_node (node_name, node_code, node_type, region_name, manager_name, capacity_ton, status, created_time, updated_time) VALUES (#{nodeName}, #{nodeCode}, #{nodeType}, #{regionName}, #{managerName}, #{capacityTon}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(WarehouseNode entity);

    @Update("UPDATE warehouse_node SET node_name = #{nodeName}, node_code = #{nodeCode}, node_type = #{nodeType}, region_name = #{regionName}, manager_name = #{managerName}, capacity_ton = #{capacityTon}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(WarehouseNode entity);

    @Delete("DELETE FROM warehouse_node WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM warehouse_node")
    long countAll();

    @Update("UPDATE warehouse_node SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

}
