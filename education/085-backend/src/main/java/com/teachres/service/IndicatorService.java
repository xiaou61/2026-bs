package com.teachres.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teachres.common.BusinessException;
import com.teachres.entity.EvalIndicator;
import com.teachres.mapper.EvalIndicatorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
public class IndicatorService {

    @Autowired
    private EvalIndicatorMapper indicatorMapper;

    public PageInfo<EvalIndicator> list(String indicatorName, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<EvalIndicator> list = indicatorMapper.selectList(indicatorName, status);
        return new PageInfo<>(list);
    }

    public List<EvalIndicator> enabledList() {
        return indicatorMapper.selectEnabledList();
    }

    public void add(EvalIndicator indicator) {
        if (!StringUtils.hasText(indicator.getIndicatorName()) || indicator.getWeight() == null) {
            throw new BusinessException("指标名称和权重不能为空");
        }
        if (indicator.getSort() == null) {
            indicator.setSort(0);
        }
        if (indicator.getStatus() == null) {
            indicator.setStatus(1);
        }
        checkWeightLimit(null, indicator);
        indicatorMapper.insert(indicator);
    }

    public void update(EvalIndicator indicator) {
        if (indicator.getId() == null) {
            throw new BusinessException("参数错误");
        }
        checkWeightLimit(indicator.getId(), indicator);
        indicatorMapper.update(indicator);
    }

    public void delete(Long id) {
        indicatorMapper.deleteById(id);
    }

    private void checkWeightLimit(Long id, EvalIndicator target) {
        if (target.getStatus() != null && target.getStatus() == 0) {
            return;
        }
        BigDecimal total = indicatorMapper.sumEnabledWeight();
        if (total == null) {
            total = BigDecimal.ZERO;
        }
        if (id != null) {
            EvalIndicator old = indicatorMapper.selectById(id);
            if (old != null && old.getStatus() != null && old.getStatus() == 1 && old.getWeight() != null) {
                total = total.subtract(old.getWeight());
            }
        }
        total = total.add(target.getWeight() == null ? BigDecimal.ZERO : target.getWeight());
        if (total.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new BusinessException("启用中的指标总权重不能超过100");
        }
    }
}
