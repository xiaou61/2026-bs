package com.legalcase.service;

import com.legalcase.common.BusinessException;
import com.legalcase.entity.LawyerProfile;
import com.legalcase.mapper.LawyerProfileMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LawyerProfileService {
    @Autowired
    private LawyerProfileMapper mapper;

    public PageInfo<LawyerProfile> page(Integer pageNum, Integer pageSize, String keyword, Integer serviceStatus) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, serviceStatus));
    }

    public LawyerProfile getById(Long id) {
        return mapper.selectById(id);
    }

    public void saveEntity(LawyerProfile entity) {
        if (entity.getId() == null) {
            entity.setServiceStatus(entity.getServiceStatus() == null ? 1 : entity.getServiceStatus());
            entity.setCreateTime(LocalDateTime.now());
        } else {
            LawyerProfile db = mapper.selectById(entity.getId());
            if (db == null) {
                throw new BusinessException(400, "律师档案不存在");
            }
            entity.setCreateTime(db.getCreateTime());
        }
        entity.setUpdateTime(LocalDateTime.now());
        if (entity.getId() == null) {
            mapper.insert(entity);
        } else {
            mapper.updateById(entity);
        }
    }

    public void delete(Long id) {
        mapper.deleteById(id);
    }

    public long countAll() {
        return mapper.countAll();
    }
}
