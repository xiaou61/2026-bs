package com.accessibletravel.mapper;

import com.accessibletravel.entity.ServiceCheckin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ServiceCheckinMapper {
    @Select({
        "<script>",
        "SELECT * FROM service_checkin",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (checkin_no LIKE CONCAT('%',#{keyword},'%') OR volunteer_no LIKE CONCAT('%',#{keyword},'%') OR checkin_location LIKE CONCAT('%',#{keyword},'%') OR checkin_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ServiceCheckin> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM service_checkin WHERE id = #{id}")
    ServiceCheckin selectById(Long id);

    @Insert("INSERT INTO service_checkin (checkin_no, volunteer_no, checkin_location, checkin_time, checkin_name, status, created_time, updated_time) VALUES (#{checkinNo}, #{volunteerNo}, #{checkinLocation}, #{checkinTime}, #{checkinName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ServiceCheckin entity);

    @Update("UPDATE service_checkin SET checkin_no = #{checkinNo}, volunteer_no = #{volunteerNo}, checkin_location = #{checkinLocation}, checkin_time = #{checkinTime}, checkin_name = #{checkinName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ServiceCheckin entity);

    @Delete("DELETE FROM service_checkin WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM service_checkin")
    long countAll();

    @Update("UPDATE service_checkin SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}

