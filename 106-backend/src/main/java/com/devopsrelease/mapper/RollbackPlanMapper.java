package com.devopsrelease.mapper;

import com.devopsrelease.entity.RollbackPlan;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RollbackPlanMapper {
    @Select({
        "<script>",
        "SELECT * FROM rollback_plan",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (rollback_version LIKE CONCAT('%',#{keyword},'%') OR strategy_name LIKE CONCAT('%',#{keyword},'%') OR trigger_condition LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<RollbackPlan> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM rollback_plan WHERE id = #{id}")
    RollbackPlan selectById(Long id);

    @Insert("INSERT INTO rollback_plan (plan_id, service_id, rollback_version, strategy_name, trigger_condition, owner_name, status, created_time, updated_time) VALUES (#{planId}, #{serviceId}, #{rollbackVersion}, #{strategyName}, #{triggerCondition}, #{ownerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RollbackPlan entity);

    @Update("UPDATE rollback_plan SET plan_id = #{planId}, service_id = #{serviceId}, rollback_version = #{rollbackVersion}, strategy_name = #{strategyName}, trigger_condition = #{triggerCondition}, owner_name = #{ownerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(RollbackPlan entity);

    @Delete("DELETE FROM rollback_plan WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM rollback_plan")
    long countAll();


    @Update("UPDATE rollback_plan SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);


}
