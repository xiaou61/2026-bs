package com.xiaou.recruitment.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.recruitment.entity.Job;
import com.xiaou.recruitment.entity.Resume;
import com.xiaou.recruitment.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService extends ServiceImpl<JobMapper, Job> {

    @Autowired
    private ResumeService resumeService;

    public boolean publishJob(Job job) {
        job.setStatus(1);
        job.setViews(0);
        return save(job);
    }

    public boolean updateJob(Job job) {
        return updateById(job);
    }

    public Job getJobById(Long id) {
        Job job = getById(id);
        if (job != null) {
            job.setViews(job.getViews() + 1);
            updateById(job);
        }
        return job;
    }

    public IPage<Job> getJobList(Integer page, Integer size, String jobType, String category, String location,
            String keyword) {
        Page<Job> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Job> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Job::getStatus, 1);
        if (jobType != null && !jobType.isEmpty()) {
            wrapper.eq(Job::getJobType, jobType);
        }
        if (category != null && !category.isEmpty()) {
            wrapper.eq(Job::getCategory, category);
        }
        if (location != null && !location.isEmpty()) {
            wrapper.like(Job::getLocation, location);
        }
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like(Job::getTitle, keyword)
                    .or().like(Job::getDescription, keyword));
        }
        wrapper.orderByDesc(Job::getCreatedAt);
        return page(pageParam, wrapper);
    }

    public List<Job> getRecommendJobs(Long userId) {
        Resume resume = resumeService.getResumeByUserId(userId);
        if (resume == null) {
            LambdaQueryWrapper<Job> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Job::getStatus, 1);
            wrapper.orderByDesc(Job::getViews);
            wrapper.last("LIMIT 10");
            return list(wrapper);
        }

        LambdaQueryWrapper<Job> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Job::getStatus, 1);
        if (resume.getMajor() != null && !resume.getMajor().isEmpty()) {
            wrapper.like(Job::getMajor, resume.getMajor());
        }
        if (resume.getEducation() != null && !resume.getEducation().isEmpty()) {
            wrapper.eq(Job::getEducation, resume.getEducation());
        }
        wrapper.orderByDesc(Job::getCreatedAt);
        wrapper.last("LIMIT 10");
        return list(wrapper);
    }

    public IPage<Job> getJobsByCompanyId(Integer page, Integer size, Long companyId) {
        Page<Job> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Job> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Job::getCompanyId, companyId);
        wrapper.orderByDesc(Job::getCreatedAt);
        return page(pageParam, wrapper);
    }
}
