package com.vehicleclaim.mapper;

import com.vehicleclaim.entity.MaterialChecklist;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MaterialChecklistMapper {
    @Select({
        "<script>",
        "SELECT * FROM material_checklist",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (checklist_no LIKE CONCAT('%',#{keyword},'%') OR report_no LIKE CONCAT('%',#{keyword},'%') OR material_type LIKE CONCAT('%',#{keyword},'%') OR material_desc LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<MaterialChecklist> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM material_checklist WHERE id = #{id}")
    MaterialChecklist selectById(Long id);

    @Insert("INSERT INTO material_checklist (checklist_no, report_no, material_type, material_desc, submit_time, status, created_time, updated_time) VALUES (#{checklistNo}, #{reportNo}, #{materialType}, #{materialDesc}, #{submitTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(MaterialChecklist entity);

    @Update("UPDATE material_checklist SET checklist_no = #{checklistNo}, report_no = #{reportNo}, material_type = #{materialType}, material_desc = #{materialDesc}, submit_time = #{submitTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(MaterialChecklist entity);

    @Delete("DELETE FROM material_checklist WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM material_checklist")
    long countAll();

    @Update("UPDATE material_checklist SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
