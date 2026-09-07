package com.zerotrust.mapper;

import com.zerotrust.entity.EmployeeAccount;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EmployeeAccountMapper {
    @Select({
        "<script>",
        "SELECT * FROM employee_account",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (employee_name LIKE CONCAT('%',#{keyword},'%') OR employee_no LIKE CONCAT('%',#{keyword},'%') OR account_name LIKE CONCAT('%',#{keyword},'%') OR department_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<EmployeeAccount> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM employee_account WHERE id = #{id}")
    EmployeeAccount selectById(Long id);

    @Insert("INSERT INTO employee_account (employee_name, employee_no, account_name, department_name, mfa_status, status, created_time, updated_time) VALUES (#{employeeName}, #{employeeNo}, #{accountName}, #{departmentName}, #{mfaStatus}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(EmployeeAccount entity);

    @Update("UPDATE employee_account SET employee_name = #{employeeName}, employee_no = #{employeeNo}, account_name = #{accountName}, department_name = #{departmentName}, mfa_status = #{mfaStatus}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(EmployeeAccount entity);

    @Delete("DELETE FROM employee_account WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM employee_account")
    long countAll();

    @Update("UPDATE employee_account SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
