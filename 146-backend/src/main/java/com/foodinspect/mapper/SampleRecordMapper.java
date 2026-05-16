package com.foodinspect.mapper;

import com.foodinspect.entity.SampleRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SampleRecordMapper {
    @Select({
        "<script>",
        "SELECT * FROM sample_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (sample_no LIKE CONCAT('%',#{keyword},'%') OR food_name LIKE CONCAT('%',#{keyword},'%') OR sample_type LIKE CONCAT('%',#{keyword},'%') OR sampling_remark LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<SampleRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM sample_record WHERE id = #{id}")
    SampleRecord selectById(Long id);

    @Insert("INSERT INTO sample_record (sample_no, food_name, sample_type, sampling_remark, recorded_time, status, created_time, updated_time) VALUES (#{sampleNo}, #{foodName}, #{sampleType}, #{samplingRemark}, #{recordedTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SampleRecord entity);

    @Update("UPDATE sample_record SET sample_no = #{sampleNo}, food_name = #{foodName}, sample_type = #{sampleType}, sampling_remark = #{samplingRemark}, recorded_time = #{recordedTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(SampleRecord entity);

    @Delete("DELETE FROM sample_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM sample_record")
    long countAll();

    @Update("UPDATE sample_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}






