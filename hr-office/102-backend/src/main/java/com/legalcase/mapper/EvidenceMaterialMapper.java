package com.legalcase.mapper;

import com.legalcase.entity.EvidenceMaterial;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EvidenceMaterialMapper {
    @Select({"<script>",
            "SELECT * FROM evidence_material",
            "<where>",
            "<if test=\"keyword != null and keyword != \'\'\"> AND (material_name LIKE CONCAT('%',#{keyword},'%') OR material_type LIKE CONCAT('%',#{keyword},'%') OR review_comment LIKE CONCAT('%',#{keyword},'%')) </if>",
            "<if test=\"caseId != null\"> AND case_id = #{caseId} </if>",
            "<if test=\"verifyStatus != null\"> AND verify_status = #{verifyStatus} </if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"})
    List<EvidenceMaterial> selectPage(@Param("keyword") String keyword, @Param("caseId") Long caseId, @Param("verifyStatus") Integer verifyStatus);

    @Select({"<script>",
            "SELECT em.* FROM evidence_material em",
            "INNER JOIN legal_case lc ON em.case_id = lc.id",
            "<where>",
            "lc.client_id = #{clientId}",
            "<if test=\"keyword != null and keyword != \'\'\"> AND (em.material_name LIKE CONCAT('%',#{keyword},'%') OR em.material_type LIKE CONCAT('%',#{keyword},'%') OR em.review_comment LIKE CONCAT('%',#{keyword},'%')) </if>",
            "<if test=\"caseId != null\"> AND em.case_id = #{caseId} </if>",
            "<if test=\"verifyStatus != null\"> AND em.verify_status = #{verifyStatus} </if>",
            "</where>",
            "ORDER BY em.id DESC",
            "</script>"})
    List<EvidenceMaterial> selectPageByClientId(@Param("keyword") String keyword, @Param("caseId") Long caseId, @Param("verifyStatus") Integer verifyStatus, @Param("clientId") Long clientId);

    @Select("SELECT * FROM evidence_material WHERE id=#{id}")
    EvidenceMaterial selectById(Long id);

    @Insert("INSERT INTO evidence_material(case_id, material_name, material_type, file_url, submitter_id, verify_status, review_comment, create_time, update_time) VALUES(#{caseId}, #{materialName}, #{materialType}, #{fileUrl}, #{submitterId}, #{verifyStatus}, #{reviewComment}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(EvidenceMaterial entity);

    @Update("UPDATE evidence_material SET case_id=#{caseId}, material_name=#{materialName}, material_type=#{materialType}, file_url=#{fileUrl}, submitter_id=#{submitterId}, verify_status=#{verifyStatus}, review_comment=#{reviewComment}, create_time=#{createTime}, update_time=#{updateTime} WHERE id=#{id}")
    int updateById(EvidenceMaterial entity);

    @Delete("DELETE FROM evidence_material WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM evidence_material")
    long countAll();
}
