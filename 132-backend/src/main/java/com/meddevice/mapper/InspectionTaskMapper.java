package com.meddevice.mapper;

import com.meddevice.entity.InspectionTask;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface InspectionTaskMapper {
    @Select({
        "<script>",
        "SELECT * FROM inspection_task",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (inspection_no LIKE CONCAT('%',#{keyword},'%') OR device_no LIKE CONCAT('%',#{keyword},'%') OR inspection_date LIKE CONCAT('%',#{keyword},'%') OR inspector_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<InspectionTask> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM inspection_task WHERE id = #{id}")
    InspectionTask selectById(Long id);

    @Insert("INSERT INTO inspection_task (inspection_no, device_no, inspection_date, inspector_name, result_text, status, created_time, updated_time) VALUES (#{inspectionNo}, #{deviceNo}, #{inspectionDate}, #{inspectorName}, #{resultText}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(InspectionTask entity);

    @Update("UPDATE inspection_task SET inspection_no = #{inspectionNo}, device_no = #{deviceNo}, inspection_date = #{inspectionDate}, inspector_name = #{inspectorName}, result_text = #{resultText}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(InspectionTask entity);

    @Delete("DELETE FROM inspection_task WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM inspection_task")
    long countAll();

    @Update("UPDATE inspection_task SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
