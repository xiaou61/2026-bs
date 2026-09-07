package com.xiaou.recruitment.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.recruitment.entity.Application;
import com.xiaou.recruitment.entity.Company;
import com.xiaou.recruitment.entity.Interview;
import com.xiaou.recruitment.entity.Job;
import com.xiaou.recruitment.entity.User;
import com.xiaou.recruitment.mapper.InterviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class InterviewService extends ServiceImpl<InterviewMapper, Interview> {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private JobService jobService;

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    public boolean createInterview(Interview interview, Long companyId) {
        Application application = applicationService.getApplicationById(interview.getApplicationId());
        if (application == null || !applicationService.belongsToCompany(interview.getApplicationId(), companyId)) {
            return false;
        }
        interview.setUserId(application.getUserId());
        interview.setJobId(application.getJobId());
        interview.setStatus("scheduled");
        boolean saved = save(interview);
        if (saved) {
            applicationService.updateApplicationStatus(application.getId(), "interview", "已安排面试", companyId);
        }
        return saved;
    }

    public boolean updateInterview(Interview interview, Long companyId) {
        Interview existing = getById(interview.getId());
        if (existing == null || !jobService.belongsToCompany(existing.getJobId(), companyId)) {
            return false;
        }
        existing.setInterviewType(interview.getInterviewType());
        existing.setInterviewTime(interview.getInterviewTime());
        existing.setLocation(interview.getLocation());
        existing.setInterviewer(interview.getInterviewer());
        existing.setStatus(interview.getStatus());
        existing.setFeedback(interview.getFeedback());
        return updateById(existing);
    }

    public boolean cancelInterview(Long id, Long userId, Long companyId, String role) {
        Interview interview = getById(id);
        if (interview == null) {
            return false;
        }
        boolean canCancel = ("student".equals(role) && userId.equals(interview.getUserId()))
                || ("company".equals(role) && jobService.belongsToCompany(interview.getJobId(), companyId));
        if (!canCancel) {
            return false;
        }
        interview.setStatus("cancelled");
        return updateById(interview);
    }

    public IPage<Interview> getMyInterviews(Integer page, Integer size, Long userId) {
        Page<Interview> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Interview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Interview::getUserId, userId);
        wrapper.orderByDesc(Interview::getInterviewTime);
        IPage<Interview> result = page(pageParam, wrapper);
        enrichInterviews(result.getRecords());
        return result;
    }

    public IPage<Interview> getCompanyInterviews(Integer page, Integer size, Long companyId) {
        Page<Interview> pageParam = new Page<>(page, size);
        List<Long> jobIds = jobService.getJobIdsByCompanyId(companyId);
        if (jobIds.isEmpty()) {
            return pageParam;
        }
        LambdaQueryWrapper<Interview> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Interview::getJobId, jobIds);
        wrapper.orderByDesc(Interview::getInterviewTime);
        IPage<Interview> result = page(pageParam, wrapper);
        enrichInterviews(result.getRecords());
        return result;
    }

    private void enrichInterviews(List<Interview> records) {
        if (records == null || records.isEmpty()) {
            return;
        }
        Map<Long, Job> jobMap = jobService.listByIds(records.stream()
                .map(Interview::getJobId)
                .filter(id -> id != null)
                .collect(Collectors.toSet())).stream().collect(Collectors.toMap(Job::getId, Function.identity()));
        Map<Long, User> userMap = userService.listByIds(records.stream()
                .map(Interview::getUserId)
                .filter(id -> id != null)
                .collect(Collectors.toSet())).stream().collect(Collectors.toMap(User::getId, Function.identity()));
        Map<Long, Company> companyMap = companyService.listByIds(jobMap.values().stream()
                .map(Job::getCompanyId)
                .filter(id -> id != null)
                .collect(Collectors.toSet())).stream().collect(Collectors.toMap(Company::getId, Function.identity()));
        for (Interview interview : records) {
            Job job = jobMap.get(interview.getJobId());
            User user = userMap.get(interview.getUserId());
            Company company = job == null ? null : companyMap.get(job.getCompanyId());
            interview.setJobTitle(job == null ? null : job.getTitle());
            interview.setStudentName(user == null ? null : user.getRealName());
            interview.setCompanyName(company == null ? null : company.getName());
        }
    }
}
