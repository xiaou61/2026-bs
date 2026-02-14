package com.student.mapper;

import com.student.entity.JobApply;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface ApplyMapper {

    @Select("<script>" +
            "select a.* from job_apply a left join intern_job j on a.job_id=j.id where 1=1 " +
            "<if test='jobId!=null'>and a.job_id=#{jobId}</if> " +
            "<if test='studentId!=null'>and a.student_id=#{studentId}</if> " +
            "<if test='status!=null'>and a.status=#{status}</if> " +
            "<if test='publisherId!=null'>and j.publisher_id=#{publisherId}</if> " +
            "order by id desc" +
            "</script>")
    List<JobApply> selectPageList(@Param("jobId") Long jobId, @Param("studentId") Long studentId, @Param("status") Integer status, @Param("publisherId") Long publisherId);

    @Select("select * from job_apply where id=#{id}")
    JobApply selectById(@Param("id") Long id);

    @Select("select * from job_apply where job_id=#{jobId} and student_id=#{studentId}")
    JobApply selectByJobAndStudent(@Param("jobId") Long jobId, @Param("studentId") Long studentId);

    @Insert("insert into job_apply(job_id,student_id,resume_url,status,apply_note,review_note,reviewer_id) values(#{jobId},#{studentId},#{resumeUrl},#{status},#{applyNote},#{reviewNote},#{reviewerId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(JobApply apply);

    @Update("update job_apply set status=#{status},review_note=#{reviewNote},reviewer_id=#{reviewerId} where id=#{id}")
    int updateReview(JobApply apply);

    @Delete("delete from job_apply where id=#{id}")
    int deleteById(@Param("id") Long id);

    @Delete("delete from job_apply where job_id=#{jobId}")
    int deleteByJobId(@Param("jobId") Long jobId);

    @Select("select count(1) from job_apply")
    Long countAll();

    @Select("select date(create_time) as day, count(1) as total from job_apply where create_time between #{start} and #{end} group by date(create_time)")
    List<Map<String, Object>> dailyCreatedCount(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
