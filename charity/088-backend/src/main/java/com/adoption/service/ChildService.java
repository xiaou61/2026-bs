package com.adoption.service;

import com.adoption.common.BusinessException;
import com.adoption.entity.ChildHealthRecord;
import com.adoption.entity.ChildProfile;
import com.adoption.mapper.ChildHealthRecordMapper;
import com.adoption.mapper.ChildProfileMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChildService {

    @Autowired
    private ChildProfileMapper childProfileMapper;

    @Autowired
    private ChildHealthRecordMapper childHealthRecordMapper;

    public Map<String, Object> page(Integer pageNum, Integer pageSize, String name, Integer adoptionStatus) {
        Page<ChildProfile> page = new Page<>(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        Page<ChildProfile> result = childProfileMapper.selectPage(page, Wrappers.<ChildProfile>lambdaQuery()
                .like(StringUtils.hasText(name), ChildProfile::getName, name)
                .eq(adoptionStatus != null, ChildProfile::getAdoptionStatus, adoptionStatus)
                .orderByDesc(ChildProfile::getCreateTime));
        fillHealthRecord(result.getRecords());
        Map<String, Object> data = new HashMap<>();
        data.put("list", result.getRecords());
        data.put("total", result.getTotal());
        return data;
    }

    public Map<String, Object> publicPage(Integer pageNum, Integer pageSize, String name, Integer gender, Integer ageMin, Integer ageMax) {
        Page<ChildProfile> page = new Page<>(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        Page<ChildProfile> result = childProfileMapper.selectPage(page, Wrappers.<ChildProfile>lambdaQuery()
                .eq(ChildProfile::getStatus, 1)
                .eq(ChildProfile::getAdoptionStatus, 0)
                .like(StringUtils.hasText(name), ChildProfile::getName, name)
                .eq(gender != null, ChildProfile::getGender, gender)
                .ge(ageMin != null, ChildProfile::getAge, ageMin)
                .le(ageMax != null, ChildProfile::getAge, ageMax)
                .orderByAsc(ChildProfile::getAge));
        fillHealthRecord(result.getRecords());
        Map<String, Object> data = new HashMap<>();
        data.put("list", result.getRecords());
        data.put("total", result.getTotal());
        return data;
    }

    public ChildProfile detail(Long id) {
        ChildProfile child = childProfileMapper.selectById(id);
        if (child == null) {
            throw new BusinessException("儿童档案不存在");
        }
        fillHealthRecord(java.util.Collections.singletonList(child));
        return child;
    }

    public void add(ChildProfile child) {
        if (!StringUtils.hasText(child.getName())) {
            throw new BusinessException("儿童姓名不能为空");
        }
        child.setChildNo("CH" + System.currentTimeMillis());
        child.setStatus(child.getStatus() == null ? 1 : child.getStatus());
        child.setAdoptionStatus(child.getAdoptionStatus() == null ? 0 : child.getAdoptionStatus());
        child.setCreateTime(LocalDateTime.now());
        childProfileMapper.insert(child);
        saveHealthRecord(child);
    }

    public void update(ChildProfile child) {
        ChildProfile dbChild = childProfileMapper.selectById(child.getId());
        if (dbChild == null) {
            throw new BusinessException("儿童档案不存在");
        }
        child.setChildNo(dbChild.getChildNo());
        childProfileMapper.updateById(child);
        saveHealthRecord(child);
    }

    public void delete(Long id) {
        childProfileMapper.deleteById(id);
        childHealthRecordMapper.delete(Wrappers.<ChildHealthRecord>lambdaQuery().eq(ChildHealthRecord::getChildId, id));
    }

    private void saveHealthRecord(ChildProfile child) {
        if (child.getHealthRecord() == null) {
            return;
        }
        ChildHealthRecord record = child.getHealthRecord();
        record.setChildId(child.getId());
        ChildHealthRecord dbRecord = childHealthRecordMapper.selectOne(Wrappers.<ChildHealthRecord>lambdaQuery().eq(ChildHealthRecord::getChildId, child.getId()));
        if (dbRecord == null) {
            childHealthRecordMapper.insert(record);
            return;
        }
        record.setId(dbRecord.getId());
        childHealthRecordMapper.updateById(record);
    }

    private void fillHealthRecord(List<ChildProfile> list) {
        for (ChildProfile item : list) {
            item.setHealthRecord(childHealthRecordMapper.selectOne(Wrappers.<ChildHealthRecord>lambdaQuery().eq(ChildHealthRecord::getChildId, item.getId())));
        }
    }
}
