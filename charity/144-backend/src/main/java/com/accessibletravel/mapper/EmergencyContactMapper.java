package com.accessibletravel.mapper;

import com.accessibletravel.entity.EmergencyContact;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EmergencyContactMapper {
    @Select({
        "<script>",
        "SELECT * FROM emergency_contact",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (contact_no LIKE CONCAT('%',#{keyword},'%') OR traveler_no LIKE CONCAT('%',#{keyword},'%') OR contact_name LIKE CONCAT('%',#{keyword},'%') OR contact_phone LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<EmergencyContact> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM emergency_contact WHERE id = #{id}")
    EmergencyContact selectById(Long id);

    @Insert("INSERT INTO emergency_contact (contact_no, traveler_no, contact_name, contact_phone, relation_remark, status, created_time, updated_time) VALUES (#{contactNo}, #{travelerNo}, #{contactName}, #{contactPhone}, #{relationRemark}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(EmergencyContact entity);

    @Update("UPDATE emergency_contact SET contact_no = #{contactNo}, traveler_no = #{travelerNo}, contact_name = #{contactName}, contact_phone = #{contactPhone}, relation_remark = #{relationRemark}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(EmergencyContact entity);

    @Delete("DELETE FROM emergency_contact WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM emergency_contact")
    long countAll();

    @Update("UPDATE emergency_contact SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}

