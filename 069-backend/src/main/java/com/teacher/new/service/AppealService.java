package com.teacher.new.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teacher.new.common.BusinessException;
import com.teacher.new.common.PageResult;
import com.teacher.new.entity.EvaluationAppeal;
import com.teacher.new.entity.EvaluationRecord;
import com.teacher.new.entity.EvaluationTask;
import com.teacher.new.entity.TeacherProfile;
import com.teacher.new.entity.User;
import com.teacher.new.mapper.EvaluationAppealMapper;
import com.teacher.new.mapper.EvaluationRecordMapper;
import com.teacher.new.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AppealService {

    @Resource
    private EvaluationAppealMapper evaluationAppealMapper;

    @Resource
    private EvaluationRecordMapper evaluationRecordMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TeacherService teacherService;

    @Resource
    private TaskService taskService;

    public EvaluationAppeal create(Long userId, String role, EvaluationAppeal appeal) {
        if (!"TEACHER".equals(role)) {
            throw new BusinessException("仅教师可发起申诉");
        }
        if (appeal == null || appeal.getRecordId() == null || !StringUtils.hasText(appeal.getReasonText())) {
            throw new BusinessException("申诉参数不完整");
        }
        TeacherProfile teacher = teacherService.getByUserId(userId);
        if (teacher == null) {
            throw new BusinessException("教师档案不存在");
        }
        EvaluationRecord record = evaluationRecordMapper.selectById(appeal.getRecordId());
        if (record == null) {
            throw new BusinessException("评教记录不存在");
        }
        if (!teacher.getId().equals(record.getTeacherId())) {
            throw new BusinessException("只能对本人评教记录发起申诉");
        }
        EvaluationAppeal waiting = evaluationAppealMapper.selectOne(new LambdaQueryWrapper<EvaluationAppeal>()
                .eq(EvaluationAppeal::getRecordId, appeal.getRecordId())
                .eq(EvaluationAppeal::getStatus, "WAITING")
                .last("limit 1"));
        if (waiting != null) {
            throw new BusinessException("该记录已有待处理申诉");
        }

        appeal.setTaskId(record.getTaskId());
        appeal.setTeacherId(teacher.getId());
        appeal.setReasonText(appeal.getReasonText().trim());
        appeal.setReplyText("");
        appeal.setStatus("WAITING");
        evaluationAppealMapper.insert(appeal);
        return appeal;
    }

    public PageResult<Map<String, Object>> page(Integer pageNum, Integer pageSize, String status, Long teacherId, Long userId, String role) {
        if ("TEACHER".equals(role)) {
            TeacherProfile teacher = teacherService.getByUserId(userId);
            if (teacher == null) {
                return emptyPage();
            }
            teacherId = teacher.getId();
        }
        Page<EvaluationAppeal> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<EvaluationAppeal> wrapper = new LambdaQueryWrapper<EvaluationAppeal>()
                .eq(StringUtils.hasText(status), EvaluationAppeal::getStatus, status == null ? null : status.trim())
                .eq(teacherId != null, EvaluationAppeal::getTeacherId, teacherId)
                .orderByDesc(EvaluationAppeal::getId);
        Page<EvaluationAppeal> resultPage = evaluationAppealMapper.selectPage(page, wrapper);

        PageResult<Map<String, Object>> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(buildRecords(resultPage.getRecords()));
        return result;
    }

    public void handle(Long id, String status, String replyText, Long handlerId) {
        if (!"PASSED".equals(status) && !"REJECTED".equals(status)) {
            throw new BusinessException("处理状态不合法");
        }
        EvaluationAppeal db = evaluationAppealMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("申诉不存在");
        }
        if (!"WAITING".equals(db.getStatus())) {
            throw new BusinessException("该申诉已处理");
        }
        db.setStatus(status);
        db.setReplyText(StringUtils.hasText(replyText) ? replyText.trim() : "");
        db.setHandleBy(handlerId);
        db.setHandleTime(LocalDateTime.now());
        evaluationAppealMapper.updateById(db);
    }

    public Long countPending() {
        return evaluationAppealMapper.selectCount(new LambdaQueryWrapper<EvaluationAppeal>().eq(EvaluationAppeal::getStatus, "WAITING"));
    }

    public Long countByTeacherId(Long teacherId) {
        if (teacherId == null) {
            return 0L;
        }
        return evaluationAppealMapper.selectCount(new LambdaQueryWrapper<EvaluationAppeal>().eq(EvaluationAppeal::getTeacherId, teacherId));
    }

    public Long countByTeacherIdAndStatus(Long teacherId, String status) {
        if (teacherId == null) {
            return 0L;
        }
        return evaluationAppealMapper.selectCount(new LambdaQueryWrapper<EvaluationAppeal>()
                .eq(EvaluationAppeal::getTeacherId, teacherId)
                .eq(EvaluationAppeal::getStatus, status));
    }

    private List<Map<String, Object>> buildRecords(List<EvaluationAppeal> appeals) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (appeals.isEmpty()) {
            return result;
        }
        Set<Long> recordIds = appeals.stream().map(EvaluationAppeal::getRecordId).collect(Collectors.toSet());
        Map<Long, EvaluationRecord> recordMap = evaluationRecordMapper.selectBatchIds(recordIds).stream().collect(Collectors.toMap(EvaluationRecord::getId, e -> e));
        Set<Long> taskIds = appeals.stream().map(EvaluationAppeal::getTaskId).collect(Collectors.toSet());
        Map<Long, EvaluationTask> taskMap = taskService.mapByIds(taskIds);

        Set<Long> teacherIds = appeals.stream().map(EvaluationAppeal::getTeacherId).collect(Collectors.toSet());
        Map<Long, TeacherProfile> teacherMap = teacherService.mapByIds(teacherIds);
        Set<Long> teacherUserIds = teacherMap.values().stream().map(TeacherProfile::getUserId).collect(Collectors.toSet());

        Set<Long> evaluatorIds = recordMap.values().stream().map(EvaluationRecord::getEvaluatorId).collect(Collectors.toSet());
        teacherUserIds.addAll(evaluatorIds);
        teacherUserIds.addAll(appeals.stream().map(EvaluationAppeal::getHandleBy).filter(java.util.Objects::nonNull).collect(Collectors.toSet()));
        Map<Long, User> userMap = teacherUserIds.isEmpty() ? new java.util.HashMap<>() : userMapper.selectBatchIds(teacherUserIds).stream().collect(Collectors.toMap(User::getId, e -> e));

        for (EvaluationAppeal appeal : appeals) {
            EvaluationRecord record = recordMap.get(appeal.getRecordId());
            EvaluationTask task = taskMap.get(appeal.getTaskId());
            TeacherProfile teacher = teacherMap.get(appeal.getTeacherId());
            User teacherUser = teacher == null ? null : userMap.get(teacher.getUserId());
            User evaluator = record == null ? null : userMap.get(record.getEvaluatorId());
            User handler = appeal.getHandleBy() == null ? null : userMap.get(appeal.getHandleBy());

            Map<String, Object> item = new LinkedHashMap<>();
            item.put("id", appeal.getId());
            item.put("recordId", appeal.getRecordId());
            item.put("taskId", appeal.getTaskId());
            item.put("taskName", task == null ? "" : task.getTaskName());
            item.put("teacherId", appeal.getTeacherId());
            item.put("teacherName", teacherUser == null ? "" : (StringUtils.hasText(teacherUser.getNickname()) ? teacherUser.getNickname() : teacherUser.getUsername()));
            item.put("evaluatorName", evaluator == null ? "" : (StringUtils.hasText(evaluator.getNickname()) ? evaluator.getNickname() : evaluator.getUsername()));
            item.put("totalScore", record == null ? null : record.getTotalScore());
            item.put("reasonText", appeal.getReasonText());
            item.put("replyText", appeal.getReplyText());
            item.put("status", appeal.getStatus());
            item.put("handleBy", appeal.getHandleBy());
            item.put("handleName", handler == null ? "" : (StringUtils.hasText(handler.getNickname()) ? handler.getNickname() : handler.getUsername()));
            item.put("handleTime", appeal.getHandleTime());
            item.put("createTime", appeal.getCreateTime());
            result.add(item);
        }
        return result;
    }

    private PageResult<Map<String, Object>> emptyPage() {
        PageResult<Map<String, Object>> result = new PageResult<>();
        result.setTotal(0L);
        result.setRecords(new ArrayList<>());
        return result;
    }
}
