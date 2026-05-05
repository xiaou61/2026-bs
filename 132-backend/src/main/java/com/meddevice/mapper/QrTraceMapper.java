package com.meddevice.mapper;

import com.meddevice.entity.QrTrace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface QrTraceMapper {
    @Select({
        "<script>",
        "SELECT * FROM qr_trace",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (trace_no LIKE CONCAT('%',#{keyword},'%') OR qr_code LIKE CONCAT('%',#{keyword},'%') OR device_no LIKE CONCAT('%',#{keyword},'%') OR current_location LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<QrTrace> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM qr_trace WHERE id = #{id}")
    QrTrace selectById(Long id);

    @Insert("INSERT INTO qr_trace (trace_no, qr_code, device_no, current_location, flow_status, status, created_time, updated_time) VALUES (#{traceNo}, #{qrCode}, #{deviceNo}, #{currentLocation}, #{flowStatus}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(QrTrace entity);

    @Update("UPDATE qr_trace SET trace_no = #{traceNo}, qr_code = #{qrCode}, device_no = #{deviceNo}, current_location = #{currentLocation}, flow_status = #{flowStatus}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(QrTrace entity);

    @Delete("DELETE FROM qr_trace WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM qr_trace")
    long countAll();

    @Update("UPDATE qr_trace SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
