package com.lostfound.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lostfound.entity.IdentityVerify;
import com.lostfound.mapper.IdentityVerifyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdentityVerifyService {
    private final IdentityVerifyMapper mapper;

    public PageInfo<IdentityVerify> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public void save(IdentityVerify entity) {
        if (entity.getId() == null) mapper.insert(entity);
        else mapper.update(entity);
    }

    public void delete(Long id) {
        mapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        mapper.updateStatus(id, status);
    }
}
