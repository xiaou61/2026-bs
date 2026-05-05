package com.greenhouse.mapper;

import com.greenhouse.entity.CropBatch;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CropBatchMapper {
    @Select({
        "<script>",
        "SELECT * FROM crop_batch",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (batch_no LIKE CONCAT('%',#{keyword},'%') OR greenhouse_no LIKE CONCAT('%',#{keyword},'%') OR crop_name LIKE CONCAT('%',#{keyword},'%') OR plant_date LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<CropBatch> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM crop_batch WHERE id = #{id}")
    CropBatch selectById(Long id);

    @Insert("INSERT INTO crop_batch (batch_no, greenhouse_no, crop_name, plant_date, owner_name, status, created_time, updated_time) VALUES (#{batchNo}, #{greenhouseNo}, #{cropName}, #{plantDate}, #{ownerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CropBatch entity);

    @Update("UPDATE crop_batch SET batch_no = #{batchNo}, greenhouse_no = #{greenhouseNo}, crop_name = #{cropName}, plant_date = #{plantDate}, owner_name = #{ownerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(CropBatch entity);

    @Delete("DELETE FROM crop_batch WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM crop_batch")
    long countAll();

    @Update("UPDATE crop_batch SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
