package com.meddevice.mapper;

import com.meddevice.entity.SterilizationRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SterilizationRecordMapper {
    @Select({
        "<script>",
        "SELECT * FROM sterilization_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (record_no LIKE CONCAT('%',#{keyword},'%') OR batch_no LIKE CONCAT('%',#{keyword},'%') OR device_no LIKE CONCAT('%',#{keyword},'%') OR sterilize_result LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<SterilizationRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM sterilization_record WHERE id = #{id}")
    SterilizationRecord selectById(Long id);

    @Insert("INSERT INTO sterilization_record (record_no, batch_no, device_no, sterilize_result, operator_name, status, created_time, updated_time) VALUES (#{recordNo}, #{batchNo}, #{deviceNo}, #{sterilizeResult}, #{operatorName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SterilizationRecord entity);

    @Update("UPDATE sterilization_record SET record_no = #{recordNo}, batch_no = #{batchNo}, device_no = #{deviceNo}, sterilize_result = #{sterilizeResult}, operator_name = #{operatorName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(SterilizationRecord entity);

    @Delete("DELETE FROM sterilization_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM sterilization_record")
    long countAll();

    @Update("UPDATE sterilization_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
