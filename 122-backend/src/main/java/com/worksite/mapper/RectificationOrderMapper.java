package com.worksite.mapper;

import com.worksite.entity.RectificationOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RectificationOrderMapper {
    @Select({
        "<script>",
        "SELECT * FROM rectification_order",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (order_no LIKE CONCAT('%',#{keyword},'%') OR hazard_no LIKE CONCAT('%',#{keyword},'%') OR responsible_team LIKE CONCAT('%',#{keyword},'%') OR deadline_time LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<RectificationOrder> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM rectification_order WHERE id = #{id}")
    RectificationOrder selectById(Long id);

    @Insert("INSERT INTO rectification_order (order_no, hazard_no, responsible_team, deadline_time, rectify_requirement, verify_status, status, created_time, updated_time) VALUES (#{orderNo}, #{hazardNo}, #{responsibleTeam}, #{deadlineTime}, #{rectifyRequirement}, #{verifyStatus}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RectificationOrder entity);

    @Update("UPDATE rectification_order SET order_no = #{orderNo}, hazard_no = #{hazardNo}, responsible_team = #{responsibleTeam}, deadline_time = #{deadlineTime}, rectify_requirement = #{rectifyRequirement}, verify_status = #{verifyStatus}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(RectificationOrder entity);

    @Delete("DELETE FROM rectification_order WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM rectification_order")
    long countAll();

    @Update("UPDATE rectification_order SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
