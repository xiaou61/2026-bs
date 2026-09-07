package com.outpatientexam.mapper;

import com.outpatientexam.entity.ExamItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ExamItemMapper {
    @Select({
            "<script>",
            "SELECT * FROM exam_item",
            "<where>",
            "<if test='keyword != null and keyword != \"\"'> AND (item_no LIKE CONCAT('%',#{keyword},'%') OR item_name LIKE CONCAT('%',#{keyword},'%') OR category LIKE CONCAT('%',#{keyword},'%') OR department_name LIKE CONCAT('%',#{keyword},'%'))</if>",
            "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"
    })
    List<ExamItem> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM exam_item WHERE id = #{id}")
    ExamItem selectById(Long id);

    @Insert("INSERT INTO exam_item (item_no, item_name, category, department_name, duration_minutes, status, created_time, updated_time) VALUES (#{itemNo}, #{itemName}, #{category}, #{departmentName}, #{durationMinutes}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ExamItem entity);

    @Update("UPDATE exam_item SET item_no = #{itemNo}, item_name = #{itemName}, category = #{category}, department_name = #{departmentName}, duration_minutes = #{durationMinutes}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ExamItem entity);

    @Delete("DELETE FROM exam_item WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM exam_item")
    long countAll();

    @Update("UPDATE exam_item SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
