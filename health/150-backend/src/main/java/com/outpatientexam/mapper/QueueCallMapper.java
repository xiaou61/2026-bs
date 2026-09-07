package com.outpatientexam.mapper;

import com.outpatientexam.entity.QueueCall;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface QueueCallMapper {
    @Select({
            "<script>",
            "SELECT * FROM queue_call",
            "<where>",
            "<if test='keyword != null and keyword != \"\"'> AND (queue_no LIKE CONCAT('%',#{keyword},'%') OR patient_name LIKE CONCAT('%',#{keyword},'%') OR call_time LIKE CONCAT('%',#{keyword},'%') OR waiting_status LIKE CONCAT('%',#{keyword},'%') OR room_no LIKE CONCAT('%',#{keyword},'%'))</if>",
            "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"
    })
    List<QueueCall> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM queue_call WHERE id = #{id}")
    QueueCall selectById(Long id);

    @Insert("INSERT INTO queue_call (queue_no, patient_name, call_time, waiting_status, room_no, status, created_time, updated_time) VALUES (#{queueNo}, #{patientName}, #{callTime}, #{waitingStatus}, #{roomNo}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(QueueCall entity);

    @Update("UPDATE queue_call SET queue_no = #{queueNo}, patient_name = #{patientName}, call_time = #{callTime}, waiting_status = #{waitingStatus}, room_no = #{roomNo}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(QueueCall entity);

    @Delete("DELETE FROM queue_call WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM queue_call")
    long countAll();

    @Update("UPDATE queue_call SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
