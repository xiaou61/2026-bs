package com.legalcase.mapper;

import com.legalcase.entity.DocumentTemplate;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DocumentTemplateMapper {
    @Select({"<script>",
            "SELECT * FROM document_template",
            "<where>",
            "<if test=\"keyword != null and keyword != \'\'\"> AND (template_name LIKE CONCAT('%',#{keyword},'%') OR template_type LIKE CONCAT('%',#{keyword},'%') OR content_variables LIKE CONCAT('%',#{keyword},'%')) </if>",
            "<if test=\"templateType != null and templateType != \'\'\"> AND template_type = #{templateType} </if>",
            "<if test=\"status != null\"> AND status = #{status} </if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"})
    List<DocumentTemplate> selectPage(@Param("keyword") String keyword, @Param("templateType") String templateType, @Param("status") Integer status);

    @Select("SELECT * FROM document_template WHERE id=#{id}")
    DocumentTemplate selectById(Long id);

    @Insert("INSERT INTO document_template(template_name, template_type, content_variables, status, usage_count, create_time, update_time) VALUES(#{templateName}, #{templateType}, #{contentVariables}, #{status}, #{usageCount}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(DocumentTemplate entity);

    @Update("UPDATE document_template SET template_name=#{templateName}, template_type=#{templateType}, content_variables=#{contentVariables}, status=#{status}, usage_count=#{usageCount}, create_time=#{createTime}, update_time=#{updateTime} WHERE id=#{id}")
    int updateById(DocumentTemplate entity);

    @Delete("DELETE FROM document_template WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM document_template")
    long countAll();
}
