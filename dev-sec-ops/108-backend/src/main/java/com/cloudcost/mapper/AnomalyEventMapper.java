package com.cloudcost.mapper;

import com.cloudcost.entity.AnomalyEvent;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AnomalyEventMapper {
    @Select({
        "<script>",
        "SELECT * FROM anomaly_event",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (event_no LIKE CONCAT('%',#{keyword},'%') OR account_name LIKE CONCAT('%',#{keyword},'%') OR resource_name LIKE CONCAT('%',#{keyword},'%') OR anomaly_type LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AnomalyEvent> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM anomaly_event WHERE id = #{id}")
    AnomalyEvent selectById(Long id);

    @Insert("INSERT INTO anomaly_event (event_no, account_name, resource_name, anomaly_type, cost_amount, detected_time, status, created_time, updated_time) VALUES (#{eventNo}, #{accountName}, #{resourceName}, #{anomalyType}, #{costAmount}, #{detectedTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AnomalyEvent entity);

    @Update("UPDATE anomaly_event SET event_no = #{eventNo}, account_name = #{accountName}, resource_name = #{resourceName}, anomaly_type = #{anomalyType}, cost_amount = #{costAmount}, detected_time = #{detectedTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AnomalyEvent entity);

    @Delete("DELETE FROM anomaly_event WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM anomaly_event")
    long countAll();

    @Update("UPDATE anomaly_event SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
