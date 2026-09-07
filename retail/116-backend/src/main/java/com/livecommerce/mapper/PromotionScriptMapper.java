package com.livecommerce.mapper;

import com.livecommerce.entity.PromotionScript;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PromotionScriptMapper {
    @Select({
        "<script>",
        "SELECT * FROM promotion_script",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (script_no LIKE CONCAT('%',#{keyword},'%') OR product_name LIKE CONCAT('%',#{keyword},'%') OR main_selling_point LIKE CONCAT('%',#{keyword},'%') OR review_user LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<PromotionScript> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM promotion_script WHERE id = #{id}")
    PromotionScript selectById(Long id);

    @Insert("INSERT INTO promotion_script (script_no, product_name, main_selling_point, coupon_text, review_user, version_no, status, created_time, updated_time) VALUES (#{scriptNo}, #{productName}, #{mainSellingPoint}, #{couponText}, #{reviewUser}, #{versionNo}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(PromotionScript entity);

    @Update("UPDATE promotion_script SET script_no = #{scriptNo}, product_name = #{productName}, main_selling_point = #{mainSellingPoint}, coupon_text = #{couponText}, review_user = #{reviewUser}, version_no = #{versionNo}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(PromotionScript entity);

    @Delete("DELETE FROM promotion_script WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM promotion_script")
    long countAll();

    @Update("UPDATE promotion_script SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
