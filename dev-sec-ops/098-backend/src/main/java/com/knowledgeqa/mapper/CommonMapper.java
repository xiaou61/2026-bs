package com.knowledgeqa.mapper;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface CommonMapper {

    @SelectProvider(type = CommonSqlProvider.class, method = "selectPage")
    List<Map<String, Object>> selectPage(@Param("table") String table,
                                         @Param("columns") String columns,
                                         @Param("keyword") String keyword,
                                         @Param("keywordColumns") List<String> keywordColumns,
                                         @Param("filters") Map<String, Object> filters,
                                         @Param("orderBy") String orderBy);

    @SelectProvider(type = CommonSqlProvider.class, method = "selectById")
    Map<String, Object> selectById(@Param("table") String table, @Param("columns") String columns, @Param("id") Long id);

    @InsertProvider(type = CommonSqlProvider.class, method = "insert")
    void insert(@Param("table") String table, @Param("data") Map<String, Object> data);

    @UpdateProvider(type = CommonSqlProvider.class, method = "update")
    void update(@Param("table") String table, @Param("data") Map<String, Object> data);

    @DeleteProvider(type = CommonSqlProvider.class, method = "delete")
    void delete(@Param("table") String table, @Param("id") Long id);

    @UpdateProvider(type = CommonSqlProvider.class, method = "updateField")
    void updateField(@Param("table") String table, @Param("id") Long id, @Param("field") String field, @Param("value") Object value);

    @SelectProvider(type = CommonSqlProvider.class, method = "countAll")
    Long countAll(@Param("table") String table);

    @SelectProvider(type = CommonSqlProvider.class, method = "countWhere")
    Long countWhere(@Param("table") String table, @Param("field") String field, @Param("value") Object value);

    @Select("select id, username, password, nickname, role, department, phone, email, status, create_time createTime, update_time updateTime from sys_user where username = #{username}")
    Map<String, Object> findUserByUsername(String username);

    @Select("select id, username, password, nickname, role, department, phone, email, status, create_time createTime, update_time updateTime from sys_user where id = #{id}")
    Map<String, Object> findUserById(Long id);

    @Select("select id, title, space_id spaceId, user_id userId, status from qa_session where id = #{id}")
    Map<String, Object> findSessionById(Long id);

    @Select("select c.id, c.document_id documentId, c.chunk_title chunkTitle, c.chunk_content chunkContent, c.keywords, c.vector_status vectorStatus, c.sort from document_chunk c join knowledge_document d on c.document_id = d.id where d.space_id = #{spaceId} and c.vector_status = 1 order by c.sort asc limit 3")
    List<Map<String, Object>> findIndexedChunksBySpace(Long spaceId);

    @Select("select ifnull(avg(confidence), 0) from qa_record")
    BigDecimal averageConfidence();
}
