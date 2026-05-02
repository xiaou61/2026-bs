package com.eldercare.service;

import com.eldercare.common.BusinessException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eldercare.entity.AbnormalWarning;
import com.eldercare.entity.FollowUpRecord;
import com.eldercare.mapper.AbnormalWarningMapper;
import com.eldercare.mapper.FollowUpRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowUpService {

    @Autowired
    private FollowUpRecordMapper followUpRecordMapper;

    @Autowired
    private AbnormalWarningMapper abnormalWarningMapper;

    public Page<FollowUpRecord> page(int pageNum, int pageSize, Long elderId, Integer status) {
        Page<FollowUpRecord> page = new Page<>(pageNum, pageSize);
        QueryWrapper<FollowUpRecord> wrapper = new QueryWrapper<>();
        if (elderId != null) {
            wrapper.eq("elder_id", elderId);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        return followUpRecordMapper.selectPage(page, wrapper);
    }

    public void add(FollowUpRecord followUpRecord, Long userId) {
        normalizeElderId(followUpRecord);
        followUpRecord.setDoctorId(userId);
        if (followUpRecord.getStatus() == null) {
            followUpRecord.setStatus(0);
        }
        followUpRecordMapper.insert(followUpRecord);
        if (followUpRecord.getWarningId() != null) {
            AbnormalWarning warning = new AbnormalWarning();
            warning.setId(followUpRecord.getWarningId());
            warning.setStatus(followUpRecord.getStatus() == 1 ? 2 : 1);
            abnormalWarningMapper.updateById(warning);
        }
    }

    public void update(FollowUpRecord followUpRecord, Long userId) {
        normalizeElderId(followUpRecord);
        followUpRecord.setDoctorId(userId);
        followUpRecordMapper.updateById(followUpRecord);
    }

    public void delete(Long id) {
        followUpRecordMapper.deleteById(id);
    }

    public FollowUpRecord getById(Long id) {
        FollowUpRecord followUpRecord = followUpRecordMapper.selectById(id);
        if (followUpRecord == null) {
            throw new BusinessException(404, "随访记录不存在");
        }
        return followUpRecord;
    }

    private void normalizeElderId(FollowUpRecord followUpRecord) {
        if (followUpRecord.getWarningId() == null) {
            return;
        }
        AbnormalWarning warning = abnormalWarningMapper.selectById(followUpRecord.getWarningId());
        if (warning == null) {
            throw new BusinessException(404, "预警记录不存在");
        }
        followUpRecord.setElderId(warning.getElderId());
    }
}
