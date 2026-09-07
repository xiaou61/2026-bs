package com.meddevice.mapper;

import com.meddevice.entity.SterilizationBatch;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SterilizationBatchMapper {
    @Select({
        "<script>",
        "SELECT * FROM sterilization_batch",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (batch_no LIKE CONCAT('%',#{keyword},'%') OR sterilize_method LIKE CONCAT('%',#{keyword},'%') OR machine_name LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<SterilizationBatch> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM sterilization_batch WHERE id = #{id}")
    SterilizationBatch selectById(Long id);

    @Insert("INSERT INTO sterilization_batch (batch_no, sterilize_method, machine_name, owner_name, start_time, status, created_time, updated_time) VALUES (#{batchNo}, #{sterilizeMethod}, #{machineName}, #{ownerName}, #{startTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SterilizationBatch entity);

    @Update("UPDATE sterilization_batch SET batch_no = #{batchNo}, sterilize_method = #{sterilizeMethod}, machine_name = #{machineName}, owner_name = #{ownerName}, start_time = #{startTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(SterilizationBatch entity);

    @Delete("DELETE FROM sterilization_batch WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM sterilization_batch")
    long countAll();

    @Update("UPDATE sterilization_batch SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
