package com.xiaou.recruitment.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.recruitment.entity.Application;
import com.xiaou.recruitment.entity.Company;
import com.xiaou.recruitment.entity.Job;
import com.xiaou.recruitment.entity.Resume;
import com.xiaou.recruitment.entity.User;
import com.xiaou.recruitment.mapper.ApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ApplicationService extends ServiceImpl<ApplicationMapper, Application> {

    @Autowired
    private JobService jobService;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    public boolean submitApplication(Application application, Long userId) {
        Resume resume = resumeService.getById(application.getResumeId());
        Job job = jobService.getById(application.getJobId());
        if (resume == null || job == null || !userId.equals(resume.getUserId())) {
            return false;
        }
        LambdaQueryWrapper<Application> duplicateWrapper = new LambdaQueryWrapper<>();
        duplicateWrapper.eq(Application::getUserId, userId)
                .eq(Application::getJobId, application.getJobId())
                .eq(Application::getResumeId, application.getResumeId());
        if (count(duplicateWrapper) > 0) {
            return false;
        }
        application.setUserId(userId);
        application.setStatus("pending");
        return save(application);
    }

    public boolean updateApplicationStatus(Long id, String status, String remark, Long companyId) {
        Application application = getById(id);
        if (application == null || !jobService.belongsToCompany(application.getJobId(), companyId)) {
            return false;
        }
        application.setStatus(status);
        if (remark != null) {
            application.setRemark(remark);
        }
        return updateById(application);
    }

    public IPage<Application> getMyApplications(Integer page, Integer size, Long userId) {
        Page<Application> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Application> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Application::getUserId, userId);
        wrapper.orderByDesc(Application::getCreatedAt);
        IPage<Application> result = page(pageParam, wrapper);
        enrichApplications(result.getRecords());
        return result;
    }

    public IPage<Application> getReceivedApplications(Integer page, Integer size, Long companyId, Long jobId) {
        Page<Application> pageParam = new Page<>(page, size);
        List<Long> jobIds = jobService.getJobIdsByCompanyId(companyId);
        if (jobIds.isEmpty()) {
            return pageParam;
        }
        LambdaQueryWrapper<Application> wrapper = new LambdaQueryWrapper<>();
        if (jobId != null) {
            if (!jobIds.contains(jobId)) {
                return pageParam;
            }
            wrapper.eq(Application::getJobId, jobId);
        } else {
            wrapper.in(Application::getJobId, jobIds);
        }
        wrapper.orderByDesc(Application::getCreatedAt);
        IPage<Application> result = page(pageParam, wrapper);
        enrichApplications(result.getRecords());
        return result;
    }

    public boolean canCompanyViewResume(Long resumeId, Long companyId) {
        List<Long> jobIds = jobService.getJobIdsByCompanyId(companyId);
        if (jobIds.isEmpty()) {
            return false;
        }
        LambdaQueryWrapper<Application> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Application::getResumeId, resumeId).in(Application::getJobId, jobIds);
        return count(wrapper) > 0;
    }

    public boolean belongsToCompany(Long applicationId, Long companyId) {
        Application application = getById(applicationId);
        return application != null && jobService.belongsToCompany(application.getJobId(), companyId);
    }

    public Application getApplicationById(Long applicationId) {
        return getById(applicationId);
    }

    private void enrichApplications(List<Application> records) {
        if (records == null || records.isEmpty()) {
            return;
        }
        Map<Long, Job> jobMap = jobService.listByIds(records.stream()
                .map(Application::getJobId)
                .filter(id -> id != null)
                .collect(Collectors.toSet())).stream().collect(Collectors.toMap(Job::getId, Function.identity()));
        Map<Long, User> userMap = userService.listByIds(records.stream()
                .map(Application::getUserId)
                .filter(id -> id != null)
                .collect(Collectors.toSet())).stream().collect(Collectors.toMap(User::getId, Function.identity()));
        Map<Long, Resume> resumeMap = resumeService.listByIds(records.stream()
                .map(Application::getResumeId)
                .filter(id -> id != null)
                .collect(Collectors.toSet())).stream().collect(Collectors.toMap(Resume::getId, Function.identity()));
        Map<Long, Company> companyMap = companyService.listByIds(jobMap.values().stream()
                .map(Job::getCompanyId)
                .filter(id -> id != null)
                .collect(Collectors.toSet())).stream().collect(Collectors.toMap(Company::getId, Function.identity()));
        for (Application application : records) {
            Job job = jobMap.get(application.getJobId());
            User user = userMap.get(application.getUserId());
            Resume resume = resumeMap.get(application.getResumeId());
            Company company = job == null ? null : companyMap.get(job.getCompanyId());
            application.setJobTitle(job == null ? null : job.getTitle());
            application.setCompanyName(company == null ? null : company.getName());
            application.setStudentName(user == null ? null : user.getRealName());
            application.setResumeName(resume == null ? null : resume.getName());
        }
    }
}
