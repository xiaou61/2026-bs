package com.student.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.student.common.BusinessException;
import com.student.common.PageResult;
import com.student.entity.Job;
import com.student.entity.User;
import com.student.mapper.ApplyMapper;
import com.student.mapper.JobMapper;
import com.student.mapper.UserMapper;
import com.student.vo.JobVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobService {

    @Resource
    private JobMapper jobMapper;

    @Resource
    private ApplyMapper applyMapper;

    @Resource
    private UserMapper userMapper;

    public PageResult<JobVO> page(Integer pageNum, Integer pageSize, String keyword, Integer status, Long publisherId, String role, Long userId) {
        Long finalPublisherId = publisherId;
        if ("TEACHER".equals(role)) {
            finalPublisherId = userId;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Job> list = jobMapper.selectPageList(keyword, finalPublisherId, status);
        PageInfo<Job> pageInfo = new PageInfo<>(list);
        PageResult<JobVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(convertList(pageInfo.getList()));
        return result;
    }

    public List<JobVO> list(Integer status) {
        return convertList(jobMapper.selectList(status));
    }

    public Job mustGetById(Long id) {
        Job job = jobMapper.selectById(id);
        if (job == null) {
            throw new BusinessException("岗位不存在");
        }
        return job;
    }

    public void save(Job job, String role, Long userId) {
        if (job == null) {
            throw new BusinessException("岗位参数不能为空");
        }
        if (job.getTitle() == null || job.getTitle().trim().isEmpty()) {
            throw new BusinessException("岗位名称不能为空");
        }
        if (job.getCompany() == null || job.getCompany().trim().isEmpty()) {
            throw new BusinessException("企业名称不能为空");
        }
        job.setTitle(job.getTitle().trim());
        job.setCompany(job.getCompany().trim());
        job.setCity(job.getCity() == null ? "" : job.getCity().trim());
        job.setSalary(job.getSalary() == null ? "" : job.getSalary().trim());
        job.setDescription(job.getDescription() == null ? "" : job.getDescription().trim());
        job.setStatus(normalizeStatus(job.getStatus(), 1));
        if (job.getId() == null) {
            add(job, role, userId);
        } else {
            update(job, role, userId);
        }
    }

    private void add(Job job, String role, Long userId) {
        if ("TEACHER".equals(role)) {
            job.setPublisherId(userId);
        }
        if (job.getPublisherId() == null) {
            throw new BusinessException("请选择发布人");
        }
        User publisher = userMapper.selectById(job.getPublisherId());
        if (publisher == null || publisher.getStatus() == null || publisher.getStatus() == 0 || (!"TEACHER".equals(publisher.getRole()) && !"ADMIN".equals(publisher.getRole()))) {
            throw new BusinessException("发布人不存在");
        }
        jobMapper.insert(job);
    }

    private void update(Job job, String role, Long userId) {
        Job db = mustGetById(job.getId());
        if ("TEACHER".equals(role) && !userId.equals(db.getPublisherId())) {
            throw new BusinessException("无权限修改该岗位");
        }
        if ("TEACHER".equals(role)) {
            job.setPublisherId(userId);
        }
        if (job.getPublisherId() == null) {
            job.setPublisherId(db.getPublisherId());
        }
        User publisher = userMapper.selectById(job.getPublisherId());
        if (publisher == null || publisher.getStatus() == null || publisher.getStatus() == 0 || (!"TEACHER".equals(publisher.getRole()) && !"ADMIN".equals(publisher.getRole()))) {
            throw new BusinessException("发布人不存在");
        }
        jobMapper.update(job);
    }

    public void deleteById(Long id, String role, Long userId) {
        Job db = mustGetById(id);
        if ("TEACHER".equals(role) && !userId.equals(db.getPublisherId())) {
            throw new BusinessException("无权限删除该岗位");
        }
        jobMapper.deleteById(id);
        applyMapper.deleteByJobId(id);
    }

    public Long countAll() {
        Long count = jobMapper.countAll();
        return count == null ? 0L : count;
    }

    private Integer normalizeStatus(Integer status, Integer fallback) {
        Integer s = status == null ? fallback : status;
        if (s == null) {
            s = 1;
        }
        if (s < 0 || s > 2) {
            throw new BusinessException("岗位状态不合法");
        }
        return s;
    }

    private List<JobVO> convertList(List<Job> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        Map<Long, String> userMap = buildUserMap();
        List<JobVO> result = new ArrayList<>();
        for (Job item : list) {
            JobVO vo = new JobVO();
            BeanUtils.copyProperties(item, vo);
            vo.setPublisherName(userMap.getOrDefault(item.getPublisherId(), ""));
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
