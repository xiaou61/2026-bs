package com.foodinspect.mapper;

import com.foodinspect.entity.FoodItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FoodItemMapper {
    @Select({
        "<script>",
        "SELECT * FROM food_item",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (food_no LIKE CONCAT('%',#{keyword},'%') OR food_name LIKE CONCAT('%',#{keyword},'%') OR food_type LIKE CONCAT('%',#{keyword},'%') OR batch_no LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<FoodItem> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM food_item WHERE id = #{id}")
    FoodItem selectById(Long id);

    @Insert("INSERT INTO food_item (food_no, food_name, food_type, batch_no, inspection_status, status, created_time, updated_time) VALUES (#{foodNo}, #{foodName}, #{foodType}, #{batchNo}, #{inspectionStatus}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(FoodItem entity);

    @Update("UPDATE food_item SET food_no = #{foodNo}, food_name = #{foodName}, food_type = #{foodType}, batch_no = #{batchNo}, inspection_status = #{inspectionStatus}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(FoodItem entity);

    @Delete("DELETE FROM food_item WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM food_item")
    long countAll();

    @Update("UPDATE food_item SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}






