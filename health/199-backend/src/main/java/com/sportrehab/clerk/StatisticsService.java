package com.sportrehab.clerk;

import com.sportrehab.mapper.RehabCenterMapper;
import com.sportrehab.mapper.MemberProfileMapper;
import com.sportrehab.mapper.CoachProfileMapper;
import com.sportrehab.mapper.AssessmentItemMapper;
import com.sportrehab.mapper.FitnessAssessmentMapper;
import com.sportrehab.mapper.RiskWarningMapper;
import com.sportrehab.mapper.TrainingPlanMapper;
import com.sportrehab.mapper.TrainingSessionMapper;
import com.sportrehab.mapper.ExerciseCheckinMapper;
import com.sportrehab.mapper.RehabFeedbackMapper;
import com.sportrehab.mapper.ReassessmentRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final RehabCenterMapper rehabCenterMapper;
    private final MemberProfileMapper memberProfileMapper;
    private final CoachProfileMapper coachProfileMapper;
    private final AssessmentItemMapper assessmentItemMapper;
    private final FitnessAssessmentMapper fitnessAssessmentMapper;
    private final RiskWarningMapper riskWarningMapper;
    private final TrainingPlanMapper trainingPlanMapper;
    private final TrainingSessionMapper trainingSessionMapper;
    private final ExerciseCheckinMapper exerciseCheckinMapper;
    private final RehabFeedbackMapper rehabFeedbackMapper;
    private final ReassessmentRecordMapper reassessmentRecordMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        long centerCount = rehabCenterMapper.countAll();
        long memberCount = memberProfileMapper.countAll();
        long coachCount = coachProfileMapper.countAll();
        long itemCount = assessmentItemMapper.countAll();
        long assessmentCount = fitnessAssessmentMapper.countAll();
        long warningCount = riskWarningMapper.countAll();
        long planCount = trainingPlanMapper.countAll();
        long sessionCount = trainingSessionMapper.countAll();
        long checkinCount = exerciseCheckinMapper.countAll();
        long feedbackCount = rehabFeedbackMapper.countAll();
        long reassessmentCount = reassessmentRecordMapper.countAll();

        data.put("centerCount", centerCount);
        data.put("memberCount", memberCount);
        data.put("coachCount", coachCount);
        data.put("itemCount", itemCount);
        data.put("assessmentCount", assessmentCount);
        data.put("warningCount", warningCount);
        data.put("planCount", planCount);
        data.put("sessionCount", sessionCount);
        data.put("checkinCount", checkinCount);
        data.put("feedbackCount", feedbackCount);
        data.put("reassessmentCount", reassessmentCount);
        data.put("totalCount", centerCount + memberCount + coachCount + itemCount + assessmentCount + warningCount + planCount + sessionCount + checkinCount + feedbackCount + reassessmentCount);

        data.put("trend", Arrays.asList(
            mapTrend("周一", 31, 18),
            mapTrend("周二", 46, 25),
            mapTrend("周三", 58, 32),
            mapTrend("周四", 72, 41),
            mapTrend("周五", 86, 53),
            mapTrend("周六", 101, 67),
            mapTrend("周日", 119, 78)
        ));

        data.put("pie", Arrays.asList(
            map("已建档", 36),
            map("评估中", 24),
            map("训练中", 32),
            map("反馈中", 28),
            map("复评中", 12),
            map("已闭环", 18)
        ));

        data.put("categoryPie", Arrays.asList(
            map("康复中心", (int) centerCount),
            map("会员档案", (int) memberCount),
            map("教练档案", (int) coachCount),
            map("体测项目", (int) itemCount),
            map("体测评估", (int) assessmentCount),
            map("风险提醒", (int) warningCount),
            map("训练计划", (int) planCount),
            map("训练安排", (int) sessionCount),
            map("训练打卡", (int) checkinCount),
            map("康复反馈", (int) feedbackCount),
            map("复评记录", (int) reassessmentCount)
        ));

        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }

    private Map<String, Object> mapTrend(String day, int assessments, int feedbacks) {
        Map<String, Object> item = new HashMap<>();
        item.put("day", day);
        item.put("assessments", assessments);
        item.put("feedbacks", feedbacks);
        return item;
    }
}
