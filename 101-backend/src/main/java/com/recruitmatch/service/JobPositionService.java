package com.recruitmatch.service;

import com.recruitmatch.common.BusinessException;
import com.recruitmatch.entity.JobPosition;
import com.recruitmatch.mapper.JobPositionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class JobPositionService extends ServiceImpl<JobPositionMapper, JobPosition> {
    public void saveEntity(JobPosition entity) {
        if (entity.getId() == null) {
            entity.setStatus(0);
            entity.setCreateTime(LocalDateTime.now());
        } else {
            JobPosition db = getById(entity.getId());
            if (db == null) {
                throw new BusinessException(400, "岗位不存在");
            }
            entity.setStatus(db.getStatus());
            entity.setCreateTime(db.getCreateTime());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }
}
