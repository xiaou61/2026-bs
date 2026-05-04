package com.cloudcost.mapper;

import com.cloudcost.entity.CostAllocation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CostAllocationMapper {
    @Select({
        "<script>",
        "SELECT * FROM cost_allocation",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (allocation_no LIKE CONCAT('%',#{keyword},'%') OR namespace_name LIKE CONCAT('%',#{keyword},'%') OR business_line LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<CostAllocation> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM cost_allocation WHERE id = #{id}")
    CostAllocation selectById(Long id);

    @Insert("INSERT INTO cost_allocation (allocation_no, namespace_name, business_line, owner_name, allocated_amount, allocation_month, status, created_time, updated_time) VALUES (#{allocationNo}, #{namespaceName}, #{businessLine}, #{ownerName}, #{allocatedAmount}, #{allocationMonth}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CostAllocation entity);

    @Update("UPDATE cost_allocation SET allocation_no = #{allocationNo}, namespace_name = #{namespaceName}, business_line = #{businessLine}, owner_name = #{ownerName}, allocated_amount = #{allocatedAmount}, allocation_month = #{allocationMonth}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(CostAllocation entity);

    @Delete("DELETE FROM cost_allocation WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM cost_allocation")
    long countAll();

    @Update("UPDATE cost_allocation SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
