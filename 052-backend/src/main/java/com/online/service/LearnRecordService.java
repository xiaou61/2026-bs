package com.online.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.entity.Course;
import com.online.entity.LearnRecord;
import com.online.entity.Video;
import com.online.mapper.CourseMapper;
import com.online.mapper.LearnRecordMapper;
import com.online.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class LearnRecordService extends ServiceImpl<LearnRecordMapper, LearnRecord> {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private VideoMapper videoMapper;

    public void startLearn(Long userId, Long courseId) {
        LearnRecord record = this.getOne(new LambdaQueryWrapper<LearnRecord>()
                .eq(LearnRecord::getUserId, userId)
                .eq(LearnRecord::getCourseId, courseId));
        if (record == null) {
            record = new LearnRecord();
            record.setUserId(userId);
            record.setCourseId(courseId);
            record.setProgress(0);
            record.setDuration(0);
            record.setFinishedCount(0);
            record.setLastLearnTime(LocalDateTime.now());
            this.save(record);
            Course course = courseMapper.selectById(courseId);
            course.setLearnCount(course.getLearnCount() + 1);
            courseMapper.updateById(course);
        }
    }

    public Map<String, Object> getVideoInfo(Long userId, Long videoId) {
        Video video = videoMapper.selectById(videoId);
        if (video == null) {
            throw new RuntimeException("视频不存在");
        }
        LearnRecord record = this.getOne(new LambdaQueryWrapper<LearnRecord>()
                .eq(LearnRecord::getUserId, userId)
                .eq(LearnRecord::getCourseId, video.getCourseId()));
        Map<String, Object> result = new HashMap<>();
        result.put("video", video);
        result.put("progress", record != null && record.getVideoId() != null && record.getVideoId().equals(videoId) ? record.getProgress() : 0);
        return result;
    }

    public void saveProgress(Long userId, Long videoId, Integer progress) {
        Video video = videoMapper.selectById(videoId);
        if (video == null) return;
        LearnRecord record = this.getOne(new LambdaQueryWrapper<LearnRecord>()
                .eq(LearnRecord::getUserId, userId)
                .eq(LearnRecord::getCourseId, video.getCourseId()));
        if (record != null) {
            record.setVideoId(videoId);
            record.setProgress(progress);
            record.setLastLearnTime(LocalDateTime.now());
            this.updateById(record);
        }
    }

    public Page<LearnRecord> getMyCourses(Long userId, Integer pageNum, Integer pageSize) {
        Page<LearnRecord> page = new Page<>(pageNum, pageSize);
        Page<LearnRecord> result = this.page(page, new LambdaQueryWrapper<LearnRecord>()
                .eq(LearnRecord::getUserId, userId)
                .orderByDesc(LearnRecord::getLastLearnTime));
        result.getRecords().forEach(record -> {
            Course course = courseMapper.selectById(record.getCourseId());
            record.setCourse(course);
        });
        return result;
    }

    public Page<LearnRecord> getRecords(Long userId, Integer pageNum, Integer pageSize) {
        return getMyCourses(userId, pageNum, pageSize);
    }
}
