package com.twinpark.mapper;

import com.twinpark.entity.InspectionRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface InspectionRecordMapper {
    @Select({
        "<script>",
        "SELECT * FROM inspection_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (record_no LIKE CONCAT('%',#{keyword},'%') OR task_no LIKE CONCAT('%',#{keyword},'%') OR point_name LIKE CONCAT('%',#{keyword},'%') OR result_type LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<InspectionRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM inspection_record WHERE id = #{id}")
    InspectionRecord selectById(Long id);

    @Insert("INSERT INTO inspection_record (record_no, task_no, point_name, result_type, meter_value, inspect_time, status, created_time, updated_time) VALUES (#{recordNo}, #{taskNo}, #{pointName}, #{resultType}, #{meterValue}, #{inspectTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(InspectionRecord entity);

    @Update("UPDATE inspection_record SET record_no = #{recordNo}, task_no = #{taskNo}, point_name = #{pointName}, result_type = #{resultType}, meter_value = #{meterValue}, inspect_time = #{inspectTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(InspectionRecord entity);

    @Delete("DELETE FROM inspection_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM inspection_record")
    long countAll();

    @Update("UPDATE inspection_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
