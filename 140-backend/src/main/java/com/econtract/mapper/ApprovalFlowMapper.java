package com.econtract.mapper;

import com.econtract.entity.ApprovalFlow;
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
        "<if test='keyword != null and keyword != \"\"'> AND (flow_no LIKE CONCAT('%',#{keyword},'%') OR contract_title LIKE CONCAT('%',#{keyword},'%') OR current_node LIKE CONCAT('%',#{keyword},'%') OR approver_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ApprovalFlow> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM approval_flow WHERE id = #{id}")
    ApprovalFlow selectById(Long id);

    @Insert("INSERT INTO approval_flow (flow_no, contract_title, current_node, approver_name, approval_opinion, status, created_time, updated_time) VALUES (#{flowNo}, #{contractTitle}, #{currentNode}, #{approverName}, #{approvalOpinion}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ApprovalFlow entity);

    @Update("UPDATE approval_flow SET flow_no = #{flowNo}, contract_title = #{contractTitle}, current_node = #{currentNode}, approver_name = #{approverName}, approval_opinion = #{approvalOpinion}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ApprovalFlow entity);

    @Delete("DELETE FROM approval_flow WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM approval_flow")
    long countAll();

    @Update("UPDATE approval_flow SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}



