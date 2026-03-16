package com.course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.course.common.BusinessException;
import com.course.entity.CourseSelection;
import com.course.entity.ScoreRecord;
import com.course.mapper.ScoreRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ScoreService {

    @Autowired
    private ScoreRecordMapper scoreMapper;

    @Autowired
    private CourseSelectionMapperAdapter selectionAdapter;

    @Autowired
    private AuthService authService;

    public PageInfo<ScoreRecord> list(Long scheduleId, Long studentId, Long userId, String role, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ScoreRecord> list = scoreMapper.selectList(scheduleId, "student".equals(role) ? userId : studentId, "teacher".equals(role) ? userId : null);
        return new PageInfo<>(list);
    }

    public void save(ScoreRecord entity, Long userId, String role) {
        authService.assertTeacher(role);
        if (entity == null || entity.getSelectionId() == null) {
            throw new BusinessException("选课记录不能为空");
        }
        CourseSelection selection = selectionAdapter.getById(entity.getSelectionId());
        if (selection == null) {
            throw new BusinessException("选课记录不存在");
        }
        BigDecimal usual = entity.getUsualScore() == null ? BigDecimal.ZERO : entity.getUsualScore();
        BigDecimal exam = entity.getExamScore() == null ? BigDecimal.ZERO : entity.getExamScore();
        BigDecimal total = usual.multiply(new BigDecimal("0.4")).add(exam.multiply(new BigDecimal("0.6"))).setScale(2, RoundingMode.HALF_UP);
        entity.setTeacherId(userId);
        entity.setScheduleId(selection.getScheduleId());
        entity.setCourseId(selection.getCourseId());
        entity.setStudentId(selection.getStudentId());
        entity.setTotalScore(total);
        entity.setGradeLevel(resolveLevel(total));
        ScoreRecord exists = scoreMapper.selectBySelectionId(entity.getSelectionId());
        if (exists == null) {
            scoreMapper.insert(entity);
        } else {
            scoreMapper.update(entity);
        }
    }

    private String resolveLevel(BigDecimal total) {
        if (total.compareTo(new BigDecimal("90")) >= 0) return "A";
        if (total.compareTo(new BigDecimal("80")) >= 0) return "B";
        if (total.compareTo(new BigDecimal("70")) >= 0) return "C";
        if (total.compareTo(new BigDecimal("60")) >= 0) return "D";
        return "E";
    }
}
