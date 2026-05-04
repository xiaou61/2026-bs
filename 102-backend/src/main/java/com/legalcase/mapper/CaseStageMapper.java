package com.legalcase.mapper;

import com.legalcase.entity.CaseStage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CaseStageMapper {
    @Select({"<script>",
            "SELECT * FROM case_stage",
            "<where>",
            "<if test=\"keyword != null and keyword != \'\'\"> AND (stage_name LIKE CONCAT('%',#{keyword},'%') OR remark LIKE CONCAT('%',#{keyword},'%')) </if>",
            "<if test=\"caseId != null\"> AND case_id = #{caseId} </if>",
            "<if test=\"status != null\"> AND status = #{status} </if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"})
    List<CaseStage> selectPage(@Param("keyword") String keyword, @Param("caseId") Long caseId, @Param("status") Integer status);

    @Select("SELECT * FROM case_stage WHERE id=#{id}")
    CaseStage selectById(Long id);

    @Insert("INSERT INTO case_stage(case_id, stage_name, stage_order, status, plan_date, finish_date, remark, create_time, update_time) VALUES(#{caseId}, #{stageName}, #{stageOrder}, #{status}, #{planDate}, #{finishDate}, #{remark}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CaseStage entity);

    @Update("UPDATE case_stage SET case_id=#{caseId}, stage_name=#{stageName}, stage_order=#{stageOrder}, status=#{status}, plan_date=#{planDate}, finish_date=#{finishDate}, remark=#{remark}, create_time=#{createTime}, update_time=#{updateTime} WHERE id=#{id}")
    int updateById(CaseStage entity);

    @Delete("DELETE FROM case_stage WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM case_stage")
    long countAll();
}
