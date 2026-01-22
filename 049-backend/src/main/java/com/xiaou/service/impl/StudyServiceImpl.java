package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.dto.CheckinDTO;
import com.xiaou.entity.*;
import com.xiaou.mapper.*;
import com.xiaou.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyService {

    private final StudyRecordMapper studyRecordMapper;
    private final StudyPlanMapper studyPlanMapper;
    private final DailyCheckinMapper dailyCheckinMapper;
    private final UserMapper userMapper;
    private final CourseMapper courseMapper;
    private final ChapterMapper chapterMapper;

    @Override
    public void updateStudyProgress(Long userId, Long courseId, Long chapterId, Integer position, Integer duration) {
        StudyRecord record = studyRecordMapper.selectOne(new LambdaQueryWrapper<StudyRecord>()
                .eq(StudyRecord::getUserId, userId).eq(StudyRecord::getCourseId, courseId));
        if (record == null) {
            record = new StudyRecord();
            record.setUserId(userId);
            record.setCourseId(courseId);
            record.setChapterId(chapterId);
            record.setLastPosition(position);
            record.setStudyDuration(duration);
            record.setProgress(0);
            studyRecordMapper.insert(record);
        } else {
            record.setChapterId(chapterId);
            record.setLastPosition(position);
            record.setStudyDuration(record.getStudyDuration() + duration);
            studyRecordMapper.updateById(record);
        }
    }

    @Override
    public Page<StudyRecord> getStudyRecords(Long userId, int current, int size) {
        Page<StudyRecord> page = studyRecordMapper.selectPage(new Page<>(current, size),
                new LambdaQueryWrapper<StudyRecord>().eq(StudyRecord::getUserId, userId).orderByDesc(StudyRecord::getUpdateTime));
        page.getRecords().forEach(r -> {
            Course c = courseMapper.selectById(r.getCourseId());
            if (c != null) r.setCourseTitle(c.getTitle());
            if (r.getChapterId() != null) {
                Chapter ch = chapterMapper.selectById(r.getChapterId());
                if (ch != null) r.setChapterTitle(ch.getTitle());
            }
        });
        return page;
    }

    @Override
    public List<StudyPlan> getMyPlans(Long userId) {
        return studyPlanMapper.selectList(new LambdaQueryWrapper<StudyPlan>()
                .eq(StudyPlan::getUserId, userId).orderByDesc(StudyPlan::getCreateTime));
    }

    @Override
    public void createPlan(StudyPlan plan) {
        studyPlanMapper.insert(plan);
    }

    @Override
    public void updatePlanProgress(Long planId, Integer progress) {
        StudyPlan plan = studyPlanMapper.selectById(planId);
        if (plan != null) {
            plan.setProgress(progress);
            if (progress >= 100) plan.setStatus(1);
            studyPlanMapper.updateById(plan);
        }
    }

    @Override
    public void checkin(Long userId, CheckinDTO dto) {
        LocalDate today = LocalDate.now();
        DailyCheckin existing = dailyCheckinMapper.selectOne(new LambdaQueryWrapper<DailyCheckin>()
                .eq(DailyCheckin::getUserId, userId).eq(DailyCheckin::getCheckinDate, today));
        if (existing != null) {
            throw new RuntimeException("今日已打卡");
        }
        DailyCheckin checkin = new DailyCheckin();
        checkin.setUserId(userId);
        checkin.setCheckinDate(today);
        checkin.setStudyDuration(dto.getStudyDuration());
        checkin.setQuestionCount(dto.getQuestionCount());
        checkin.setCorrectCount(dto.getCorrectCount());
        checkin.setNote(dto.getNote());
        checkin.setMood(dto.getMood());
        dailyCheckinMapper.insert(checkin);
        
        // 更新用户学习天数和积分
        User user = userMapper.selectById(userId);
        user.setStudyDays(user.getStudyDays() + 1);
        user.setPoints(user.getPoints() + 5);
        userMapper.updateById(user);
    }

    @Override
    public List<DailyCheckin> getCheckinRecords(Long userId, Integer days) {
        return dailyCheckinMapper.selectList(new LambdaQueryWrapper<DailyCheckin>()
                .eq(DailyCheckin::getUserId, userId)
                .ge(DailyCheckin::getCheckinDate, LocalDate.now().minusDays(days))
                .orderByDesc(DailyCheckin::getCheckinDate));
    }

    @Override
    public Map<String, Object> getCheckinStats(Long userId) {
        Map<String, Object> stats = new HashMap<>();
        // 总打卡天数
        Long totalDays = dailyCheckinMapper.selectCount(new LambdaQueryWrapper<DailyCheckin>().eq(DailyCheckin::getUserId, userId));
        stats.put("totalDays", totalDays);
        
        // 连续打卡天数
        int continuousDays = 0;
        LocalDate date = LocalDate.now();
        while (dailyCheckinMapper.selectCount(new LambdaQueryWrapper<DailyCheckin>()
                .eq(DailyCheckin::getUserId, userId).eq(DailyCheckin::getCheckinDate, date)) > 0) {
            continuousDays++;
            date = date.minusDays(1);
        }
        stats.put("continuousDays", continuousDays);
        
        // 本周打卡
        LocalDate weekStart = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() - 1);
        Long weekDays = dailyCheckinMapper.selectCount(new LambdaQueryWrapper<DailyCheckin>()
                .eq(DailyCheckin::getUserId, userId).ge(DailyCheckin::getCheckinDate, weekStart));
        stats.put("weekDays", weekDays);
        
        return stats;
    }
}
