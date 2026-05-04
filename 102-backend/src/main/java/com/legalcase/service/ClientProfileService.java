package com.legalcase.service;

import com.legalcase.common.BusinessException;
import com.legalcase.entity.ClientProfile;
import com.legalcase.mapper.ClientProfileMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ClientProfileService {
    @Autowired
    private ClientProfileMapper mapper;

    public PageInfo<ClientProfile> page(Integer pageNum, Integer pageSize, String keyword, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public ClientProfile getById(Long id) {
        return mapper.selectById(id);
    }

    public void saveEntity(ClientProfile entity) {
        if (entity.getId() == null) {
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setCreateTime(LocalDateTime.now());
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
