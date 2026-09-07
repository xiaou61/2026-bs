package com.xiaou.service;

import com.xiaou.common.BusinessException;
import com.xiaou.entity.Course;
import com.xiaou.entity.CourseSelection;
import com.xiaou.entity.SystemConfig;
import com.xiaou.entity.User;
import com.xiaou.mapper.CourseMapper;
import com.xiaou.mapper.CourseSelectionMapper;
import com.xiaou.mapper.SystemConfigMapper;
import com.xiaou.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CourseSelectionService {

    @Autowired
    private CourseSelectionMapper courseSelectionMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    private void checkSelectionTime() {
        SystemConfig enabledConfig = systemConfigMapper.findByKey("selection_enabled");
        if (enabledConfig == null || !"true".equals(enabledConfig.getConfigValue())) {
            throw new BusinessException("当前不在选课时间内");
        }

        SystemConfig startConfig = systemConfigMapper.findByKey("selection_start_time");
        SystemConfig endConfig = systemConfigMapper.findByKey("selection_end_time");

        if (startConfig != null && endConfig != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime start = LocalDateTime.parse(startConfig.getConfigValue(), formatter);
            LocalDateTime end = LocalDateTime.parse(endConfig.getConfigValue(), formatter);

            if (now.isBefore(start) || now.isAfter(end)) {
                throw new BusinessException("当前不在选课时间内");
            }
        }
    }

    public List<CourseSelection> findByStudentId(Long studentId) {
        return courseSelectionMapper.findByStudentId(studentId);
    }

    public List<CourseSelection> findByCourseId(Long courseId) {
        return courseSelectionMapper.findByCourseId(courseId);
    }

    @Transactional
    public CourseSelection selectCourse(Long studentId, Long courseId) {
        checkSelectionTime();

        CourseSelection existSelection = courseSelectionMapper.findByStudentAndCourse(studentId, courseId);
        if (existSelection != null) {
            throw new BusinessException("已选择该课程，请勿重复选课");
        }

        Course course = courseMapper.findById(courseId);
        if (course == null) {
            throw new BusinessException("课程不存在");
        }

        if (course.getSelectedCount() >= course.getTotalCapacity()) {
            throw new BusinessException("课程已满，无法选课");
        }

        User student = userMapper.findById(studentId);
        if (student == null) {
            throw new BusinessException("学生不存在");
        }

        CourseSelection selection = new CourseSelection();
        selection.setStudentId(studentId);
        selection.setStudentName(student.getName());
        selection.setStudentNo(student.getStudentNo());
        selection.setCourseId(courseId);
        selection.setCourseName(course.getCourseName());
        selection.setCourseNo(course.getCourseNo());
        selection.setTeacherName(course.getTeacherName());
        selection.setCredit(course.getCredit());
        selection.setStatus("selected");
        selection.setSelectionTime(LocalDateTime.now());

        courseSelectionMapper.insert(selection);
        courseMapper.updateSelectedCount(courseId, 1);

        return selection;
    }

    @Transactional
    public void dropCourse(Long studentId, Long courseId) {
        checkSelectionTime();

        CourseSelection selection = courseSelectionMapper.findByStudentAndCourse(studentId, courseId);
        if (selection == null) {
            throw new BusinessException("未选择该课程");
        }

        courseSelectionMapper.deleteByStudentAndCourse(studentId, courseId);
        courseMapper.updateSelectedCount(courseId, -1);
    }
}

