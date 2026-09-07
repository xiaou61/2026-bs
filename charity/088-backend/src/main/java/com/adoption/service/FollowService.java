package com.adoption.service;

import com.adoption.entity.FollowUpRecord;
import com.adoption.entity.SysUser;
import com.adoption.mapper.FollowUpRecordMapper;
import com.adoption.mapper.SysUserMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FollowService {

    @Autowired
    private FollowUpRecordMapper followUpRecordMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    public Map<String, Object> page(Integer pageNum, Integer pageSize) {
        Page<FollowUpRecord> page = new Page<>(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        Page<FollowUpRecord> result = followUpRecordMapper.selectPage(page, Wrappers.<FollowUpRecord>lambdaQuery().orderByDesc(FollowUpRecord::getCreateTime));
        for (FollowUpRecord item : result.getRecords()) {
            SysUser reviewer = sysUserMapper.selectById(item.getReviewerId());
            item.setReviewerName(reviewer == null ? "" : reviewer.getRealName());
        }
        Map<String, Object> data = new HashMap<>();
        data.put("list", result.getRecords());
        data.put("total", result.getTotal());
        return data;
    }

    public void add(FollowUpRecord record) {
        followUpRecordMapper.insert(record);
    }

    public void update(FollowUpRecord record) {
        followUpRecordMapper.updateById(record);
    }
}
