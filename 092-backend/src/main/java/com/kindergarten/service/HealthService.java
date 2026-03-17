package com.kindergarten.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kindergarten.common.BusinessException;
import com.kindergarten.entity.HealthRecord;
import com.kindergarten.mapper.HealthRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthService {

    @Autowired
    private HealthRecordMapper healthRecordMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<HealthRecord> list(Long childId, Long userId, String role, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Long parentId = "parent".equals(role) ? userId : null;
        Long teacherId = "teacher".equals(role) ? userId : null;
        List<HealthRecord> list = healthRecordMapper.selectList(childId, parentId, teacherId);
        return new PageInfo<>(list);
    }

    public void save(HealthRecord entity, Long userId, String role) {
        authService.assertTeacher(role);
        if (entity == null || entity.getChildId() == null || entity.getRecordDate() == null) {
            throw new BusinessException("幼儿档案和晨检日期不能为空");
        }
        entity.setTeacherId(userId);
        if (entity.getId() == null) {
            healthRecordMapper.insert(entity);
            return;
        }
        HealthRecord current = healthRecordMapper.selectById(entity.getId());
        if (current == null) {
            throw new BusinessException("晨检记录不存在");
        }
        healthRecordMapper.update(entity);
    }
}
