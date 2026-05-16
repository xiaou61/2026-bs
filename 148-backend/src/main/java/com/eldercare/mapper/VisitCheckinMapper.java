package com.eldercare.mapper;

import com.eldercare.entity.VisitCheckin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface VisitCheckinMapper {
    @Select({
        "<script>",
        "SELECT * FROM visit_checkin",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (checkin_no LIKE CONCAT('%',#{keyword},'%') OR elder_name LIKE CONCAT('%',#{keyword},'%') OR checkin_type LIKE CONCAT('%',#{keyword},'%') OR checkin_remark LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<VisitCheckin> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM visit_checkin WHERE id = #{id}")
    VisitCheckin selectById(Long id);

    @Insert("INSERT INTO visit_checkin (checkin_no, elder_name, checkin_type, checkin_remark, checkin_time, status, created_time, updated_time) VALUES (#{checkinNo}, #{elderName}, #{checkinType}, #{checkinRemark}, #{checkinTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(VisitCheckin entity);

    @Update("UPDATE visit_checkin SET checkin_no = #{checkinNo}, elder_name = #{elderName}, checkin_type = #{checkinType}, checkin_remark = #{checkinRemark}, checkin_time = #{checkinTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(VisitCheckin entity);

    @Delete("DELETE FROM visit_checkin WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM visit_checkin")
    long countAll();

    @Update("UPDATE visit_checkin SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
