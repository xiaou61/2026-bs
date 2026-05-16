package com.eldercare.mapper;

import com.eldercare.entity.CareNotice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CareNoticeMapper {
    @Select({
        "<script>",
        "SELECT * FROM care_notice",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (notice_no LIKE CONCAT('%',#{keyword},'%') OR elder_name LIKE CONCAT('%',#{keyword},'%') OR notice_type LIKE CONCAT('%',#{keyword},'%') OR receiver_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<CareNotice> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM care_notice WHERE id = #{id}")
    CareNotice selectById(Long id);

    @Insert("INSERT INTO care_notice (notice_no, elder_name, notice_type, notice_content, receiver_name, status, created_time, updated_time) VALUES (#{noticeNo}, #{elderName}, #{noticeType}, #{noticeContent}, #{receiverName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CareNotice entity);

    @Update("UPDATE care_notice SET notice_no = #{noticeNo}, elder_name = #{elderName}, notice_type = #{noticeType}, notice_content = #{noticeContent}, receiver_name = #{receiverName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(CareNotice entity);

    @Delete("DELETE FROM care_notice WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM care_notice")
    long countAll();

    @Update("UPDATE care_notice SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
