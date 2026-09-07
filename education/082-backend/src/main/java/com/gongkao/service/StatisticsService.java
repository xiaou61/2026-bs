package com.gongkao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gongkao.entity.Course;
import com.gongkao.entity.ExamPaper;
import com.gongkao.entity.ExamRecord;
import com.gongkao.entity.Question;
import com.gongkao.entity.StudyPlan;
import com.gongkao.entity.Subject;
import com.gongkao.entity.User;
import com.gongkao.mapper.CourseMapper;
import com.gongkao.mapper.ExamPaperMapper;
import com.gongkao.mapper.ExamRecordMapper;
import com.gongkao.mapper.QuestionMapper;
import com.gongkao.mapper.StudyPlanMapper;
import com.gongkao.mapper.SubjectMapper;
import com.gongkao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private ExamPaperMapper examPaperMapper;

    @Autowired
    private ExamRecordMapper examRecordMapper;

    @Autowired
    private StudyPlanMapper studyPlanMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        long userCount = userMapper.selectCount(new QueryWrapper<User>());
        long subjectCount = subjectMapper.selectCount(new QueryWrapper<Subject>());
        long courseCount = courseMapper.selectCount(new QueryWrapper<Course>());
        long questionCount = questionMapper.selectCount(new QueryWrapper<Question>());
        long paperCount = examPaperMapper.selectCount(new QueryWrapper<ExamPaper>());
        long recordCount = examRecordMapper.selectCount(new QueryWrapper<ExamRecord>());
        long passCount = examRecordMapper.selectCount(new QueryWrapper<ExamRecord>().eq("pass_status", 1));
        long planCount = studyPlanMapper.selectCount(new QueryWrapper<StudyPlan>());

        BigDecimal passRate = BigDecimal.ZERO;
        if (recordCount > 0) {
            passRate = BigDecimal.valueOf(passCount)
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(recordCount), 2, RoundingMode.HALF_UP);
        }

        data.put("userCount", userCount);
        data.put("subjectCount", subjectCount);
        data.put("courseCount", courseCount);
        data.put("questionCount", questionCount);
        data.put("paperCount", paperCount);
        data.put("recordCount", recordCount);
        data.put("planCount", planCount);
        data.put("passRate", passRate);
        data.put("subjectDistribution", subjectDistribution());
        return data;
    }

    private List<Map<String, Object>> subjectDistribution() {
        QueryWrapper<Course> courseWrapper = new QueryWrapper<>();
        courseWrapper.select("subject_id AS subjectId", "COUNT(*) AS count").groupBy("subject_id");
        List<Map<String, Object>> rows = courseMapper.selectMaps(courseWrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Object subjectIdObj = row.get("subjectId");
            if (subjectIdObj == null) {
                subjectIdObj = row.get("subjectid");
            }
            if (subjectIdObj == null) {
                subjectIdObj = row.get("subject_id");
            }
            Long subjectId = subjectIdObj == null ? null : Long.valueOf(String.valueOf(subjectIdObj));
            Subject subject = subjectId == null ? null : subjectMapper.selectById(subjectId);
            Map<String, Object> item = new HashMap<>();
            item.put("name", subject == null ? "未分类" : subject.getName());
            item.put("value", row.get("count"));
            result.add(item);
        }
        return result;
    }
}
