package com.devopsrelease.mapper;

import com.devopsrelease.entity.ChangeChecklist;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ChangeChecklistMapper {
    @Select({
        "<script>",
        "SELECT * FROM change_checklist",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (item_name LIKE CONCAT('%',#{keyword},'%') OR check_owner LIKE CONCAT('%',#{keyword},'%') OR check_result LIKE CONCAT('%',#{keyword},'%') OR evidence_url LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ChangeChecklist> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM change_checklist WHERE id = #{id}")
    ChangeChecklist selectById(Long id);

    @Insert("INSERT INTO change_checklist (order_id, item_name, check_owner, required_flag, check_result, evidence_url, status, created_time, updated_time) VALUES (#{orderId}, #{itemName}, #{checkOwner}, #{requiredFlag}, #{checkResult}, #{evidenceUrl}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ChangeChecklist entity);

    @Update("UPDATE change_checklist SET order_id = #{orderId}, item_name = #{itemName}, check_owner = #{checkOwner}, required_flag = #{requiredFlag}, check_result = #{checkResult}, evidence_url = #{evidenceUrl}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ChangeChecklist entity);

    @Delete("DELETE FROM change_checklist WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM change_checklist")
    long countAll();


    @Update("UPDATE change_checklist SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);


}
