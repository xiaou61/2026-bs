package com.eldercare.mapper;

import com.eldercare.entity.ServiceRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ServiceRecordMapper {
    @Select({
        "<script>",
        "SELECT * FROM service_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (record_no LIKE CONCAT('%',#{keyword},'%') OR elder_name LIKE CONCAT('%',#{keyword},'%') OR service_conclusion LIKE CONCAT('%',#{keyword},'%') OR caregiver_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ServiceRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM service_record WHERE id = #{id}")
    ServiceRecord selectById(Long id);

    @Insert("INSERT INTO service_record (record_no, elder_name, service_conclusion, service_time, caregiver_name, status, created_time, updated_time) VALUES (#{recordNo}, #{elderName}, #{serviceConclusion}, #{serviceTime}, #{caregiverName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ServiceRecord entity);

    @Update("UPDATE service_record SET record_no = #{recordNo}, elder_name = #{elderName}, service_conclusion = #{serviceConclusion}, service_time = #{serviceTime}, caregiver_name = #{caregiverName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ServiceRecord entity);

    @Delete("DELETE FROM service_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM service_record")
    long countAll();

    @Update("UPDATE service_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
