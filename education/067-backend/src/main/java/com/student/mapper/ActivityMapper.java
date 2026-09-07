package com.student.mapper;

import com.student.entity.Activity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ActivityMapper {

    @Select("<script>" +
            "select * from campus_activity where 1=1 " +
            "<if test='title!=null and title!=\"\"'>and title like concat('%',#{title},'%')</if> " +
            "<if test='organizerId!=null'>and organizer_id=#{organizerId}</if> " +
            "<if test='status!=null'>and status=#{status}</if> " +
            "order by id desc" +
            "</script>")
    List<Activity> selectPageList(@Param("title") String title, @Param("organizerId") Long organizerId, @Param("status") Integer status);

    @Select("<script>" +
            "select * from campus_activity where 1=1 " +
            "<if test='status!=null'>and status=#{status}</if> " +
            "order by id desc" +
            "</script>")
    List<Activity> selectList(@Param("status") Integer status);

    @Select("select * from campus_activity where id=#{id}")
    Activity selectById(@Param("id") Long id);

    @Insert("insert into campus_activity(title,organizer_id,location,start_time,end_time,max_participant,participant_count,status,content) values(#{title},#{organizerId},#{location},#{startTime},#{endTime},#{maxParticipant},#{participantCount},#{status},#{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Activity activity);

    @Update("update campus_activity set title=#{title},organizer_id=#{organizerId},location=#{location},start_time=#{startTime},end_time=#{endTime},max_participant=#{maxParticipant},participant_count=#{participantCount},status=#{status},content=#{content} where id=#{id}")
    int update(Activity activity);

    @Update("update campus_activity set participant_count=#{participantCount} where id=#{id}")
    int updateParticipantCount(@Param("id") Long id, @Param("participantCount") Integer participantCount);

    @Delete("delete from campus_activity where id=#{id}")
    int deleteById(@Param("id") Long id);

    @Select("select count(1) from campus_activity")
    Long countAll();
}
