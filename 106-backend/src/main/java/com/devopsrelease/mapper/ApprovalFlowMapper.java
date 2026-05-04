package com.devopsrelease.mapper;

import com.devopsrelease.entity.ApprovalFlow;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ApprovalFlowMapper {
    @Select({
        "<script>",
        "SELECT * FROM approval_flow",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (flow_name LIKE CONCAT('%',#{keyword},'%') OR business_type LIKE CONCAT('%',#{keyword},'%') OR node_name LIKE CONCAT('%',#{keyword},'%') OR approver_role LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ApprovalFlow> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM approval_flow WHERE id = #{id}")
    ApprovalFlow selectById(Long id);

    @Insert("INSERT INTO approval_flow (flow_name, business_type, node_name, approver_role, sort_no, status, created_time, updated_time) VALUES (#{flowName}, #{businessType}, #{nodeName}, #{approverRole}, #{sortNo}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ApprovalFlow entity);

    @Update("UPDATE approval_flow SET flow_name = #{flowName}, business_type = #{businessType}, node_name = #{nodeName}, approver_role = #{approverRole}, sort_no = #{sortNo}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ApprovalFlow entity);

    @Delete("DELETE FROM approval_flow WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM approval_flow")
    long countAll();


    @Update("UPDATE approval_flow SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);


}
