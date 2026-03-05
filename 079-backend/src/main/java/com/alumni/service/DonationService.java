package com.alumni.service;

import com.alumni.common.BusinessException;
import com.alumni.entity.DonationProject;
import com.alumni.entity.DonationRecord;
import com.alumni.entity.User;
import com.alumni.mapper.DonationProjectMapper;
import com.alumni.mapper.DonationRecordMapper;
import com.alumni.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DonationService {

    @Autowired
    private DonationProjectMapper donationProjectMapper;

    @Autowired
    private DonationRecordMapper donationRecordMapper;

    @Autowired
    private UserMapper userMapper;

    public Page<DonationProject> projectList(Integer pageNum, Integer pageSize, Integer status) {
        Page<DonationProject> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<DonationProject> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(DonationProject::getStatus, status);
        }
        wrapper.orderByDesc(DonationProject::getCreateTime);
        return donationProjectMapper.selectPage(page, wrapper);
    }

    public DonationProject getProjectById(Long id) {
        return donationProjectMapper.selectById(id);
    }

    public void addProject(DonationProject project) {
        project.setCurrentAmount(new java.math.BigDecimal(0));
        project.setStatus(0);
        donationProjectMapper.insert(project);
    }

    public void updateProject(DonationProject project) {
        donationProjectMapper.updateById(project);
    }

    public void deleteProject(Long id) {
        donationProjectMapper.deleteById(id);
    }

    public void donate(DonationRecord record) {
        DonationProject project = donationProjectMapper.selectById(record.getProjectId());
        if (project == null) {
            throw new BusinessException("项目不存在");
        }
        if (project.getStatus() != 0) {
            throw new BusinessException("项目已结束");
        }
        record.setStatus(0);
        record.setCertificateNo("CERT" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        donationRecordMapper.insert(record);
    }

    public Page<DonationRecord> recordList(Integer pageNum, Integer pageSize, Long projectId, Long userId) {
        Page<DonationRecord> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<DonationRecord> wrapper = new LambdaQueryWrapper<>();
        if (projectId != null) {
            wrapper.eq(DonationRecord::getProjectId, projectId);
        }
        if (userId != null) {
            wrapper.eq(DonationRecord::getUserId, userId);
        }
        wrapper.orderByDesc(DonationRecord::getCreateTime);
        Page<DonationRecord> result = donationRecordMapper.selectPage(page, wrapper);
        List<User> users = userMapper.selectList(null);
        Map<Long, String> userMap = users.stream().collect(Collectors.toMap(User::getId, User::getName));
        List<DonationProject> projects = donationProjectMapper.selectList(null);
        Map<Long, String> projectMap = projects.stream().collect(Collectors.toMap(DonationProject::getId, DonationProject::getName));
        result.getRecords().forEach(r -> {
            if (r.getIsAnonymous() == 1) {
                r.setUserName("匿名");
            } else {
                r.setUserName(userMap.get(r.getUserId()));
            }
            r.setProjectName(projectMap.get(r.getProjectId()));
        });
        return result;
    }

    public void confirmRecord(Long id) {
        DonationRecord record = donationRecordMapper.selectById(id);
        if (record == null) return;
        record.setStatus(1);
        donationRecordMapper.updateById(record);
        DonationProject project = donationProjectMapper.selectById(record.getProjectId());
        if (project != null) {
            project.setCurrentAmount(project.getCurrentAmount().add(record.getAmount()));
            donationProjectMapper.updateById(project);
        }
    }
}
