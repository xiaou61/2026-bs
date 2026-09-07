package com.xiaou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.dto.CheckinDTO;
import com.xiaou.entity.*;
import java.util.List;
import java.util.Map;

public interface StudyService {
    void updateStudyProgress(Long userId, Long courseId, Long chapterId, Integer position, Integer duration);
    Page<StudyRecord> getStudyRecords(Long userId, int current, int size);
    List<StudyPlan> getMyPlans(Long userId);
    void createPlan(StudyPlan plan);
    void updatePlanProgress(Long planId, Integer progress);
    void checkin(Long userId, CheckinDTO dto);
    List<DailyCheckin> getCheckinRecords(Long userId, Integer days);
    Map<String, Object> getCheckinStats(Long userId);
}
