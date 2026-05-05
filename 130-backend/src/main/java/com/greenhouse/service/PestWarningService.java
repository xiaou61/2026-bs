package com.greenhouse.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.greenhouse.entity.PestWarning;
import com.greenhouse.mapper.PestWarningMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PestWarningService {
    private final PestWarningMapper pestWarningMapper;

    public PageInfo<PestWarning> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(pestWarningMapper.selectPage(keyword, status));
    }

    public void save(PestWarning entity) {
        if (entity.getId() == null) pestWarningMapper.insert(entity);
        else pestWarningMapper.update(entity);
    }

    public void delete(Long id) {
        pestWarningMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        pestWarningMapper.updateStatus(id, status);
    }
}
