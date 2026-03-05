package com.alumni.service;

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

    public void update(JobPost job) {
        jobPostMapper.updateById(job);
    }

    public void delete(Long id) {
        jobPostMapper.deleteById(id);
    }

    public void audit(Long id, Integer status) {
        JobPost job = new JobPost();
        job.setId(id);
        job.setStatus(status);
        jobPostMapper.updateById(job);
    }
}
