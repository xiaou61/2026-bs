package com.opera.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.opera.common.BusinessException;
import com.opera.entity.PerformanceSchedule;
import com.opera.mapper.PerformanceScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceService {

    @Autowired
    private PerformanceScheduleMapper scheduleMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<PerformanceSchedule> list(String courseName, Long artistId, Long termId, Long classId, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PerformanceSchedule> list = scheduleMapper.selectList(courseName, artistId, termId, classId, status);
        return new PageInfo<>(list);
    }

    public List<PerformanceSchedule> artistList(Long artistId) {
        return scheduleMapper.selectTeacherList(artistId);
    }

    public List<PerformanceSchedule> memberList(Long memberId) {
        return scheduleMapper.selectStudentList(memberId);
    }

    public PerformanceSchedule getById(Long id) {
        return scheduleMapper.selectById(id);
    }

    public void add(PerformanceSchedule entity, String role) {
        authService.assertAdmin(role);
        validate(entity);
        if (entity.getSelectedCount() == null) {
            entity.setSelectedCount(0);
        }
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        scheduleMapper.insert(entity);
    }

    public void update(PerformanceSchedule entity, String role) {
        authService.assertAdmin(role);
        if (entity.getId() == null) {
            throw new BusinessException("排期ID不能为空");
        }
        validate(entity);
        scheduleMapper.update(entity);
    }

    public void delete(Long id, String role) {
        authService.assertAdmin(role);
        scheduleMapper.deleteById(id);
    }

    public void increaseSelectedCount(Long id) {
        scheduleMapper.increaseSelectedCount(id);
    }

    private void validate(PerformanceSchedule entity) {
        if (entity == null || entity.getCourseId() == null || entity.getTermId() == null || entity.getArtistId() == null) {
            throw new BusinessException("剧目、文化专题、艺术家不能为空");
        }
    }
}


