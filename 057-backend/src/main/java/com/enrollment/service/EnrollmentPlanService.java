package com.enrollment.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enrollment.entity.EnrollmentPlan;
import com.enrollment.mapper.EnrollmentPlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentPlanService {

    @Autowired
    private EnrollmentPlanMapper planMapper;

    public IPage<EnrollmentPlan> getPage(Integer pageNum, Integer pageSize, Integer year, Long majorId) {
        Page<EnrollmentPlan> page = new Page<>(pageNum, pageSize);
        return planMapper.selectPageWithMajor(page, year, majorId);
    }

    public List<EnrollmentPlan> getByYear(Integer year) {
        return planMapper.selectList(new QueryWrapper<EnrollmentPlan>().eq("year", year).eq("status", 1));
    }

    public void add(EnrollmentPlan plan) {
        planMapper.insert(plan);
    }

    public void update(EnrollmentPlan plan) {
        planMapper.updateById(plan);
    }

    public void delete(Long id) {
        planMapper.deleteById(id);
    }

    public void publish(Long id) {
        EnrollmentPlan plan = new EnrollmentPlan();
        plan.setId(id);
        plan.setStatus(1);
        planMapper.updateById(plan);
    }

    public void close(Long id) {
        EnrollmentPlan plan = new EnrollmentPlan();
        plan.setId(id);
        plan.setStatus(2);
        planMapper.updateById(plan);
    }
}
