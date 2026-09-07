package com.knowledgeqa.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.knowledgeqa.common.BusinessException;
import com.knowledgeqa.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrudService {

    @Autowired
    private CommonMapper commonMapper;

    private final Map<String, TableMeta> metas = new HashMap<>();

    public CrudService() {
        metas.put("user", new TableMeta("sys_user", "id, username, nickname, role, department, phone, email, status, create_time createTime, update_time updateTime", "id desc", List.of("username", "nickname", "department")));
        metas.put("space", new TableMeta("knowledge_space", "id, name, owner_name ownerName, visibility, description, status, create_time createTime, update_time updateTime", "id desc", List.of("name", "owner_name", "description")));
        metas.put("category", new TableMeta("document_category", "id, space_id spaceId, name, code, description, sort, status, create_time createTime, update_time updateTime", "sort desc, id desc", List.of("name", "code", "description")));
        metas.put("document", new TableMeta("knowledge_document", "id, space_id spaceId, category_id categoryId, title, summary, source_type sourceType, tags, content, status, creator_id creatorId, create_time createTime, update_time updateTime", "id desc", List.of("title", "summary", "tags")));
        metas.put("chunk", new TableMeta("document_chunk", "id, document_id documentId, chunk_title chunkTitle, chunk_content chunkContent, keywords, vector_status vectorStatus, sort, create_time createTime, update_time updateTime", "sort asc, id desc", List.of("chunk_title", "keywords", "chunk_content")));
        metas.put("group", new TableMeta("permission_group", "id, name, owner_name ownerName, description, status, create_time createTime, update_time updateTime", "id desc", List.of("name", "owner_name", "description")));
        metas.put("member", new TableMeta("group_member", "id, group_id groupId, user_id userId, member_role memberRole, status, create_time createTime", "id desc", List.of("member_role")));
        metas.put("permission", new TableMeta("document_permission", "id, space_id spaceId, document_id documentId, group_id groupId, permission_level permissionLevel, status, create_time createTime, update_time updateTime", "id desc", List.of("permission_level")));
        metas.put("session", new TableMeta("qa_session", "id, session_no sessionNo, title, space_id spaceId, user_id userId, status, create_time createTime, close_time closeTime", "id desc", List.of("session_no", "title")));
        metas.put("record", new TableMeta("qa_record", "id, session_id sessionId, question, answer, source_chunk_ids sourceChunkIds, confidence, resolved, status, create_time createTime, update_time updateTime", "id desc", List.of("question", "answer")));
        metas.put("feedback", new TableMeta("qa_feedback", "id, record_id recordId, user_id userId, content, score, status, reply_content replyContent, create_time createTime, update_time updateTime", "id desc", List.of("content", "reply_content")));
        metas.put("log", new TableMeta("access_log", "id, user_id userId, username, role, module_name moduleName, action_type actionType, description, create_time createTime", "id desc", List.of("username", "description", "module_name")));
    }

    public PageInfo<Map<String, Object>> page(String key, Integer pageNum, Integer pageSize, String keyword, Map<String, Object> filters) {
        TableMeta meta = meta(key);
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = commonMapper.selectPage(meta.getTable(), meta.getColumns(), keyword, meta.getKeywordColumns(), clean(filters), meta.getOrderBy());
        return new PageInfo<>(list);
    }

    public Map<String, Object> get(String key, Long id) {
        TableMeta meta = meta(key);
        return commonMapper.selectById(meta.getTable(), meta.getColumns(), id);
    }

    public void save(String key, Map<String, Object> data) {
        TableMeta meta = meta(key);
        if (data.get("id") == null) {
            data.putIfAbsent("status", 1);
            data.putIfAbsent("createTime", LocalDateTime.now());
            if (hasUpdateTime(meta)) {
                data.putIfAbsent("updateTime", LocalDateTime.now());
            }
            commonMapper.insert(meta.getTable(), data);
        } else {
            if (hasUpdateTime(meta)) {
                data.put("updateTime", LocalDateTime.now());
            }
            commonMapper.update(meta.getTable(), data);
        }
    }

    public void delete(String key, Long id) {
        commonMapper.delete(meta(key).getTable(), id);
    }

    public void updateField(String key, Long id, String field, Object value) {
        commonMapper.updateField(meta(key).getTable(), id, field, value);
    }

    public Long count(String key) {
        return commonMapper.countAll(meta(key).getTable());
    }

    public Long countWhere(String key, String field, Object value) {
        return commonMapper.countWhere(meta(key).getTable(), field, value);
    }

    private TableMeta meta(String key) {
        TableMeta meta = metas.get(key);
        if (meta == null) {
            throw new BusinessException(400, "模块不存在");
        }
        return meta;
    }

    private boolean hasUpdateTime(TableMeta meta) {
        return !"qa_session".equals(meta.getTable()) && !"group_member".equals(meta.getTable()) && !"access_log".equals(meta.getTable());
    }

    private Map<String, Object> clean(Map<String, Object> filters) {
        Map<String, Object> result = new HashMap<>();
        if (filters == null) {
            return result;
        }
        filters.forEach((key, value) -> {
            if (value != null && !"".equals(String.valueOf(value))) {
                result.put(key, value);
            }
        });
        return result;
    }
}
