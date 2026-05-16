package com.foodinspect.mapper;

import com.foodinspect.entity.PublicReport;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PublicReportMapper {
    @Select({
        "<script>",
        "SELECT * FROM public_report",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (report_no LIKE CONCAT('%',#{keyword},'%') OR food_name LIKE CONCAT('%',#{keyword},'%') OR report_title LIKE CONCAT('%',#{keyword},'%') OR publish_channel LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<PublicReport> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM public_report WHERE id = #{id}")
    PublicReport selectById(Long id);

    @Insert("INSERT INTO public_report (report_no, food_name, report_title, publish_channel, publish_time, status, created_time, updated_time) VALUES (#{reportNo}, #{foodName}, #{reportTitle}, #{publishChannel}, #{publishTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(PublicReport entity);

    @Update("UPDATE public_report SET report_no = #{reportNo}, food_name = #{foodName}, report_title = #{reportTitle}, publish_channel = #{publishChannel}, publish_time = #{publishTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(PublicReport entity);

    @Delete("DELETE FROM public_report WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM public_report")
    long countAll();

    @Update("UPDATE public_report SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}






