package com.legalcase.mapper;

import com.legalcase.entity.FeeRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FeeRecordMapper {
    @Select({"<script>",
            "SELECT * FROM fee_record",
            "<where>",
            "<if test=\"keyword != null and keyword != \'\'\"> AND (fee_type LIKE CONCAT('%',#{keyword},'%') OR remark LIKE CONCAT('%',#{keyword},'%')) </if>",
            "<if test=\"caseId != null\"> AND case_id = #{caseId} </if>",
            "<if test=\"clientId != null\"> AND client_id = #{clientId} </if>",
            "<if test=\"payStatus != null\"> AND pay_status = #{payStatus} </if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"})
    List<FeeRecord> selectPage(@Param("keyword") String keyword, @Param("caseId") Long caseId, @Param("clientId") Long clientId, @Param("payStatus") Integer payStatus);

    @Select("SELECT * FROM fee_record WHERE id=#{id}")
    FeeRecord selectById(Long id);

    @Insert("INSERT INTO fee_record(case_id, client_id, fee_type, amount, pay_status, pay_time, remark, create_time, update_time) VALUES(#{caseId}, #{clientId}, #{feeType}, #{amount}, #{payStatus}, #{payTime}, #{remark}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(FeeRecord entity);

    @Update("UPDATE fee_record SET case_id=#{caseId}, client_id=#{clientId}, fee_type=#{feeType}, amount=#{amount}, pay_status=#{payStatus}, pay_time=#{payTime}, remark=#{remark}, create_time=#{createTime}, update_time=#{updateTime} WHERE id=#{id}")
    int updateById(FeeRecord entity);

    @Delete("DELETE FROM fee_record WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM fee_record")
    long countAll();
}
