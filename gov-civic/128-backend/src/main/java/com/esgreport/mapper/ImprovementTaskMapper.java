package com.esgreport.mapper;

import com.esgreport.entity.ImprovementTask;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ImprovementTaskMapper {
    @Select({
        "<script>",
        "SELECT * FROM improvement_task",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (task_no LIKE CONCAT('%',#{keyword},'%') OR company_name LIKE CONCAT('%',#{keyword},'%') OR department_name LIKE CONCAT('%',#{keyword},'%') OR improve_item LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ImprovementTask> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM improvement_task WHERE id = #{id}")
    ImprovementTask selectById(Long id);

    @Insert("INSERT INTO improvement_task (task_no, company_name, department_name, improve_item, deadline_time, status, created_time, updated_time) VALUES (#{taskNo}, #{companyName}, #{departmentName}, #{improveItem}, #{deadlineTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ImprovementTask entity);

    @Update("UPDATE improvement_task SET task_no = #{taskNo}, company_name = #{companyName}, department_name = #{departmentName}, improve_item = #{improveItem}, deadline_time = #{deadlineTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ImprovementTask entity);

    @Delete("DELETE FROM improvement_task WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM improvement_task")
    long countAll();

    @Update("UPDATE improvement_task SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
