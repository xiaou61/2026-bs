package com.adoption.service;

import com.adoption.common.BusinessException;
import com.adoption.entity.AdoptionApplication;
import com.adoption.entity.ApprovalRecord;
import com.adoption.entity.HomeVisitRecord;
import com.adoption.entity.SysUser;
import com.adoption.mapper.AdoptionApplicationMapper;
import com.adoption.mapper.ApprovalRecordMapper;
import com.adoption.mapper.HomeVisitRecordMapper;
import com.adoption.mapper.SysUserMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class HomeVisitService {

    @Autowired
    private HomeVisitRecordMapper homeVisitRecordMapper;

    @Autowired
    private AdoptionApplicationMapper adoptionApplicationMapper;

    @Autowired
    private ApprovalRecordMapper approvalRecordMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    public Map<String, Object> page(Integer pageNum, Integer pageSize) {
        Page<HomeVisitRecord> page = new Page<>(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        Page<HomeVisitRecord> result = homeVisitRecordMapper.selectPage(page, Wrappers.<HomeVisitRecord>lambdaQuery().orderByDesc(HomeVisitRecord::getCreateTime));
        for (HomeVisitRecord item : result.getRecords()) {
            SysUser user = sysUserMapper.selectById(item.getReviewerId());
            item.setReviewerName(user == null ? "" : user.getRealName());
        }
        Map<String, Object> data = new HashMap<>();
        data.put("list", result.getRecords());
        data.put("total", result.getTotal());
        return data;
    }

    public void add(HomeVisitRecord record) {
        if (record.getApplicationId() == null || !StringUtils.hasText(record.getVisitResult())) {
            throw new BusinessException("家访信息不完整");
        }
        homeVisitRecordMapper.insert(record);
        AdoptionApplication application = adoptionApplicationMapper.selectById(record.getApplicationId());
        if (application != null) {
            application.setStatus("通过".equals(record.getVisitResult()) ? 3 : 6);
            adoptionApplicationMapper.updateById(application);
        }
        ApprovalRecord approvalRecord = new ApprovalRecord();
        approvalRecord.setApplicationId(record.getApplicationId());
        approvalRecord.setNodeName("家访评估");
        approvalRecord.setApprovalStatus("通过".equals(record.getVisitResult()) ? 1 : 2);
        approvalRecord.setApprovalRemark(record.getVisitRemark());
        approvalRecord.setApproverId(record.getReviewerId());
        approvalRecordMapper.insert(approvalRecord);
    }

    public void update(HomeVisitRecord record) {
        homeVisitRecordMapper.updateById(record);
    }
}
