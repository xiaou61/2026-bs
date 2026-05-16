package com.foodinspect.mapper;

import com.foodinspect.entity.InspectionNotice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface InspectionNoticeMapper {
    @Select({
        "<script>",
        "SELECT * FROM inspection_notice",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (notice_no LIKE CONCAT('%',#{keyword},'%') OR food_name LIKE CONCAT('%',#{keyword},'%') OR notice_type LIKE CONCAT('%',#{keyword},'%') OR receiver_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<InspectionNotice> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM inspection_notice WHERE id = #{id}")
    InspectionNotice selectById(Long id);

    @Insert("INSERT INTO inspection_notice (notice_no, food_name, notice_type, notice_content, receiver_name, status, created_time, updated_time) VALUES (#{noticeNo}, #{foodName}, #{noticeType}, #{noticeContent}, #{receiverName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(InspectionNotice entity);

    @Update("UPDATE inspection_notice SET notice_no = #{noticeNo}, food_name = #{foodName}, notice_type = #{noticeType}, notice_content = #{noticeContent}, receiver_name = #{receiverName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(InspectionNotice entity);

    @Delete("DELETE FROM inspection_notice WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM inspection_notice")
    long countAll();

    @Update("UPDATE inspection_notice SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}






