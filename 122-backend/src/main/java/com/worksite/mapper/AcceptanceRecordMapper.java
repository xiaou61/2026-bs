package com.worksite.mapper;

import com.worksite.entity.AcceptanceRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AcceptanceRecordMapper {
    @Select({
        "<script>",
        "SELECT * FROM acceptance_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (accept_no LIKE CONCAT('%',#{keyword},'%') OR order_no LIKE CONCAT('%',#{keyword},'%') OR inspector_name LIKE CONCAT('%',#{keyword},'%') OR accept_time LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AcceptanceRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM acceptance_record WHERE id = #{id}")
    AcceptanceRecord selectById(Long id);

    @Insert("INSERT INTO acceptance_record (accept_no, order_no, inspector_name, accept_time, accept_result, score, status, created_time, updated_time) VALUES (#{acceptNo}, #{orderNo}, #{inspectorName}, #{acceptTime}, #{acceptResult}, #{score}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AcceptanceRecord entity);

    @Update("UPDATE acceptance_record SET accept_no = #{acceptNo}, order_no = #{orderNo}, inspector_name = #{inspectorName}, accept_time = #{acceptTime}, accept_result = #{acceptResult}, score = #{score}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AcceptanceRecord entity);

    @Delete("DELETE FROM acceptance_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM acceptance_record")
    long countAll();

    @Update("UPDATE acceptance_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
