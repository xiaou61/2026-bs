package com.greenhouse.mapper;

import com.greenhouse.entity.HarvestRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface HarvestRecordMapper {
    @Select({
        "<script>",
        "SELECT * FROM harvest_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (harvest_no LIKE CONCAT('%',#{keyword},'%') OR batch_no LIKE CONCAT('%',#{keyword},'%') OR harvest_date LIKE CONCAT('%',#{keyword},'%') OR quality_level LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<HarvestRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM harvest_record WHERE id = #{id}")
    HarvestRecord selectById(Long id);

    @Insert("INSERT INTO harvest_record (harvest_no, batch_no, harvest_date, harvest_weight, quality_level, status, created_time, updated_time) VALUES (#{harvestNo}, #{batchNo}, #{harvestDate}, #{harvestWeight}, #{qualityLevel}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(HarvestRecord entity);

    @Update("UPDATE harvest_record SET harvest_no = #{harvestNo}, batch_no = #{batchNo}, harvest_date = #{harvestDate}, harvest_weight = #{harvestWeight}, quality_level = #{qualityLevel}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(HarvestRecord entity);

    @Delete("DELETE FROM harvest_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM harvest_record")
    long countAll();

    @Update("UPDATE harvest_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
