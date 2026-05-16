package com.accessibletravel.mapper;

import com.accessibletravel.entity.MessageNotice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MessageNoticeMapper {
    @Select({
        "<script>",
        "SELECT * FROM message_notice",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (notice_no LIKE CONCAT('%',#{keyword},'%') OR traveler_no LIKE CONCAT('%',#{keyword},'%') OR notice_type LIKE CONCAT('%',#{keyword},'%') OR receiver_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<MessageNotice> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM message_notice WHERE id = #{id}")
    MessageNotice selectById(Long id);

    @Insert("INSERT INTO message_notice (notice_no, traveler_no, notice_type, notice_content, receiver_name, status, created_time, updated_time) VALUES (#{noticeNo}, #{travelerNo}, #{noticeType}, #{noticeContent}, #{receiverName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(MessageNotice entity);

    @Update("UPDATE message_notice SET notice_no = #{noticeNo}, traveler_no = #{travelerNo}, notice_type = #{noticeType}, notice_content = #{noticeContent}, receiver_name = #{receiverName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(MessageNotice entity);

    @Delete("DELETE FROM message_notice WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM message_notice")
    long countAll();

    @Update("UPDATE message_notice SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}

