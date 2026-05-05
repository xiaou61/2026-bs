package com.twinpark.mapper;

import com.twinpark.entity.WorkOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface WorkOrderMapper {
    @Select({
        "<script>",
        "SELECT * FROM work_order",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (work_order_no LIKE CONCAT('%',#{keyword},'%') OR defect_no LIKE CONCAT('%',#{keyword},'%') OR device_name LIKE CONCAT('%',#{keyword},'%') OR engineer_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<WorkOrder> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM work_order WHERE id = #{id}")
    WorkOrder selectById(Long id);

    @Insert("INSERT INTO work_order (work_order_no, defect_no, device_name, engineer_name, deadline_time, handle_result, status, created_time, updated_time) VALUES (#{workOrderNo}, #{defectNo}, #{deviceName}, #{engineerName}, #{deadlineTime}, #{handleResult}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(WorkOrder entity);

    @Update("UPDATE work_order SET work_order_no = #{workOrderNo}, defect_no = #{defectNo}, device_name = #{deviceName}, engineer_name = #{engineerName}, deadline_time = #{deadlineTime}, handle_result = #{handleResult}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(WorkOrder entity);

    @Delete("DELETE FROM work_order WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM work_order")
    long countAll();

    @Update("UPDATE work_order SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
