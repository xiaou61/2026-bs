package com.teacher.new.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teacher.new.common.BusinessException;
import com.teacher.new.common.PageResult;
import com.teacher.new.entity.EvaluationIndicator;
import com.teacher.new.mapper.EvaluationIndicatorMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class IndicatorService {

    @Resource
    private EvaluationIndicatorMapper evaluationIndicatorMapper;

    public PageResult<EvaluationIndicator> page(Integer pageNum, Integer pageSize, String indicatorName, String dimensionName, Integer status) {
        Page<EvaluationIndicator> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<EvaluationIndicator> wrapper = new LambdaQueryWrapper<EvaluationIndicator>()
                .like(StringUtils.hasText(indicatorName), EvaluationIndicator::getIndicatorName, indicatorName == null ? null : indicatorName.trim())
                .like(StringUtils.hasText(dimensionName), EvaluationIndicator::getDimensionName, dimensionName == null ? null : dimensionName.trim())
                .eq(status != null, EvaluationIndicator::getStatus, status)
                .orderByAsc(EvaluationIndicator::getSortNo)
                .orderByAsc(EvaluationIndicator::getId);
        Page<EvaluationIndicator> resultPage = evaluationIndicatorMapper.selectPage(page, wrapper);
        PageResult<EvaluationIndicator> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<EvaluationIndicator> list() {
        return evaluationIndicatorMapper.selectList(new LambdaQueryWrapper<EvaluationIndicator>()
                .eq(EvaluationIndicator::getStatus, 1)
                .orderByAsc(EvaluationIndicator::getSortNo)
                .orderByAsc(EvaluationIndicator::getId));
    }

    public void save(EvaluationIndicator indicator) {
        if (indicator == null || !StringUtils.hasText(indicator.getIndicatorName()) || !StringUtils.hasText(indicator.getDimensionName()) || indicator.getWeightValue() == null) {
            throw new BusinessException("指标参数不完整");
        }
        if (indicator.getWeightValue().compareTo(BigDecimal.ZERO) <= 0 || indicator.getWeightValue().compareTo(new BigDecimal("100")) > 0) {
            throw new BusinessException("权重范围不合法");
        }
        if (indicator.getId() == null) {
            add(indicator);
        } else {
            update(indicator);
        }
    }

    private void add(EvaluationIndicator indicator) {
        indicator.setIndicatorName(indicator.getIndicatorName().trim());
        indicator.setDimensionName(indicator.getDimensionName().trim());
        indicator.setSortNo(indicator.getSortNo() == null ? 1 : indicator.getSortNo());
        indicator.setStatus(indicator.getStatus() == null ? 1 : (indicator.getStatus() == 0 ? 0 : 1));
        evaluationIndicatorMapper.insert(indicator);
    }

    private void update(EvaluationIndicator indicator) {
        EvaluationIndicator db = evaluationIndicatorMapper.selectById(indicator.getId());
        if (db == null) {
            throw new BusinessException("指标不存在");
        }
        db.setIndicatorName(indicator.getIndicatorName().trim());
        db.setDimensionName(indicator.getDimensionName().trim());
        db.setWeightValue(indicator.getWeightValue());
        db.setSortNo(indicator.getSortNo() == null ? db.getSortNo() : indicator.getSortNo());
        if (indicator.getStatus() != null) {
            db.setStatus(indicator.getStatus() == 0 ? 0 : 1);
        }
        evaluationIndicatorMapper.updateById(db);
    }

    public void updateStatus(Long id, Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException("状态不合法");
        }
        EvaluationIndicator db = evaluationIndicatorMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("指标不存在");
        }
        db.setStatus(status);
        evaluationIndicatorMapper.updateById(db);
    }

    public void deleteById(Long id) {
        evaluationIndicatorMapper.deleteById(id);
    }
}
