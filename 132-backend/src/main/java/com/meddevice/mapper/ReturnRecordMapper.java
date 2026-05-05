package com.meddevice.mapper;

import com.meddevice.entity.ReturnRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ReturnRecordMapper {
    @Select({
        "<script>",
        "SELECT * FROM return_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (return_no LIKE CONCAT('%',#{keyword},'%') OR borrow_no LIKE CONCAT('%',#{keyword},'%') OR return_time LIKE CONCAT('%',#{keyword},'%') OR check_result LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ReturnRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM return_record WHERE id = #{id}")
    ReturnRecord selectById(Long id);

    @Insert("INSERT INTO return_record (return_no, borrow_no, return_time, check_result, receiver_name, status, created_time, updated_time) VALUES (#{returnNo}, #{borrowNo}, #{returnTime}, #{checkResult}, #{receiverName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ReturnRecord entity);

    @Update("UPDATE return_record SET return_no = #{returnNo}, borrow_no = #{borrowNo}, return_time = #{returnTime}, check_result = #{checkResult}, receiver_name = #{receiverName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ReturnRecord entity);

    @Delete("DELETE FROM return_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM return_record")
    long countAll();

    @Update("UPDATE return_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
