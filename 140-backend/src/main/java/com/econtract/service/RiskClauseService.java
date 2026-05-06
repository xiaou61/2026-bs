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
    private final RiskClauseMapper performanceStatisticMapper;

    public PageInfo<RiskClause> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(performanceStatisticMapper.selectPage(keyword, status));
    }

    public void save(RiskClause entity) {
        if (entity.getId() == null) performanceStatisticMapper.insert(entity);
        else performanceStatisticMapper.update(entity);
    }

    public void delete(Long id) {
        performanceStatisticMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        performanceStatisticMapper.updateStatus(id, status);
    }
}



