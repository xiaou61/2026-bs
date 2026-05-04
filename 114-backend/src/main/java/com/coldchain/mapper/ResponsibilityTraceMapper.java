package com.coldchain.mapper;

import com.coldchain.entity.ResponsibilityTrace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ResponsibilityTraceMapper {
    @Select({
        "<script>",
        "SELECT * FROM responsibility_trace",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (trace_no LIKE CONCAT('%',#{keyword},'%') OR order_no LIKE CONCAT('%',#{keyword},'%') OR responsible_party LIKE CONCAT('%',#{keyword},'%') OR reason_text LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ResponsibilityTrace> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM responsibility_trace WHERE id = #{id}")
    ResponsibilityTrace selectById(Long id);

    @Insert("INSERT INTO responsibility_trace (trace_no, order_no, responsible_party, node_name, reason_text, impact_scope, status, created_time, updated_time) VALUES (#{traceNo}, #{orderNo}, #{responsibleParty}, #{nodeName}, #{reasonText}, #{impactScope}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ResponsibilityTrace entity);

    @Update("UPDATE responsibility_trace SET trace_no = #{traceNo}, order_no = #{orderNo}, responsible_party = #{responsibleParty}, node_name = #{nodeName}, reason_text = #{reasonText}, impact_scope = #{impactScope}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ResponsibilityTrace entity);

    @Delete("DELETE FROM responsibility_trace WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM responsibility_trace")
    long countAll();

    @Update("UPDATE responsibility_trace SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

}
