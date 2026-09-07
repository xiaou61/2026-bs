package com.econtract.mapper;

import com.econtract.entity.ContractNotice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ContractNoticeMapper {
    @Select({
        "<script>",
        "SELECT * FROM contract_notice",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (notice_no LIKE CONCAT('%',#{keyword},'%') OR notice_title LIKE CONCAT('%',#{keyword},'%') OR notice_type LIKE CONCAT('%',#{keyword},'%') OR receiver_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ContractNotice> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM contract_notice WHERE id = #{id}")
    ContractNotice selectById(Long id);

    @Insert("INSERT INTO contract_notice (notice_no, notice_title, notice_type, receiver_name, publish_time, status, created_time, updated_time) VALUES (#{noticeNo}, #{noticeTitle}, #{noticeType}, #{receiverName}, #{publishTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ContractNotice entity);

    @Update("UPDATE contract_notice SET notice_no = #{noticeNo}, notice_title = #{noticeTitle}, notice_type = #{noticeType}, receiver_name = #{receiverName}, publish_time = #{publishTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ContractNotice entity);

    @Delete("DELETE FROM contract_notice WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM contract_notice")
    long countAll();

    @Update("UPDATE contract_notice SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}



