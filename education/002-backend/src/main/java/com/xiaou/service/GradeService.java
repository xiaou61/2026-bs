package com.xiaou.service;

import com.xiaou.common.BusinessException;
import com.xiaou.entity.Course;
import com.xiaou.entity.Grade;
import com.xiaou.entity.User;
import com.xiaou.mapper.CourseMapper;
import com.xiaou.mapper.GradeMapper;
import com.xiaou.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GradeService {

    @Autowired
    private GradeMapper gradeMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private UserMapper userMapper;

    public List<Grade> findByStudentId(Long studentId) {
        return gradeMapper.findByStudentId(studentId);
    }

    public List<Grade> findByCourseId(Long courseId) {
        return gradeMapper.findByCourseId(courseId);
    }

    public List<Grade> findByTeacherId(Long teacherId) {
        return gradeMapper.findByTeacherId(teacherId);
    }

    private BigDecimal calculateGPA(BigDecimal score) {
        if (score == null) {
            return null;
        }

        if (score.compareTo(new BigDecimal("90")) >= 0) {
            return new BigDecimal("4.0");
        } else if (score.compareTo(new BigDecimal("85")) >= 0) {
            return new BigDecimal("3.7");
        } else if (score.compareTo(new BigDecimal("82")) >= 0) {
            return new BigDecimal("3.3");
        } else if (score.compareTo(new BigDecimal("78")) >= 0) {
            return new BigDecimal("3.0");
        } else if (score.compareTo(new BigDecimal("75")) >= 0) {
            return new BigDecimal("2.7");
        } else if (score.compareTo(new BigDecimal("72")) >= 0) {
            return new BigDecimal("2.3");
        } else if (score.compareTo(new BigDecimal("68")) >= 0) {
            return new BigDecimal("2.0");
        } else if (score.compareTo(new BigDecimal("64")) >= 0) {
            return new BigDecimal("1.5");
        } else if (score.compareTo(new BigDecimal("60")) >= 0) {
            return new BigDecimal("1.0");
        } else {
            return new BigDecimal("0.0");
        }
    }

    public Grade submitGrade(Long studentId, Long courseId, BigDecimal score, Long teacherId) {
        User student = userMapper.findById(studentId);
        if (student == null) {
            throw new BusinessException("学生不存在");
        }

        Course course = courseMapper.findById(courseId);
        if (course == null) {
            throw new BusinessException("课程不存在");
        }

        if (score.compareTo(BigDecimal.ZERO) < 0 || score.compareTo(new BigDecimal("100")) > 0) {
            throw new BusinessException("成绩必须在0-100之间");
        }

        BigDecimal gpa = calculateGPA(score);

        Grade existGrade = gradeMapper.findByStudentAndCourse(studentId, courseId);
        if (existGrade != null) {
            existGrade.setScore(score);
            existGrade.setGpa(gpa);
            existGrade.setStatus("submitted");
            existGrade.setSubmitTime(LocalDateTime.now());
            gradeMapper.update(existGrade);
            return gradeMapper.findById(existGrade.getId());
        } else {
            Grade grade = new Grade();
            grade.setStudentId(studentId);
            grade.setStudentName(student.getName());
            grade.setStudentNo(student.getStudentNo());
            grade.setCourseId(courseId);
            grade.setCourseName(course.getCourseName());
            grade.setCourseNo(course.getCourseNo());
            grade.setTeacherId(teacherId);
            grade.setTeacherName(course.getTeacherName());
            grade.setCredit(course.getCredit());
            grade.setScore(score);
            grade.setGpa(gpa);
            grade.setSemester(course.getSemester());
            grade.setStatus("submitted");
            grade.setSubmitTime(LocalDateTime.now());
            gradeMapper.insert(grade);
            return grade;
        }
    }
}

