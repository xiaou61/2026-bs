package com.gongkao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongkao.entity.StudyPlan;
import com.gongkao.mapper.StudyPlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class StudyPlanService {

    @Autowired
    private StudyPlanMapper studyPlanMapper;

    public Page<StudyPlan> getList(int pageNum, int pageSize, Long userId, Long subjectId, Integer status, String title) {
        Page<StudyPlan> page = new Page<>(pageNum, pageSize);
        QueryWrapper<StudyPlan> wrapper = new QueryWrapper<>();
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        if (subjectId != null) {
            wrapper.eq("subject_id", subjectId);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (StringUtils.hasText(title)) {
            wrapper.like("title", title);
        }
        wrapper.orderByDesc("create_time");
        return studyPlanMapper.selectPage(page, wrapper);
    }

    public void add(StudyPlan studyPlan) {
        if (studyPlan.getStatus() == null) {
            studyPlan.setStatus(1);
        }
        if (studyPlan.getCompletedDays() == null) {
            studyPlan.setCompletedDays(0);
        }
        studyPlanMapper.insert(studyPlan);
    }

    public void update(StudyPlan studyPlan) {
        studyPlanMapper.updateById(studyPlan);
    }

    public void delete(Long id) {
        studyPlanMapper.deleteById(id);
    }
}
