package com.coldchain.mapper;

import com.coldchain.entity.DisposalTask;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DisposalTaskMapper {
    @Select({
        "<script>",
        "SELECT * FROM disposal_task",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (task_no LIKE CONCAT('%',#{keyword},'%') OR alert_no LIKE CONCAT('%',#{keyword},'%') OR handler_name LIKE CONCAT('%',#{keyword},'%') OR measure_text LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<DisposalTask> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM disposal_task WHERE id = #{id}")
    DisposalTask selectById(Long id);

    @Insert("INSERT INTO disposal_task (task_no, alert_no, handler_name, measure_text, finish_time, status, created_time, updated_time) VALUES (#{taskNo}, #{alertNo}, #{handlerName}, #{measureText}, #{finishTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(DisposalTask entity);

    @Update("UPDATE disposal_task SET task_no = #{taskNo}, alert_no = #{alertNo}, handler_name = #{handlerName}, measure_text = #{measureText}, finish_time = #{finishTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(DisposalTask entity);

    @Delete("DELETE FROM disposal_task WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM disposal_task")
    long countAll();

    @Update("UPDATE disposal_task SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

}
