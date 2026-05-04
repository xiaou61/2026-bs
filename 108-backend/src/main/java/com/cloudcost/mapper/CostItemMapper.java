package com.cloudcost.mapper;

import com.cloudcost.entity.CostItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CostItemMapper {
    @Select({
        "<script>",
        "SELECT * FROM cost_item",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (resource_id LIKE CONCAT('%',#{keyword},'%') OR resource_name LIKE CONCAT('%',#{keyword},'%') OR resource_type LIKE CONCAT('%',#{keyword},'%') OR namespace_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<CostItem> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM cost_item WHERE id = #{id}")
    CostItem selectById(Long id);

    @Insert("INSERT INTO cost_item (resource_id, resource_name, resource_type, namespace_name, bill_month, cost_amount, usage_quantity, unit_name, status, created_time, updated_time) VALUES (#{resourceId}, #{resourceName}, #{resourceType}, #{namespaceName}, #{billMonth}, #{costAmount}, #{usageQuantity}, #{unitName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CostItem entity);

    @Update("UPDATE cost_item SET resource_id = #{resourceId}, resource_name = #{resourceName}, resource_type = #{resourceType}, namespace_name = #{namespaceName}, bill_month = #{billMonth}, cost_amount = #{costAmount}, usage_quantity = #{usageQuantity}, unit_name = #{unitName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(CostItem entity);

    @Delete("DELETE FROM cost_item WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM cost_item")
    long countAll();

    @Update("UPDATE cost_item SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
