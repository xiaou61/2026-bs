package com.alumni.service;

import com.alumni.common.BusinessException;
import com.alumni.entity.AlumniCompany;
import com.alumni.entity.JobPost;
import com.alumni.mapper.AlumniCompanyMapper;
import com.alumni.mapper.JobPostMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobPostMapper jobPostMapper;

    @Autowired
    private AlumniCompanyMapper alumniCompanyMapper;

    public Page<JobPost> list(Integer pageNum, Integer pageSize, String title, String city, Integer status) {
        Page<JobPost> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<JobPost> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(title)) {
            wrapper.like(JobPost::getTitle, title);
        }
        if (StringUtils.hasText(city)) {
            wrapper.eq(JobPost::getCity, city);
        }
        if (status != null) {
            wrapper.eq(JobPost::getStatus, status);
        }
        wrapper.orderByDesc(JobPost::getCreateTime);
        Page<JobPost> result = jobPostMapper.selectPage(page, wrapper);
        List<AlumniCompany> companies = alumniCompanyMapper.selectList(null);
        Map<Long, AlumniCompany> companyMap = companies.stream().collect(Collectors.toMap(AlumniCompany::getId, c -> c));
        result.getRecords().forEach(j -> {
            AlumniCompany company = companyMap.get(j.getCompanyId());
            if (company != null) {
                j.setCompanyName(company.getName());
                j.setCompanyLogo(company.getLogo());
            }
        });
        return result;
    }

    public JobPost getById(Long id) {
        JobPost job = jobPostMapper.selectById(id);
        if (job != null) {
            AlumniCompany company = alumniCompanyMapper.selectById(job.getCompanyId());
            if (company != null) {
                job.setCompanyName(company.getName());
                job.setCompanyLogo(company.getLogo());
            }
        }
        return job;
    }

    public void add(JobPost job) {
        job.setStatus(0);
        jobPostMapper.insert(job);
    }

    public void add(JobPost job, Long userId, boolean admin) {
        AlumniCompany company = requireCompany(job.getCompanyId());
        requireCompanyOwnerOrAdmin(company, userId, admin);
        job.setStatus(0);
        jobPostMapper.insert(job);
    }

    public void update(JobPost job) {
        jobPostMapper.updateById(job);
    }

    public void update(JobPost job, Long userId, boolean admin) {
        JobPost old = requireJob(job.getId());
        AlumniCompany company = requireCompany(old.getCompanyId());
        requireCompanyOwnerOrAdmin(company, userId, admin);
        job.setCompanyId(old.getCompanyId());
        job.setStatus(admin ? job.getStatus() : old.getStatus());
        jobPostMapper.updateById(job);
    }

    public void delete(Long id) {
        jobPostMapper.deleteById(id);
    }

    public void delete(Long id, Long userId, boolean admin) {
        JobPost job = requireJob(id);
        AlumniCompany company = requireCompany(job.getCompanyId());
        requireCompanyOwnerOrAdmin(company, userId, admin);
        jobPostMapper.deleteById(id);
    }

    public void audit(Long id, Integer status) {
        JobPost job = new JobPost();
        job.setId(id);
        job.setStatus(status);
        jobPostMapper.updateById(job);
    }

    private JobPost requireJob(Long id) {
        JobPost job = jobPostMapper.selectById(id);
        if (job == null) {
            throw new BusinessException(404, "岗位不存在");
        }
        return job;
    }

    private AlumniCompany requireCompany(Long id) {
        AlumniCompany company = alumniCompanyMapper.selectById(id);
        if (company == null) {
            throw new BusinessException(404, "企业不存在");
        }
        return company;
    }

    private void requireCompanyOwnerOrAdmin(AlumniCompany company, Long userId, boolean admin) {
        if (!admin && (userId == null || !userId.equals(company.getUserId()))) {
            throw new BusinessException(403, "无权操作");
        }
    }
}
