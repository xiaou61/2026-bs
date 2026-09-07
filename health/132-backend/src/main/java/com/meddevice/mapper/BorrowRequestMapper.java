package com.meddevice.mapper;

import com.meddevice.entity.BorrowRequest;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BorrowRequestMapper {
    @Select({
        "<script>",
        "SELECT * FROM borrow_request",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (request_no LIKE CONCAT('%',#{keyword},'%') OR device_no LIKE CONCAT('%',#{keyword},'%') OR dept_name LIKE CONCAT('%',#{keyword},'%') OR applicant_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<BorrowRequest> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM borrow_request WHERE id = #{id}")
    BorrowRequest selectById(Long id);

    @Insert("INSERT INTO borrow_request (request_no, device_no, dept_name, applicant_name, request_time, status, created_time, updated_time) VALUES (#{requestNo}, #{deviceNo}, #{deptName}, #{applicantName}, #{requestTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(BorrowRequest entity);

    @Update("UPDATE borrow_request SET request_no = #{requestNo}, device_no = #{deviceNo}, dept_name = #{deptName}, applicant_name = #{applicantName}, request_time = #{requestTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(BorrowRequest entity);

    @Delete("DELETE FROM borrow_request WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM borrow_request")
    long countAll();

    @Update("UPDATE borrow_request SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
