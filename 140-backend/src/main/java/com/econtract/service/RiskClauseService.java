package com.econtract.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.econtract.entity.RiskClause;
import com.econtract.mapper.RiskClauseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiskClauseService {
    private final RiskClauseMapper riskClauseMapper;

    public PageInfo<RiskClause> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(riskClauseMapper.selectPage(keyword, status));
    }

    public void save(RiskClause entity) {
        if (entity.getId() == null) riskClauseMapper.insert(entity);
        else riskClauseMapper.update(entity);
    }

    public void delete(Long id) {
        riskClauseMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        riskClauseMapper.updateStatus(id, status);
    }
}



