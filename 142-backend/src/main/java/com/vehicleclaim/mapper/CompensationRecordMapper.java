package com.vehicleclaim.mapper;

import com.vehicleclaim.entity.CompensationRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CompensationRecordMapper {
    @Select({
        "<script>",
        "SELECT * FROM compensation_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (compensation_no LIKE CONCAT('%',#{keyword},'%') OR report_no LIKE CONCAT('%',#{keyword},'%') OR payee_name LIKE CONCAT('%',#{keyword},'%') OR compensation_amount LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<CompensationRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM compensation_record WHERE id = #{id}")
    CompensationRecord selectById(Long id);

    @Insert("INSERT INTO compensation_record (compensation_no, report_no, compensation_amount, compensation_time, payee_name, status, created_time, updated_time) VALUES (#{compensationNo}, #{reportNo}, #{compensationAmount}, #{compensationTime}, #{payeeName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CompensationRecord entity);

    @Update("UPDATE compensation_record SET compensation_no = #{compensationNo}, report_no = #{reportNo}, compensation_amount = #{compensationAmount}, compensation_time = #{compensationTime}, payee_name = #{payeeName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(CompensationRecord entity);

    @Delete("DELETE FROM compensation_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM compensation_record")
    long countAll();

    @Update("UPDATE compensation_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
