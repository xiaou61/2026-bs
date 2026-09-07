package com.textintegrity.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.textintegrity.common.BusinessException;
import com.textintegrity.mapper.CommonMapper;
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
        metas.put("course", new TableMeta("course_info", "id, course_name courseName, course_code courseCode, teacher_name teacherName, semester, description, status, create_time createTime, update_time updateTime", "id desc", List.of("course_name", "course_code", "teacher_name")));
        metas.put("class", new TableMeta("class_info", "id, class_name className, major, grade, teacher_name teacherName, student_count studentCount, status, create_time createTime, update_time updateTime", "id desc", List.of("class_name", "major", "teacher_name")));
        metas.put("student", new TableMeta("student_profile", "id, student_no studentNo, name, class_id classId, major, grade, phone, email, status, create_time createTime, update_time updateTime", "id desc", List.of("student_no", "name", "major")));
        metas.put("assignment", new TableMeta("assignment_task", "id, course_id courseId, title, assignment_type assignmentType, teacher_id teacherId, deadline, threshold_score thresholdScore, description, status, create_time createTime, update_time updateTime", "id desc", List.of("title", "assignment_type", "description")));
        metas.put("submission", new TableMeta("text_submission", "id, assignment_id assignmentId, student_id studentId, title, content, citation_note citationNote, attachment_url attachmentUrl, word_count wordCount, status, submit_time submitTime, update_time updateTime", "id desc", List.of("title", "content", "citation_note")));
        metas.put("rule", new TableMeta("detection_rule", "id, rule_name ruleName, rule_type ruleType, risk_level riskLevel, weight, keywords, description, status, create_time createTime, update_time updateTime", "id desc", List.of("rule_name", "keywords", "description")));
        metas.put("task", new TableMeta("detection_task", "id, task_no taskNo, submission_id submissionId, task_name taskName, priority, status, reviewer_id reviewerId, create_time createTime, finish_time finishTime", "id desc", List.of("task_no", "task_name")));
        metas.put("result", new TableMeta("detection_result", "id, task_id taskId, submission_id submissionId, matched_rules matchedRules, ai_probability aiProbability, repeat_rate repeatRate, citation_risk citationRisk, risk_level riskLevel, score, conclusion, suggestion, review_status reviewStatus, review_comment reviewComment, create_time createTime, update_time updateTime", "id desc", List.of("matched_rules", "conclusion", "suggestion")));
        metas.put("warning", new TableMeta("integrity_warning", "id, result_id resultId, student_id studentId, warning_level warningLevel, warning_title warningTitle, warning_content warningContent, status, handler_id handlerId, handle_comment handleComment, create_time createTime, update_time updateTime", "id desc", List.of("warning_title", "warning_content", "handle_comment")));
        metas.put("rectification", new TableMeta("rectification_record", "id, warning_id warningId, student_id studentId, revision_note revisionNote, revision_url revisionUrl, status, review_comment reviewComment, create_time createTime, update_time updateTime", "id desc", List.of("revision_note", "review_comment")));
        metas.put("appeal", new TableMeta("appeal_record", "id, target_type targetType, target_id targetId, student_id studentId, reason, status, handle_comment handleComment, create_time createTime, update_time updateTime", "id desc", List.of("target_type", "reason", "handle_comment")));
        metas.put("log", new TableMeta("operation_log", "id, user_id userId, username, role, module_name moduleName, action_type actionType, description, create_time createTime", "id desc", List.of("username", "description", "module_name")));
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
            if ("submission".equals(key)) {
                data.putIfAbsent("submitTime", LocalDateTime.now());
            } else {
                data.putIfAbsent("createTime", LocalDateTime.now());
            }
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
        return !"detection_task".equals(meta.getTable()) && !"operation_log".equals(meta.getTable());
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
