package com.legalcase.mapper;

import com.legalcase.entity.DocumentVersion;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DocumentVersionMapper {
    @Select({"<script>",
            "SELECT * FROM document_version",
            "<where>",
            "<if test=\"keyword != null and keyword != \'\'\"> AND (version_no LIKE CONCAT('%',#{keyword},'%') OR content LIKE CONCAT('%',#{keyword},'%') OR change_summary LIKE CONCAT('%',#{keyword},'%')) </if>",
            "<if test=\"documentId != null\"> AND document_id = #{documentId} </if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"})
    List<DocumentVersion> selectPage(@Param("keyword") String keyword, @Param("documentId") Long documentId);

    @Select("SELECT * FROM document_version WHERE id=#{id}")
    DocumentVersion selectById(Long id);

    @Insert("INSERT INTO document_version(document_id, version_no, content, change_summary, operator_id, create_time) VALUES(#{documentId}, #{versionNo}, #{content}, #{changeSummary}, #{operatorId}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(DocumentVersion entity);

    @Update("UPDATE document_version SET document_id=#{documentId}, version_no=#{versionNo}, content=#{content}, change_summary=#{changeSummary}, operator_id=#{operatorId}, create_time=#{createTime} WHERE id=#{id}")
    int updateById(DocumentVersion entity);

    @Delete("DELETE FROM document_version WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM document_version")
    long countAll();
}
