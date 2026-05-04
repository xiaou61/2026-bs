package com.legalcase.mapper;

import com.legalcase.entity.LegalDocument;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface LegalDocumentMapper {
    @Select({"<script>",
            "SELECT * FROM legal_document",
            "<where>",
            "<if test=\"keyword != null and keyword != \'\'\"> AND (document_no LIKE CONCAT('%',#{keyword},'%') OR document_title LIKE CONCAT('%',#{keyword},'%') OR document_type LIKE CONCAT('%',#{keyword},'%') OR content LIKE CONCAT('%',#{keyword},'%')) </if>",
            "<if test=\"caseId != null\"> AND case_id = #{caseId} </if>",
            "<if test=\"templateId != null\"> AND template_id = #{templateId} </if>",
            "<if test=\"documentType != null and documentType != \'\'\"> AND document_type = #{documentType} </if>",
            "<if test=\"status != null\"> AND status = #{status} </if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"})
    List<LegalDocument> selectPage(@Param("keyword") String keyword, @Param("caseId") Long caseId, @Param("templateId") Long templateId, @Param("documentType") String documentType, @Param("status") Integer status);

    @Select("SELECT * FROM legal_document WHERE id=#{id}")
    LegalDocument selectById(Long id);

    @Insert("INSERT INTO legal_document(case_id, template_id, document_no, document_title, document_type, content, status, generated_by, review_comment, create_time, update_time) VALUES(#{caseId}, #{templateId}, #{documentNo}, #{documentTitle}, #{documentType}, #{content}, #{status}, #{generatedBy}, #{reviewComment}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(LegalDocument entity);

    @Update("UPDATE legal_document SET case_id=#{caseId}, template_id=#{templateId}, document_no=#{documentNo}, document_title=#{documentTitle}, document_type=#{documentType}, content=#{content}, status=#{status}, generated_by=#{generatedBy}, review_comment=#{reviewComment}, create_time=#{createTime}, update_time=#{updateTime} WHERE id=#{id}")
    int updateById(LegalDocument entity);

    @Delete("DELETE FROM legal_document WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM legal_document")
    long countAll();
}
