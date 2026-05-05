package com.livecommerce.mapper;

import com.livecommerce.entity.LiveSession;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface LiveSessionMapper {
    @Select({
        "<script>",
        "SELECT * FROM live_session",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (session_no LIKE CONCAT('%',#{keyword},'%') OR session_title LIKE CONCAT('%',#{keyword},'%') OR channel_name LIKE CONCAT('%',#{keyword},'%') OR anchor_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<LiveSession> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM live_session WHERE id = #{id}")
    LiveSession selectById(Long id);

    @Insert("INSERT INTO live_session (session_no, session_title, channel_name, anchor_name, start_time, target_gmv, status, created_time, updated_time) VALUES (#{sessionNo}, #{sessionTitle}, #{channelName}, #{anchorName}, #{startTime}, #{targetGmv}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(LiveSession entity);

    @Update("UPDATE live_session SET session_no = #{sessionNo}, session_title = #{sessionTitle}, channel_name = #{channelName}, anchor_name = #{anchorName}, start_time = #{startTime}, target_gmv = #{targetGmv}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(LiveSession entity);

    @Delete("DELETE FROM live_session WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM live_session")
    long countAll();

    @Update("UPDATE live_session SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
