package com.student.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.student.common.BusinessException;
import com.student.common.PageResult;
import com.student.entity.Job;
import com.student.entity.JobApply;
import com.student.entity.User;
import com.student.mapper.ApplyMapper;
import com.student.mapper.JobMapper;
import com.student.mapper.UserMapper;
import com.student.vo.ApplyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApplyService {

    @Resource
    private ApplyMapper applyMapper;

    @Resource
    private JobMapper jobMapper;

    @Resource
    private UserMapper userMapper;

    public PageResult<ApplyVO> page(Integer pageNum, Integer pageSize, Long jobId, Long studentId, Integer status, String role, Long userId) {
        Long publisherId = "TEACHER".equals(role) ? userId : null;
        PageHelper.startPage(pageNum, pageSize);
        List<JobApply> list = applyMapper.selectPageList(jobId, studentId, status, publisherId);
        PageInfo<JobApply> pageInfo = new PageInfo<>(list);
        PageResult<ApplyVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(convertList(pageInfo.getList()));
        return result;
    }

    public PageResult<ApplyVO> myPage(Long studentId, Integer pageNum, Integer pageSize, Integer status, Long jobId) {
        PageHelper.startPage(pageNum, pageSize);
        List<JobApply> list = applyMapper.selectPageList(jobId, studentId, status, null);
        PageInfo<JobApply> pageInfo = new PageInfo<>(list);
        PageResult<ApplyVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(convertList(pageInfo.getList()));
        return result;
    }

    public void add(Long jobId, Long studentId, String resumeUrl, String applyNote) {
        if (jobId == null) {
            throw new BusinessException("岗位不能为空");
        }
        Job job = jobMapper.selectById(jobId);
        if (job == null) {
            throw new BusinessException("岗位不存在");
        }
        if (job.getStatus() == null || job.getStatus() != 1) {
            throw new BusinessException("当前岗位不可投递");
        }
        if (job.getDeadline() != null && job.getDeadline().isBefore(LocalDate.now())) {
            throw new BusinessException("岗位已过投递截止日期");
        }
        JobApply exist = applyMapper.selectByJobAndStudent(jobId, studentId);
        if (exist != null) {
            throw new BusinessException("你已投递该岗位");
        }
        JobApply apply = new JobApply();
        apply.setJobId(jobId);
        apply.setStudentId(studentId);
        apply.setResumeUrl(resumeUrl == null ? "" : resumeUrl.trim());
        apply.setApplyNote(applyNote == null ? "" : applyNote.trim());
        apply.setStatus(0);
        apply.setReviewNote("");
        apply.setReviewerId(null);
        applyMapper.insert(apply);
    }

    public void review(Long id, Integer status, String reviewNote, Long reviewerId, String role, Long userId) {
        if (status == null || (status != 1 && status != 2)) {
            throw new BusinessException("审核状态不合法");
        }
        JobApply db = applyMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("投递记录不存在");
        }
        Job job = jobMapper.selectById(db.getJobId());
        if (job == null) {
            throw new BusinessException("岗位不存在");
        }
        if ("TEACHER".equals(role) && !userId.equals(job.getPublisherId())) {
            throw new BusinessException("无权限审核该记录");
        }
        db.setStatus(status);
        db.setReviewNote(reviewNote == null ? "" : reviewNote.trim());
        db.setReviewerId(reviewerId);
        applyMapper.updateReview(db);
    }

    public void deleteById(Long id, String role, Long userId) {
        JobApply db = applyMapper.selectById(id);
        if (db == null) {
            return;
        }
        Job job = jobMapper.selectById(db.getJobId());
        if ("STUDENT".equals(role) && !userId.equals(db.getStudentId())) {
            throw new BusinessException("无权限删除该记录");
        }
        if ("TEACHER".equals(role) && (job == null || !userId.equals(job.getPublisherId()))) {
            throw new BusinessException("无权限删除该记录");
        }
        applyMapper.deleteById(id);
    }

    public Long countAll() {
        Long count = applyMapper.countAll();
        return count == null ? 0L : count;
    }

    private List<ApplyVO> convertList(List<JobApply> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        Map<Long, Job> jobMap = new HashMap<>();
        for (Job job : jobMapper.selectList(null)) {
            jobMap.put(job.getId(), job);
        }
        Map<Long, String> userMap = buildUserMap();
        List<ApplyVO> result = new ArrayList<>();
        for (JobApply item : list) {
            ApplyVO vo = new ApplyVO();
            BeanUtils.copyProperties(item, vo);
            Job job = jobMap.get(item.getJobId());
            if (job != null) {
                vo.setJobTitle(job.getTitle());
                vo.setCompany(job.getCompany());
            } else {
                vo.setJobTitle("");
                vo.setCompany("");
            }
            vo.setStudentName(userMap.getOrDefault(item.getStudentId(), ""));
            vo.setReviewerName(item.getReviewerId() == null ? "" : userMap.getOrDefault(item.getReviewerId(), ""));
            result.add(vo);
        }
        return result;
    }

    private Map<Long, String> buildUserMap() {
        List<User> users = userMapper.selectPageList(null, null, null, null);
        Map<Long, String> userMap = new HashMap<>();
        for (User user : users) {
            String name = user.getNickname() == null || user.getNickname().trim().isEmpty() ? user.getUsername() : user.getNickname();
            userMap.put(user.getId(), name);
        }
        return userMap;
    }
}
