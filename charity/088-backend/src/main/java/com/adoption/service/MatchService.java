package com.adoption.service;

import com.adoption.common.BusinessException;
import com.adoption.entity.AdoptionApplication;
import com.adoption.entity.ApprovalRecord;
import com.adoption.entity.ChildProfile;
import com.adoption.entity.MatchRecord;
import com.adoption.entity.SysUser;
import com.adoption.mapper.AdoptionApplicationMapper;
import com.adoption.mapper.ApprovalRecordMapper;
import com.adoption.mapper.ChildProfileMapper;
import com.adoption.mapper.MatchRecordMapper;
import com.adoption.mapper.SysUserMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MatchService {

    @Autowired
    private MatchRecordMapper matchRecordMapper;

    @Autowired
    private AdoptionApplicationMapper adoptionApplicationMapper;

    @Autowired
    private ApprovalRecordMapper approvalRecordMapper;

    @Autowired
    private ChildProfileMapper childProfileMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    public Map<String, Object> page(Integer pageNum, Integer pageSize) {
        Page<MatchRecord> page = new Page<>(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        Page<MatchRecord> result = matchRecordMapper.selectPage(page, Wrappers.<MatchRecord>lambdaQuery().orderByDesc(MatchRecord::getCreateTime));
        for (MatchRecord item : result.getRecords()) {
            ChildProfile child = childProfileMapper.selectById(item.getChildId());
            SysUser reviewer = sysUserMapper.selectById(item.getReviewerId());
            item.setChildName(child == null ? "" : child.getName());
            item.setReviewerName(reviewer == null ? "" : reviewer.getRealName());
        }
        Map<String, Object> data = new HashMap<>();
        data.put("list", result.getRecords());
        data.put("total", result.getTotal());
        return data;
    }

    public void add(MatchRecord record) {
        if (record.getApplicationId() == null || record.getChildId() == null) {
            throw new BusinessException("匹配信息不完整");
        }
        record.setStatus(record.getStatus() == null ? 0 : record.getStatus());
        matchRecordMapper.insert(record);
        ChildProfile child = childProfileMapper.selectById(record.getChildId());
        if (child != null && child.getAdoptionStatus() != null && child.getAdoptionStatus() == 0) {
            child.setAdoptionStatus(1);
            childProfileMapper.updateById(child);
        }
    }

    public void approve(Long reviewerId, Long matchId, Integer status, String remark) {
        MatchRecord record = matchRecordMapper.selectById(matchId);
        if (record == null) {
            throw new BusinessException("匹配记录不存在");
        }
        record.setStatus(status);
        record.setMatchRemark(remark);
        matchRecordMapper.updateById(record);
        AdoptionApplication application = adoptionApplicationMapper.selectById(record.getApplicationId());
        if (application != null) {
            application.setStatus(status != null && status == 1 ? 4 : 6);
            application.setReviewRemark(remark);
            adoptionApplicationMapper.updateById(application);
        }
        ChildProfile child = childProfileMapper.selectById(record.getChildId());
        if (child != null) {
            child.setAdoptionStatus(status != null && status == 1 ? 1 : 0);
            childProfileMapper.updateById(child);
        }
        ApprovalRecord approvalRecord = new ApprovalRecord();
        approvalRecord.setApplicationId(record.getApplicationId());
        approvalRecord.setNodeName("匹配审批");
        approvalRecord.setApprovalStatus(status);
        approvalRecord.setApprovalRemark(remark);
        approvalRecord.setApproverId(reviewerId);
        approvalRecordMapper.insert(approvalRecord);
    }
}
