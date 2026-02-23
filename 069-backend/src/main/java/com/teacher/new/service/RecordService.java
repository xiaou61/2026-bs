package com.teacher.new.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teacher.new.common.BusinessException;
import com.teacher.new.common.PageResult;
import com.teacher.new.entity.EvaluationRecord;
import com.teacher.new.entity.EvaluationTask;
import com.teacher.new.entity.TeacherProfile;
import com.teacher.new.entity.TeachingClass;
import com.teacher.new.entity.User;
import com.teacher.new.mapper.EvaluationRecordMapper;
import com.teacher.new.mapper.EvaluationTaskMapper;
import com.teacher.new.mapper.TeachingClassMapper;
import com.teacher.new.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecordService {

    @Resource
    private EvaluationRecordMapper evaluationRecordMapper;

    @Resource
    private EvaluationTaskMapper evaluationTaskMapper;

    @Resource
    private TeachingClassMapper teachingClassMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TeacherService teacherService;

    @Resource
    private TaskService taskService;

    @Resource
    private TeachingClassService teachingClassService;

    public EvaluationRecord create(Long userId, String role, EvaluationRecord record) {
        if (!"STUDENT".equals(role)) {
            throw new BusinessException("仅学生可提交考评");
        }
        if (record == null || record.getTaskId() == null) {
            throw new BusinessException("考评参数不完整");
        }
        EvaluationTask task = evaluationTaskMapper.selectById(record.getTaskId());
        if (task == null) {
            throw new BusinessException("考评任务不存在");
        }
        if (!TaskService.STATUS_OPEN.equals(task.getStatus())) {
            throw new BusinessException("当前任务不可提交");
        }
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(task.getStartTime()) || now.isAfter(task.getEndTime())) {
            throw new BusinessException("不在任务考评时间范围内");
        }
        User user = userMapper.selectById(userId);
        if (user == null || user.getClassId() == null || !user.getClassId().equals(task.getClassId())) {
            throw new BusinessException("无权限参与该任务考评");
        }
        Long count = evaluationRecordMapper.selectCount(new LambdaQueryWrapper<EvaluationRecord>()
                .eq(EvaluationRecord::getTaskId, task.getId())
                .eq(EvaluationRecord::getEvaluatorId, userId));
        if (count != null && count > 0) {
            throw new BusinessException("该任务已提交过考评");
        }

        BigDecimal attitude = normalizeScore(record.getAttitudeScore(), "教学态度");
        BigDecimal content = normalizeScore(record.getContentScore(), "教学内容");
        BigDecimal method = normalizeScore(record.getMethodScore(), "教学方法");
        BigDecimal manage = normalizeScore(record.getManageScore(), "课堂管理");
        BigDecimal homework = normalizeScore(record.getHomeworkScore(), "作业反馈");
        BigDecimal total = attitude.add(content).add(method).add(manage).add(homework)
                .divide(new BigDecimal("5"), 2, RoundingMode.HALF_UP);

        record.setEvaluatorId(userId);
        record.setTeacherId(task.getTeacherId());
        record.setClassId(task.getClassId());
        record.setAttitudeScore(attitude);
        record.setContentScore(content);
        record.setMethodScore(method);
        record.setManageScore(manage);
        record.setHomeworkScore(homework);
        record.setTotalScore(total);
        record.setCommentText(StringUtils.hasText(record.getCommentText()) ? record.getCommentText().trim() : "");
        evaluationRecordMapper.insert(record);
        return record;
    }

    public PageResult<Map<String, Object>> page(Integer pageNum, Integer pageSize, Long taskId, Long teacherId, Long classId, Long evaluatorId, Long userId, String role) {
        if ("STUDENT".equals(role)) {
            evaluatorId = userId;
        }
        if ("TEACHER".equals(role)) {
            TeacherProfile teacher = teacherService.getByUserId(userId);
            if (teacher == null) {
                return emptyPage();
            }
            teacherId = teacher.getId();
        }

        Page<EvaluationRecord> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<EvaluationRecord> wrapper = new LambdaQueryWrapper<EvaluationRecord>()
                .eq(taskId != null, EvaluationRecord::getTaskId, taskId)
                .eq(teacherId != null, EvaluationRecord::getTeacherId, teacherId)
                .eq(classId != null, EvaluationRecord::getClassId, classId)
                .eq(evaluatorId != null, EvaluationRecord::getEvaluatorId, evaluatorId)
                .orderByDesc(EvaluationRecord::getId);
        Page<EvaluationRecord> resultPage = evaluationRecordMapper.selectPage(page, wrapper);

        PageResult<Map<String, Object>> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(buildRecords(resultPage.getRecords()));
        return result;
    }

    public EvaluationRecord getById(Long id) {
        return evaluationRecordMapper.selectById(id);
    }

    public void deleteById(Long id) {
        evaluationRecordMapper.deleteById(id);
    }

    public Long countAll() {
        return evaluationRecordMapper.selectCount(null);
    }

    public Long countByEvaluator(Long evaluatorId) {
        return evaluationRecordMapper.selectCount(new LambdaQueryWrapper<EvaluationRecord>().eq(EvaluationRecord::getEvaluatorId, evaluatorId));
    }

    public Long countByTeacher(Long teacherId) {
        if (teacherId == null) {
            return 0L;
        }
        return evaluationRecordMapper.selectCount(new LambdaQueryWrapper<EvaluationRecord>().eq(EvaluationRecord::getTeacherId, teacherId));
    }

    public BigDecimal avgScoreByTeacher(Long teacherId) {
        if (teacherId == null) {
            return BigDecimal.ZERO;
        }
        QueryWrapper<EvaluationRecord> wrapper = new QueryWrapper<>();
        wrapper.select("ifnull(round(avg(total_score),2),0) as avgScore");
        wrapper.eq("teacher_id", teacherId);
        List<Map<String, Object>> maps = evaluationRecordMapper.selectMaps(wrapper);
        if (maps.isEmpty() || maps.get(0).get("avgScore") == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(String.valueOf(maps.get(0).get("avgScore")));
    }

    public long countByDate(LocalDate day, String role, Long userId) {
        LocalDateTime start = day.atStartOfDay();
        LocalDateTime end = day.plusDays(1).atStartOfDay();
        LambdaQueryWrapper<EvaluationRecord> wrapper = new LambdaQueryWrapper<EvaluationRecord>()
                .ge(EvaluationRecord::getCreateTime, start)
                .lt(EvaluationRecord::getCreateTime, end);
        if ("STUDENT".equals(role)) {
            wrapper.eq(EvaluationRecord::getEvaluatorId, userId);
        }
        if ("TEACHER".equals(role)) {
            TeacherProfile teacher = teacherService.getByUserId(userId);
            if (teacher == null) {
                return 0L;
            }
            wrapper.eq(EvaluationRecord::getTeacherId, teacher.getId());
        }
        return evaluationRecordMapper.selectCount(wrapper);
    }

    public BigDecimal avgByDate(LocalDate day, String role, Long userId) {
        LocalDateTime start = day.atStartOfDay();
        LocalDateTime end = day.plusDays(1).atStartOfDay();
        QueryWrapper<EvaluationRecord> wrapper = new QueryWrapper<>();
        wrapper.select("ifnull(round(avg(total_score),2),0) as avgScore");
        wrapper.ge("create_time", start).lt("create_time", end);
        if ("STUDENT".equals(role)) {
            wrapper.eq("evaluator_id", userId);
        }
        if ("TEACHER".equals(role)) {
            TeacherProfile teacher = teacherService.getByUserId(userId);
            if (teacher == null) {
                return BigDecimal.ZERO;
            }
            wrapper.eq("teacher_id", teacher.getId());
        }
        List<Map<String, Object>> maps = evaluationRecordMapper.selectMaps(wrapper);
        if (maps.isEmpty() || maps.get(0).get("avgScore") == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(String.valueOf(maps.get(0).get("avgScore")));
    }

    private List<Map<String, Object>> buildRecords(List<EvaluationRecord> records) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (records.isEmpty()) {
            return result;
        }
        Set<Long> taskIds = records.stream().map(EvaluationRecord::getTaskId).collect(Collectors.toSet());
        Set<Long> teacherIds = records.stream().map(EvaluationRecord::getTeacherId).collect(Collectors.toSet());
        Set<Long> classIds = records.stream().map(EvaluationRecord::getClassId).collect(Collectors.toSet());
        Set<Long> evaluatorIds = records.stream().map(EvaluationRecord::getEvaluatorId).collect(Collectors.toSet());

        Map<Long, EvaluationTask> taskMap = taskService.mapByIds(taskIds);
        Map<Long, TeacherProfile> teacherMap = teacherService.mapByIds(teacherIds);
        Map<Long, TeachingClass> classMap = teachingClassMapper.selectBatchIds(classIds).stream().collect(Collectors.toMap(TeachingClass::getId, e -> e));
        Map<Long, User> evaluatorMap = evaluatorIds.isEmpty() ? new HashMap<>() : userMapper.selectBatchIds(evaluatorIds).stream().collect(Collectors.toMap(User::getId, e -> e));

        Set<Long> teacherUserIds = teacherMap.values().stream().map(TeacherProfile::getUserId).collect(Collectors.toSet());
        Map<Long, User> teacherUserMap = teacherUserIds.isEmpty() ? new HashMap<>() : userMapper.selectBatchIds(teacherUserIds).stream().collect(Collectors.toMap(User::getId, e -> e));

        for (EvaluationRecord record : records) {
            EvaluationTask task = taskMap.get(record.getTaskId());
            TeacherProfile teacher = teacherMap.get(record.getTeacherId());
            TeachingClass teachingClass = classMap.get(record.getClassId());
            User evaluator = evaluatorMap.get(record.getEvaluatorId());
            User teacherUser = teacher == null ? null : teacherUserMap.get(teacher.getUserId());
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("id", record.getId());
            item.put("taskId", record.getTaskId());
            item.put("taskName", task == null ? "" : task.getTaskName());
            item.put("evaluatorId", record.getEvaluatorId());
            item.put("evaluatorName", evaluator == null ? "" : (StringUtils.hasText(evaluator.getNickname()) ? evaluator.getNickname() : evaluator.getUsername()));
            item.put("teacherId", record.getTeacherId());
            item.put("teacherName", teacherUser == null ? "" : (StringUtils.hasText(teacherUser.getNickname()) ? teacherUser.getNickname() : teacherUser.getUsername()));
            item.put("classId", record.getClassId());
            item.put("className", teachingClassService.classFullName(teachingClass));
            item.put("attitudeScore", record.getAttitudeScore());
            item.put("contentScore", record.getContentScore());
            item.put("methodScore", record.getMethodScore());
            item.put("manageScore", record.getManageScore());
            item.put("homeworkScore", record.getHomeworkScore());
            item.put("totalScore", record.getTotalScore());
            item.put("commentText", record.getCommentText());
            item.put("createTime", record.getCreateTime());
            result.add(item);
        }
        return result;
    }

    private BigDecimal normalizeScore(BigDecimal score, String label) {
        if (score == null) {
            throw new BusinessException(label + "不能为空");
        }
        if (score.compareTo(BigDecimal.ZERO) < 0 || score.compareTo(new BigDecimal("100")) > 0) {
            throw new BusinessException(label + "范围必须在0-100");
        }
        return score;
    }

    private PageResult<Map<String, Object>> emptyPage() {
        PageResult<Map<String, Object>> result = new PageResult<>();
        result.setTotal(0L);
        result.setRecords(new ArrayList<>());
        return result;
    }
}
