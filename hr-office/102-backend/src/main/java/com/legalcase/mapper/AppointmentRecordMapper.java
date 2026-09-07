package com.legalcase.mapper;

import com.legalcase.entity.AppointmentRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AppointmentRecordMapper {
    @Select({"<script>",
            "SELECT * FROM appointment_record",
            "<where>",
            "<if test=\"keyword != null and keyword != \'\'\"> AND (appointment_type LIKE CONCAT('%',#{keyword},'%') OR location LIKE CONCAT('%',#{keyword},'%') OR remark LIKE CONCAT('%',#{keyword},'%')) </if>",
            "<if test=\"caseId != null\"> AND case_id = #{caseId} </if>",
            "<if test=\"clientId != null\"> AND client_id = #{clientId} </if>",
            "<if test=\"lawyerId != null\"> AND lawyer_id = #{lawyerId} </if>",
            "<if test=\"status != null\"> AND status = #{status} </if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"})
    List<AppointmentRecord> selectPage(@Param("keyword") String keyword, @Param("caseId") Long caseId, @Param("clientId") Long clientId, @Param("lawyerId") Long lawyerId, @Param("status") Integer status);

    @Select("SELECT * FROM appointment_record WHERE id=#{id}")
    AppointmentRecord selectById(Long id);

    @Insert("INSERT INTO appointment_record(case_id, client_id, lawyer_id, appointment_time, appointment_type, location, status, remark, create_time, update_time) VALUES(#{caseId}, #{clientId}, #{lawyerId}, #{appointmentTime}, #{appointmentType}, #{location}, #{status}, #{remark}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AppointmentRecord entity);

    @Update("UPDATE appointment_record SET case_id=#{caseId}, client_id=#{clientId}, lawyer_id=#{lawyerId}, appointment_time=#{appointmentTime}, appointment_type=#{appointmentType}, location=#{location}, status=#{status}, remark=#{remark}, create_time=#{createTime}, update_time=#{updateTime} WHERE id=#{id}")
    int updateById(AppointmentRecord entity);

    @Delete("DELETE FROM appointment_record WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM appointment_record")
    long countAll();
}
