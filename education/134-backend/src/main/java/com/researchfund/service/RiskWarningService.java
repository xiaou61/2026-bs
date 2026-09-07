package com.researchfund.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.researchfund.entity.RiskWarning;
import com.researchfund.mapper.RiskWarningMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiskWarningService {
    private final RiskWarningMapper riskWarningMapper;

    public PageInfo<RiskWarning> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(riskWarningMapper.selectPage(keyword, status));
    }

    public void save(RiskWarning entity) {
        if (entity.getId() == null) riskWarningMapper.insert(entity);
        else riskWarningMapper.update(entity);
    }

    public void delete(Long id) {
        riskWarningMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        riskWarningMapper.updateStatus(id, status);
    }
}
