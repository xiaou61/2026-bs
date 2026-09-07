package com.devopsrelease.mapper;

import com.devopsrelease.entity.RollbackRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RollbackRecordMapper {
    @Select({
        "<script>",
        "SELECT * FROM rollback_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (rollback_no LIKE CONCAT('%',#{keyword},'%') OR operator_name LIKE CONCAT('%',#{keyword},'%') OR reason LIKE CONCAT('%',#{keyword},'%') OR status LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<RollbackRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM rollback_record WHERE id = #{id}")
    RollbackRecord selectById(Long id);

    @Insert("INSERT INTO rollback_record (order_id, plan_id, rollback_no, operator_name, reason, status, started_at, finished_at, created_time, updated_time) VALUES (#{orderId}, #{planId}, #{rollbackNo}, #{operatorName}, #{reason}, #{status}, #{startedAt}, #{finishedAt}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RollbackRecord entity);

    @Update("UPDATE rollback_record SET order_id = #{orderId}, plan_id = #{planId}, rollback_no = #{rollbackNo}, operator_name = #{operatorName}, reason = #{reason}, status = #{status}, started_at = #{startedAt}, finished_at = #{finishedAt}, updated_time = NOW() WHERE id = #{id}")
    int update(RollbackRecord entity);

    @Delete("DELETE FROM rollback_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM rollback_record")
    long countAll();


    @Update("UPDATE rollback_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);


}
