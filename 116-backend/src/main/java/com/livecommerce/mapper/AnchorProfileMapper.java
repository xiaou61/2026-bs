package com.livecommerce.mapper;

import com.livecommerce.entity.AnchorProfile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AnchorProfileMapper {
    @Select({
        "<script>",
        "SELECT * FROM anchor_profile",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (anchor_name LIKE CONCAT('%',#{keyword},'%') OR anchor_no LIKE CONCAT('%',#{keyword},'%') OR special_category LIKE CONCAT('%',#{keyword},'%') OR level_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AnchorProfile> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM anchor_profile WHERE id = #{id}")
    AnchorProfile selectById(Long id);

    @Insert("INSERT INTO anchor_profile (anchor_name, anchor_no, special_category, level_name, commission_rate, phone, status, created_time, updated_time) VALUES (#{anchorName}, #{anchorNo}, #{specialCategory}, #{levelName}, #{commissionRate}, #{phone}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AnchorProfile entity);

    @Update("UPDATE anchor_profile SET anchor_name = #{anchorName}, anchor_no = #{anchorNo}, special_category = #{specialCategory}, level_name = #{levelName}, commission_rate = #{commissionRate}, phone = #{phone}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AnchorProfile entity);

    @Delete("DELETE FROM anchor_profile WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM anchor_profile")
    long countAll();

    @Update("UPDATE anchor_profile SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
