package com.teacher.new.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teacher.new.common.BusinessException;
import com.teacher.new.common.PageResult;
import com.teacher.new.entity.EvaluationTask;
import com.teacher.new.entity.TeacherProfile;
import com.teacher.new.entity.TeachingClass;
import com.teacher.new.entity.User;
import com.teacher.new.mapper.EvaluationTaskMapper;
import com.teacher.new.mapper.TeachingClassMapper;
import com.teacher.new.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskService {

    public static final String STATUS_DRAFT = "DRAFT";
    public static final String STATUS_OPEN = "OPEN";
    public static final String STATUS_CLOSED = "CLOSED";

    @Resource
    private EvaluationTaskMapper evaluationTaskMapper;

    @Resource
    private TeachingClassMapper teachingClassMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TeacherService teacherService;

    @Resource
    private TeachingClassService teachingClassService;

    public PageResult<Map<String, Object>> page(Integer pageNum, Integer pageSize, String taskName, String termName, String status, Long classId, Long teacherId) {
        Page<EvaluationTask> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<EvaluationTask> wrapper = buildWrapper(taskName, termName, status, classId, teacherId);
        Page<EvaluationTask> resultPage = evaluationTaskMapper.selectPage(page, wrapper);
        return buildPageResult(resultPage);
    }

    public PageResult<Map<String, Object>> myPage(Long userId, String role, Integer pageNum, Integer pageSize, String taskName, String status) {
        if ("STUDENT".equals(role)) {
            User user = userMapper.selectById(userId);
            if (user == null || user.getClassId() == null) {
                return emptyPage();
            }
            return page(pageNum, pageSize, taskName, null, status, user.getClassId(), null);
        }
        if ("TEACHER".equals(role)) {
            TeacherProfile teacher = teacherService.getByUserId(userId);
            if (teacher == null) {
                return emptyPage();
            }
            return page(pageNum, pageSize, taskName, null, status, null, teacher.getId());
        }
        return page(pageNum, pageSize, taskName, null, status, null, null);
    }

    public void save(EvaluationTask task, Long creatorId) {
        if (task == null || !StringUtils.hasText(task.getTaskName()) || !StringUtils.hasText(task.getTermName()) || task.getClassId() == null || task.getTeacherId() == null || task.getStartTime() == null || task.getEndTime() == null) {
            throw new BusinessException("考评任务参数不完整");
        }
        if (!task.getEndTime().isAfter(task.getStartTime())) {
            throw new BusinessException("结束时间必须晚于开始时间");
        }
        TeachingClass teachingClass = teachingClassMapper.selectById(task.getClassId());
        if (teachingClass == null || teachingClass.getStatus() == null || teachingClass.getStatus() == 0) {
            throw new BusinessException("班级不存在或已停用");
        }
        TeacherProfile teacher = teacherService.mapByIds(java.util.Collections.singleton(task.getTeacherId())).get(task.getTeacherId());
        if (teacher == null || teacher.getStatus() == null || teacher.getStatus() == 0) {
            throw new BusinessException("教师档案不存在或已停用");
        }
        if (task.getId() == null) {
            add(task, creatorId);
        } else {
            update(task);
        }
    }

    private void add(EvaluationTask task, Long creatorId) {
        task.setTaskName(task.getTaskName().trim());
        task.setTermName(task.getTermName().trim());
        task.setStatus(normalizeStatus(task.getStatus(), STATUS_DRAFT));
        task.setCreatorId(creatorId);
        evaluationTaskMapper.insert(task);
    }

    private void update(EvaluationTask task) {
        EvaluationTask db = evaluationTaskMapper.selectById(task.getId());
        if (db == null) {
            throw new BusinessException("考评任务不存在");
        }
        db.setTaskName(task.getTaskName().trim());
        db.setTermName(task.getTermName().trim());
        db.setClassId(task.getClassId());
        db.setTeacherId(task.getTeacherId());
        db.setStartTime(task.getStartTime());
        db.setEndTime(task.getEndTime());
        if (StringUtils.hasText(task.getStatus())) {
            db.setStatus(normalizeStatus(task.getStatus(), db.getStatus()));
        }
        evaluationTaskMapper.updateById(db);
    }

    public void updateStatus(Long id, String status) {
        EvaluationTask db = evaluationTaskMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("考评任务不存在");
        }
        db.setStatus(normalizeStatus(status, db.getStatus()));
        evaluationTaskMapper.updateById(db);
    }

    public void deleteById(Long id) {
        evaluationTaskMapper.deleteById(id);
    }

    public EvaluationTask getById(Long id) {
        return evaluationTaskMapper.selectById(id);
    }

    public Long countAll() {
        return evaluationTaskMapper.selectCount(null);
    }

    public Long countOpen() {
        return evaluationTaskMapper.selectCount(new LambdaQueryWrapper<EvaluationTask>().eq(EvaluationTask::getStatus, STATUS_OPEN));
    }

    public Long countByTeacherId(Long teacherId) {
        if (teacherId == null) {
            return 0L;
        }
        return evaluationTaskMapper.selectCount(new LambdaQueryWrapper<EvaluationTask>().eq(EvaluationTask::getTeacherId, teacherId));
    }

    public Long countByClassId(Long classId) {
        if (classId == null) {
            return 0L;
        }
        return evaluationTaskMapper.selectCount(new LambdaQueryWrapper<EvaluationTask>().eq(EvaluationTask::getClassId, classId));
    }

    public Long countByClassIdAndStatus(Long classId, String status) {
        if (classId == null) {
            return 0L;
        }
        return evaluationTaskMapper.selectCount(new LambdaQueryWrapper<EvaluationTask>()
                .eq(EvaluationTask::getClassId, classId)
                .eq(EvaluationTask::getStatus, status));
    }

    public List<Map<String, Object>> statusDistribution(String role, Long userId) {
        QueryWrapper<EvaluationTask> wrapper = new QueryWrapper<>();
        if ("TEACHER".equals(role)) {
            TeacherProfile teacher = teacherService.getByUserId(userId);
            if (teacher == null) {
                return new ArrayList<>();
            }
            wrapper.eq("teacher_id", teacher.getId());
        }
        if ("STUDENT".equals(role)) {
            User user = userMapper.selectById(userId);
            if (user == null || user.getClassId() == null) {
                return new ArrayList<>();
            }
            wrapper.eq("class_id", user.getClassId());
        }
        wrapper.select("status", "count(*) as count").groupBy("status");
        return evaluationTaskMapper.selectMaps(wrapper);
    }

    public Map<Long, EvaluationTask> mapByIds(Collection<Long> ids) {
        Map<Long, EvaluationTask> map = new HashMap<>();
        if (ids == null || ids.isEmpty()) {
            return map;
        }
        Set<Long> idSet = ids.stream().filter(java.util.Objects::nonNull).collect(Collectors.toSet());
        if (idSet.isEmpty()) {
            return map;
        }
        List<EvaluationTask> list = evaluationTaskMapper.selectBatchIds(idSet);
        for (EvaluationTask item : list) {
            map.put(item.getId(), item);
        }
        return map;
    }

    private LambdaQueryWrapper<EvaluationTask> buildWrapper(String taskName, String termName, String status, Long classId, Long teacherId) {
        return new LambdaQueryWrapper<EvaluationTask>()
                .like(StringUtils.hasText(taskName), EvaluationTask::getTaskName, taskName == null ? null : taskName.trim())
                .like(StringUtils.hasText(termName), EvaluationTask::getTermName, termName == null ? null : termName.trim())
                .eq(StringUtils.hasText(status), EvaluationTask::getStatus, status == null ? null : status.trim())
                .eq(classId != null, EvaluationTask::getClassId, classId)
                .eq(teacherId != null, EvaluationTask::getTeacherId, teacherId)
                .orderByDesc(EvaluationTask::getId);
    }

    private PageResult<Map<String, Object>> buildPageResult(Page<EvaluationTask> resultPage) {
        List<Map<String, Object>> records = buildRecords(resultPage.getRecords());
        PageResult<Map<String, Object>> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(records);
        return result;
    }

    private List<Map<String, Object>> buildRecords(List<EvaluationTask> tasks) {
        List<Map<String, Object>> records = new ArrayList<>();
        if (tasks.isEmpty()) {
            return records;
        }
        Set<Long> classIds = tasks.stream().map(EvaluationTask::getClassId).collect(Collectors.toSet());
        Set<Long> teacherIds = tasks.stream().map(EvaluationTask::getTeacherId).collect(Collectors.toSet());
        Map<Long, TeachingClass> classMap = teachingClassMapper.selectBatchIds(classIds).stream().collect(Collectors.toMap(TeachingClass::getId, e -> e));
        Map<Long, TeacherProfile> teacherMap = teacherService.mapByIds(teacherIds);
        Set<Long> userIds = teacherMap.values().stream().map(TeacherProfile::getUserId).collect(Collectors.toSet());
        Map<Long, User> userMap = userIds.isEmpty() ? new HashMap<>() : userMapper.selectBatchIds(userIds).stream().collect(Collectors.toMap(User::getId, e -> e));

        for (EvaluationTask task : tasks) {
            TeachingClass teachingClass = classMap.get(task.getClassId());
            TeacherProfile teacher = teacherMap.get(task.getTeacherId());
            User teacherUser = teacher == null ? null : userMap.get(teacher.getUserId());
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("id", task.getId());
            item.put("taskName", task.getTaskName());
            item.put("termName", task.getTermName());
            item.put("classId", task.getClassId());
            item.put("className", teachingClassService.classFullName(teachingClass));
            item.put("teacherId", task.getTeacherId());
            item.put("teacherNo", teacher == null ? "" : teacher.getTeacherNo());
            item.put("teacherName", teacherUser == null ? "" : (StringUtils.hasText(teacherUser.getNickname()) ? teacherUser.getNickname() : teacherUser.getUsername()));
            item.put("startTime", task.getStartTime());
            item.put("endTime", task.getEndTime());
            item.put("status", task.getStatus());
            item.put("creatorId", task.getCreatorId());
            item.put("createTime", task.getCreateTime());
            records.add(item);
        }
        return records;
    }

    private String normalizeStatus(String status, String fallback) {
        String value = StringUtils.hasText(status) ? status.trim() : fallback;
        if (!STATUS_DRAFT.equals(value) && !STATUS_OPEN.equals(value) && !STATUS_CLOSED.equals(value)) {
            throw new BusinessException("任务状态不合法");
        }
        return value;
    }

    private PageResult<Map<String, Object>> emptyPage() {
        PageResult<Map<String, Object>> result = new PageResult<>();
        result.setTotal(0L);
        result.setRecords(new ArrayList<>());
        return result;
    }
}
