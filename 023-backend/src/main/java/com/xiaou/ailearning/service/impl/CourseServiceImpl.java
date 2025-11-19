package com.xiaou.ailearning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.ailearning.common.PageResult;
import com.xiaou.ailearning.entity.Course;
import com.xiaou.ailearning.entity.LearningRecord;
import com.xiaou.ailearning.mapper.CourseMapper;
import com.xiaou.ailearning.mapper.LearningRecordMapper;
import com.xiaou.ailearning.service.CourseService;
import com.xiaou.ailearning.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseMapper courseMapper;
    
    @Autowired
    private LearningRecordMapper learningRecordMapper;
    
    @Autowired
    private RecommendationService recommendationService;

    @Override
    public PageResult<Course> getCourseList(int page, int size, String keyword) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getStatus, 1);
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            wrapper.and(w -> w.like(Course::getCourseName, keyword)
                           .or()
                           .like(Course::getDescription, keyword));
        }
        
        wrapper.orderByDesc(Course::getCreateTime);
        
        IPage<Course> pageInfo = new Page<>(page, size);
        IPage<Course> result = courseMapper.selectPage(pageInfo, wrapper);
        
        return new PageResult<>(result.getRecords(), result.getTotal(), page, size);
    }

    @Override
    public Course getCourseDetail(Long courseId) {
        return courseMapper.selectById(courseId);
    }

    @Override
    public List<Course> getRecommendedCourses(Long userId, int limit) {
        return recommendationService.recommendCourses(userId, limit);
    }

    @Override
    public List<Course> getCoursesByCategory(Long categoryId) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getCategoryId, categoryId)
               .eq(Course::getStatus, 1)
               .orderByDesc(Course::getCreateTime);
        
        return courseMapper.selectList(wrapper);
    }

    @Override
    public List<Course> getHotCourses(int limit) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getStatus, 1)
               .orderByDesc(Course::getCreateTime)
               .last("LIMIT " + limit);
        
        return courseMapper.selectList(wrapper);
    }

    @Override
    public boolean enrollCourse(Long userId, Long courseId) {
        try {
            if (isEnrolled(userId, courseId)) {
                return true;
            }
            
            LearningRecord enrollRecord = new LearningRecord();
            enrollRecord.setUserId(userId);
            enrollRecord.setCourseId(courseId);
            enrollRecord.setLearningType(1); // 观看类型
            enrollRecord.setStartTime(LocalDateTime.now());
            enrollRecord.setCompletionRate(0.0);
            enrollRecord.setMasteryLevel(0.0);
            
            learningRecordMapper.insert(enrollRecord);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isEnrolled(Long userId, Long courseId) {
        LambdaQueryWrapper<LearningRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LearningRecord::getUserId, userId)
               .eq(LearningRecord::getCourseId, courseId);
        
        return learningRecordMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<Course> getUserEnrolledCourses(Long userId) {
        List<LearningRecord> records = learningRecordMapper.selectByUserId(userId);
        
        Set<Long> enrolledCourseIds = records.stream()
                .map(LearningRecord::getCourseId)
                .filter(id -> id != null)
                .collect(Collectors.toSet());
        
        if (enrolledCourseIds.isEmpty()) {
            return List.of();
        }
        
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Course::getId, enrolledCourseIds)
               .eq(Course::getStatus, 1)
               .orderByDesc(Course::getCreateTime);
        
        return courseMapper.selectList(wrapper);
    }
}