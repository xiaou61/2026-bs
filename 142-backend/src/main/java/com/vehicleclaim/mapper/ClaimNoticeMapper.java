package com.vehicleclaim.mapper;

import com.vehicleclaim.entity.ClaimNotice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ClaimNoticeMapper {
    @Select({
        "<script>",
        "SELECT * FROM claim_notice",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (notice_no LIKE CONCAT('%',#{keyword},'%') OR report_no LIKE CONCAT('%',#{keyword},'%') OR notice_type LIKE CONCAT('%',#{keyword},'%') OR receiver_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ClaimNotice> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM claim_notice WHERE id = #{id}")
    ClaimNotice selectById(Long id);

    @Insert("INSERT INTO claim_notice (notice_no, report_no, notice_type, notice_content, receiver_name, status, created_time, updated_time) VALUES (#{noticeNo}, #{reportNo}, #{noticeType}, #{noticeContent}, #{receiverName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ClaimNotice entity);

    @Update("UPDATE claim_notice SET notice_no = #{noticeNo}, report_no = #{reportNo}, notice_type = #{noticeType}, notice_content = #{noticeContent}, receiver_name = #{receiverName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ClaimNotice entity);

    @Delete("DELETE FROM claim_notice WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM claim_notice")
    long countAll();

    @Update("UPDATE claim_notice SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
