package com.econtract.mapper;

import com.econtract.entity.SealRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SealRecordMapper {
    @Select({
        "<script>",
        "SELECT * FROM seal_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (seal_record_no LIKE CONCAT('%',#{keyword},'%') OR contract_title LIKE CONCAT('%',#{keyword},'%') OR seal_type LIKE CONCAT('%',#{keyword},'%') OR operator_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<SealRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM seal_record WHERE id = #{id}")
    SealRecord selectById(Long id);

    @Insert("INSERT INTO seal_record (seal_record_no, contract_title, seal_type, operator_name, seal_time, status, created_time, updated_time) VALUES (#{sealRecordNo}, #{contractTitle}, #{sealType}, #{operatorName}, #{sealTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SealRecord entity);

    @Update("UPDATE seal_record SET seal_record_no = #{sealRecordNo}, contract_title = #{contractTitle}, seal_type = #{sealType}, operator_name = #{operatorName}, seal_time = #{sealTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(SealRecord entity);

    @Delete("DELETE FROM seal_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM seal_record")
    long countAll();

    @Update("UPDATE seal_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}



