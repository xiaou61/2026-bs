package com.housekeeping.mapper;

import com.housekeeping.entity.ScheduleDispatch;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ScheduleDispatchMapper {
    @Select({"<script>", "SELECT * FROM schedule_dispatch", "<where>", "<if test='keyword != null and keyword != \"\"'> AND (record_no LIKE CONCAT('%',#{keyword},'%') OR record_name LIKE CONCAT('%',#{keyword},'%') OR category LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>", "<if test='status != null and status != \"\"'> AND status = #{status}</if>", "</where>", "ORDER BY id DESC", "</script>"})
    List<ScheduleDispatch> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Insert("INSERT INTO schedule_dispatch (record_no,record_name,category,owner_name,plan_time,status,remark,created_time,updated_time) VALUES (#{recordNo},#{recordName},#{category},#{ownerName},#{planTime},#{status},#{remark},NOW(),NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ScheduleDispatch entity);

    @Update("UPDATE schedule_dispatch SET record_no=#{recordNo},record_name=#{recordName},category=#{category},owner_name=#{ownerName},plan_time=#{planTime},status=#{status},remark=#{remark},updated_time=NOW() WHERE id=#{id}")
    int update(ScheduleDispatch entity);

    @Delete("DELETE FROM schedule_dispatch WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM schedule_dispatch")
    long countAll();

    @Update("UPDATE schedule_dispatch SET status=#{status},updated_time=NOW() WHERE id=#{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
