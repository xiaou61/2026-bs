package com.cloudcost.mapper;

import com.cloudcost.entity.SavingPlan;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SavingPlanMapper {
    @Select({
        "<script>",
        "SELECT * FROM saving_plan",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (plan_name LIKE CONCAT('%',#{keyword},'%') OR account_name LIKE CONCAT('%',#{keyword},'%') OR coverage_scope LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<SavingPlan> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM saving_plan WHERE id = #{id}")
    SavingPlan selectById(Long id);

    @Insert("INSERT INTO saving_plan (plan_name, account_name, coverage_scope, commit_amount, expected_saving, owner_name, status, created_time, updated_time) VALUES (#{planName}, #{accountName}, #{coverageScope}, #{commitAmount}, #{expectedSaving}, #{ownerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SavingPlan entity);

    @Update("UPDATE saving_plan SET plan_name = #{planName}, account_name = #{accountName}, coverage_scope = #{coverageScope}, commit_amount = #{commitAmount}, expected_saving = #{expectedSaving}, owner_name = #{ownerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(SavingPlan entity);

    @Delete("DELETE FROM saving_plan WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM saving_plan")
    long countAll();

    @Update("UPDATE saving_plan SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
