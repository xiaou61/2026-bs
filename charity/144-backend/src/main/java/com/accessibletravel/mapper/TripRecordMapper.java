package com.accessibletravel.mapper;

import com.accessibletravel.entity.TripRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TripRecordMapper {
    @Select({
        "<script>",
        "SELECT * FROM trip_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (trip_no LIKE CONCAT('%',#{keyword},'%') OR traveler_no LIKE CONCAT('%',#{keyword},'%') OR travel_date LIKE CONCAT('%',#{keyword},'%') OR completion_status LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<TripRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM trip_record WHERE id = #{id}")
    TripRecord selectById(Long id);

    @Insert("INSERT INTO trip_record (trip_no, traveler_no, travel_date, travel_route, completion_status, status, created_time, updated_time) VALUES (#{tripNo}, #{travelerNo}, #{travelDate}, #{travelRoute}, #{completionStatus}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(TripRecord entity);

    @Update("UPDATE trip_record SET trip_no = #{tripNo}, traveler_no = #{travelerNo}, travel_date = #{travelDate}, travel_route = #{travelRoute}, completion_status = #{completionStatus}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(TripRecord entity);

    @Delete("DELETE FROM trip_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM trip_record")
    long countAll();

    @Update("UPDATE trip_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}

