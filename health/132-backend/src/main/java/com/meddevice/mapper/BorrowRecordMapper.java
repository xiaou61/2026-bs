package com.meddevice.mapper;

import com.meddevice.entity.BorrowRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BorrowRecordMapper {
    @Select({
        "<script>",
        "SELECT * FROM borrow_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (borrow_no LIKE CONCAT('%',#{keyword},'%') OR request_no LIKE CONCAT('%',#{keyword},'%') OR device_no LIKE CONCAT('%',#{keyword},'%') OR borrow_time LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<BorrowRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM borrow_record WHERE id = #{id}")
    BorrowRecord selectById(Long id);

    @Insert("INSERT INTO borrow_record (borrow_no, request_no, device_no, borrow_time, borrower_name, status, created_time, updated_time) VALUES (#{borrowNo}, #{requestNo}, #{deviceNo}, #{borrowTime}, #{borrowerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(BorrowRecord entity);

    @Update("UPDATE borrow_record SET borrow_no = #{borrowNo}, request_no = #{requestNo}, device_no = #{deviceNo}, borrow_time = #{borrowTime}, borrower_name = #{borrowerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(BorrowRecord entity);

    @Delete("DELETE FROM borrow_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM borrow_record")
    long countAll();

    @Update("UPDATE borrow_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
