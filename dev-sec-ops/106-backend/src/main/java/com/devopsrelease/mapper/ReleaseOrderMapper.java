package com.devopsrelease.mapper;

import com.devopsrelease.entity.ReleaseOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ReleaseOrderMapper {
    @Select({
        "<script>",
        "SELECT * FROM release_order",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (order_no LIKE CONCAT('%',#{keyword},'%') OR version_no LIKE CONCAT('%',#{keyword},'%') OR applicant LIKE CONCAT('%',#{keyword},'%') OR release_scope LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ReleaseOrder> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM release_order WHERE id = #{id}")
    ReleaseOrder selectById(Long id);

    @Insert("INSERT INTO release_order (order_no, plan_id, service_id, version_no, applicant, release_scope, status, scheduled_at, summary, created_time, updated_time) VALUES (#{orderNo}, #{planId}, #{serviceId}, #{versionNo}, #{applicant}, #{releaseScope}, #{status}, #{scheduledAt}, #{summary}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ReleaseOrder entity);

    @Update("UPDATE release_order SET order_no = #{orderNo}, plan_id = #{planId}, service_id = #{serviceId}, version_no = #{versionNo}, applicant = #{applicant}, release_scope = #{releaseScope}, status = #{status}, scheduled_at = #{scheduledAt}, summary = #{summary}, updated_time = NOW() WHERE id = #{id}")
    int update(ReleaseOrder entity);

    @Delete("DELETE FROM release_order WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM release_order")
    long countAll();


    @Update("UPDATE release_order SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);


}
