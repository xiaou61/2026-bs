package com.student.mapper;

import com.student.entity.ActivitySignup;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface SignupMapper {

    @Select("<script>" +
            "select s.* from activity_signup s left join campus_activity a on s.activity_id=a.id where 1=1 " +
            "<if test='activityId!=null'>and s.activity_id=#{activityId}</if> " +
            "<if test='studentId!=null'>and s.student_id=#{studentId}</if> " +
            "<if test='status!=null'>and s.status=#{status}</if> " +
            "<if test='organizerId!=null'>and a.organizer_id=#{organizerId}</if> " +
            "order by id desc" +
            "</script>")
    List<ActivitySignup> selectPageList(@Param("activityId") Long activityId, @Param("studentId") Long studentId, @Param("status") Integer status, @Param("organizerId") Long organizerId);

    @Select("select * from activity_signup where id=#{id}")
    ActivitySignup selectById(@Param("id") Long id);

    @Select("select * from activity_signup where activity_id=#{activityId} and student_id=#{studentId}")
    ActivitySignup selectByActivityAndStudent(@Param("activityId") Long activityId, @Param("studentId") Long studentId);

    @Insert("insert into activity_signup(activity_id,student_id,status,checkin_time,remark) values(#{activityId},#{studentId},#{status},#{checkinTime},#{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ActivitySignup signup);

    @Update("update activity_signup set status=#{status},checkin_time=#{checkinTime},remark=#{remark} where id=#{id}")
    int updateStatus(ActivitySignup signup);

    @Delete("delete from activity_signup where id=#{id}")
    int deleteById(@Param("id") Long id);

    @Delete("delete from activity_signup where activity_id=#{activityId}")
    int deleteByActivityId(@Param("activityId") Long activityId);

    @Select("select count(1) from activity_signup")
    Long countAll();

    @Select("select date(create_time) as day, count(1) as total from activity_signup where create_time between #{start} and #{end} group by date(create_time)")
    List<Map<String, Object>> dailyCreatedCount(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
