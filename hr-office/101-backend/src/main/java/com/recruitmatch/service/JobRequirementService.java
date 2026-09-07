package com.recruitmatch.service;

import com.recruitmatch.common.BusinessException;
import com.recruitmatch.entity.JobRequirement;
import com.recruitmatch.mapper.JobRequirementMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class JobRequirementService extends ServiceImpl<JobRequirementMapper, JobRequirement> {
    public void saveEntity(JobRequirement entity) {
        if (entity.getId() == null) {
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setCreateTime(LocalDateTime.now());
        } else {
            JobRequirement db = getById(entity.getId());
            if (db == null) {
                throw new BusinessException(400, "岗位要求不存在");
            }
            entity.setCreateTime(db.getCreateTime());
            entity.setStatus(entity.getStatus() == null ? db.getStatus() : entity.getStatus());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }
}
