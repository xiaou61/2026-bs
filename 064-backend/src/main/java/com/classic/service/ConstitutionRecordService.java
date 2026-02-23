package com.classic.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.classic.common.BusinessException;
import com.classic.common.PageResult;
import com.classic.entity.ConstitutionRecord;
import com.classic.entity.User;
import com.classic.mapper.ConstitutionRecordMapper;
import com.classic.mapper.UserMapper;
import com.classic.vo.ConstitutionRecordVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConstitutionRecordService {

    @Resource
    private ConstitutionRecordMapper constitutionRecordMapper;

    @Resource
    private UserMapper userMapper;

    public PageResult<ConstitutionRecordVO> myPage(Long userId, Integer pageNum, Integer pageSize, Integer status) {
        LambdaQueryWrapper<ConstitutionRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ConstitutionRecord::getUserId, userId);
        wrapper.eq(status != null, ConstitutionRecord::getStatus, status);
        wrapper.orderByDesc(ConstitutionRecord::getId);
        Page<ConstitutionRecord> page = constitutionRecordMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        PageResult<ConstitutionRecordVO> result = new PageResult<>();
        result.setTotal(page.getTotal());
        result.setRecords(convertList(page.getRecords()));
        return result;
    }

    public PageResult<ConstitutionRecordVO> page(Integer pageNum, Integer pageSize, Long userId, Integer status) {
        LambdaQueryWrapper<ConstitutionRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(userId != null, ConstitutionRecord::getUserId, userId);
        wrapper.eq(status != null, ConstitutionRecord::getStatus, status);
        wrapper.orderByDesc(ConstitutionRecord::getId);
        Page<ConstitutionRecord> page = constitutionRecordMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        PageResult<ConstitutionRecordVO> result = new PageResult<>();
        result.setTotal(page.getTotal());
        result.setRecords(convertList(page.getRecords()));
        return result;
    }

    public void save(ConstitutionRecord record, Long userId) {
        if (record == null || record.getConstitutionType() == null || record.getConstitutionType().trim().isEmpty()) {
            throw new BusinessException("体质类型不能为空");
        }
        if (record.getSymptomDesc() == null || record.getSymptomDesc().trim().isEmpty()) {
            throw new BusinessException("症状描述不能为空");
        }
        ConstitutionRecord entity = new ConstitutionRecord();
        entity.setUserId(userId);
        entity.setConstitutionType(record.getConstitutionType().trim());
        entity.setSymptomDesc(record.getSymptomDesc().trim());
        entity.setStatus(0);
        constitutionRecordMapper.insert(entity);
    }

    public void reply(Long id, String suggestion) {
        if (suggestion == null || suggestion.trim().isEmpty()) {
            throw new BusinessException("建议内容不能为空");
        }
        ConstitutionRecord record = constitutionRecordMapper.selectById(id);
        if (record == null) {
            throw new BusinessException("体质记录不存在");
        }
        record.setSuggestion(suggestion.trim());
        record.setStatus(1);
        constitutionRecordMapper.updateById(record);
    }

    public Long countReplied() {
        return constitutionRecordMapper.selectCount(new LambdaQueryWrapper<ConstitutionRecord>().eq(ConstitutionRecord::getStatus, 1));
    }

    private List<ConstitutionRecordVO> convertList(List<ConstitutionRecord> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<User> users = userMapper.selectList(new LambdaQueryWrapper<>());
        Map<Long, String> userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getId(), user.getNickname() == null || user.getNickname().trim().isEmpty() ? user.getUsername() : user.getNickname());
        }
        List<ConstitutionRecordVO> result = new ArrayList<>();
        for (ConstitutionRecord item : list) {
            ConstitutionRecordVO vo = new ConstitutionRecordVO();
            BeanUtils.copyProperties(item, vo);
            vo.setUserName(userMap.getOrDefault(item.getUserId(), "未知用户"));
            result.add(vo);
        }
        return result;
    }
}
