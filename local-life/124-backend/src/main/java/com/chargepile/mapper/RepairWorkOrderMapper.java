package com.chargepile.mapper;

import com.chargepile.entity.RepairWorkOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RepairWorkOrderMapper {
    @Select({
        "<script>",
        "SELECT * FROM repair_work_order",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (work_order_no LIKE CONCAT('%',#{keyword},'%') OR fault_no LIKE CONCAT('%',#{keyword},'%') OR maintainer_name LIKE CONCAT('%',#{keyword},'%') OR solution_text LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<RepairWorkOrder> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM repair_work_order WHERE id = #{id}")
    RepairWorkOrder selectById(Long id);

    @Insert("INSERT INTO repair_work_order (work_order_no, fault_no, maintainer_name, solution_text, deadline_time, repair_result, status, created_time, updated_time) VALUES (#{workOrderNo}, #{faultNo}, #{maintainerName}, #{solutionText}, #{deadlineTime}, #{repairResult}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RepairWorkOrder entity);

    @Update("UPDATE repair_work_order SET work_order_no = #{workOrderNo}, fault_no = #{faultNo}, maintainer_name = #{maintainerName}, solution_text = #{solutionText}, deadline_time = #{deadlineTime}, repair_result = #{repairResult}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(RepairWorkOrder entity);

    @Delete("DELETE FROM repair_work_order WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM repair_work_order")
    long countAll();

    @Update("UPDATE repair_work_order SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
