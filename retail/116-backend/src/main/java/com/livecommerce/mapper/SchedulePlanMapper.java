package com.livecommerce.mapper;

import com.livecommerce.entity.SchedulePlan;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SchedulePlanMapper {
    @Select({
        "<script>",
        "SELECT * FROM schedule_plan",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (plan_no LIKE CONCAT('%',#{keyword},'%') OR session_title LIKE CONCAT('%',#{keyword},'%') OR anchor_name LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<SchedulePlan> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM schedule_plan WHERE id = #{id}")
    SchedulePlan selectById(Long id);

    @Insert("INSERT INTO schedule_plan (plan_no, session_title, plan_date, anchor_name, product_count, owner_name, status, created_time, updated_time) VALUES (#{planNo}, #{sessionTitle}, #{planDate}, #{anchorName}, #{productCount}, #{ownerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SchedulePlan entity);

    @Update("UPDATE schedule_plan SET plan_no = #{planNo}, session_title = #{sessionTitle}, plan_date = #{planDate}, anchor_name = #{anchorName}, product_count = #{productCount}, owner_name = #{ownerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(SchedulePlan entity);

    @Delete("DELETE FROM schedule_plan WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM schedule_plan")
    long countAll();

    @Update("UPDATE schedule_plan SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
