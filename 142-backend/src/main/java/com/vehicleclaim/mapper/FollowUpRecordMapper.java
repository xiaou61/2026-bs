package com.vehicleclaim.mapper;

import com.vehicleclaim.entity.FollowUpRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FollowUpRecordMapper {
    @Select({
        "<script>",
        "SELECT * FROM performance_statistic",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (stat_no LIKE CONCAT('%',#{keyword},'%') OR project_no LIKE CONCAT('%',#{keyword},'%') OR stat_month LIKE CONCAT('%',#{keyword},'%') OR claim_count LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<FollowUpRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM performance_statistic WHERE id = #{id}")
    FollowUpRecord selectById(Long id);

    @Insert("INSERT INTO performance_statistic (stat_no, project_no, stat_month, claim_count, achievement_count, status, created_time, updated_time) VALUES (#{statNo}, #{projectNo}, #{statMonth}, #{claimCount}, #{achievementCount}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(FollowUpRecord entity);

    @Update("UPDATE performance_statistic SET stat_no = #{statNo}, project_no = #{projectNo}, stat_month = #{statMonth}, claim_count = #{claimCount}, achievement_count = #{achievementCount}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(FollowUpRecord entity);

    @Delete("DELETE FROM performance_statistic WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM performance_statistic")
    long countAll();

    @Update("UPDATE performance_statistic SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}




