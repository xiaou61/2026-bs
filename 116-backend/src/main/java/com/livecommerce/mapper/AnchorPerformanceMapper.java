package com.livecommerce.mapper;

import com.livecommerce.entity.AnchorPerformance;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AnchorPerformanceMapper {
    @Select({
        "<script>",
        "SELECT * FROM anchor_performance",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (performance_no LIKE CONCAT('%',#{keyword},'%') OR anchor_name LIKE CONCAT('%',#{keyword},'%') OR session_title LIKE CONCAT('%',#{keyword},'%') OR status LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AnchorPerformance> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM anchor_performance WHERE id = #{id}")
    AnchorPerformance selectById(Long id);

    @Insert("INSERT INTO anchor_performance (performance_no, anchor_name, session_title, gmv_amount, order_count, conversion_rate, status, created_time, updated_time) VALUES (#{performanceNo}, #{anchorName}, #{sessionTitle}, #{gmvAmount}, #{orderCount}, #{conversionRate}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AnchorPerformance entity);

    @Update("UPDATE anchor_performance SET performance_no = #{performanceNo}, anchor_name = #{anchorName}, session_title = #{sessionTitle}, gmv_amount = #{gmvAmount}, order_count = #{orderCount}, conversion_rate = #{conversionRate}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AnchorPerformance entity);

    @Delete("DELETE FROM anchor_performance WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM anchor_performance")
    long countAll();

    @Update("UPDATE anchor_performance SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
