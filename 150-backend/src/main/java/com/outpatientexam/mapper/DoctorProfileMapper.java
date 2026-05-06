package com.outpatientexam.mapper;

import com.outpatientexam.entity.DoctorProfile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DoctorProfileMapper {
    @Select({
        "<script>",
        "SELECT * FROM budget_allocation",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (budget_no LIKE CONCAT('%',#{keyword},'%') OR project_no LIKE CONCAT('%',#{keyword},'%') OR category_name LIKE CONCAT('%',#{keyword},'%') OR budget_amount LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<DoctorProfile> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM budget_allocation WHERE id = #{id}")
    DoctorProfile selectById(Long id);

    @Insert("INSERT INTO budget_allocation (budget_no, project_no, category_name, budget_amount, used_amount, status, created_time, updated_time) VALUES (#{budgetNo}, #{projectNo}, #{categoryName}, #{budgetAmount}, #{usedAmount}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(DoctorProfile entity);

    @Update("UPDATE budget_allocation SET budget_no = #{budgetNo}, project_no = #{projectNo}, category_name = #{categoryName}, budget_amount = #{budgetAmount}, used_amount = #{usedAmount}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(DoctorProfile entity);

    @Delete("DELETE FROM budget_allocation WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM budget_allocation")
    long countAll();

    @Update("UPDATE budget_allocation SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}








