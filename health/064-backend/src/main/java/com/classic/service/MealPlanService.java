package com.classic.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.classic.common.BusinessException;
import com.classic.common.PageResult;
import com.classic.entity.FormulaInfo;
import com.classic.entity.MealPlan;
import com.classic.mapper.FormulaInfoMapper;
import com.classic.mapper.MealPlanMapper;
import com.classic.vo.MealPlanVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MealPlanService {

    @Resource
    private MealPlanMapper mealPlanMapper;

    @Resource
    private FormulaInfoMapper formulaInfoMapper;

    public List<MealPlanVO> list() {
        List<MealPlan> plans = mealPlanMapper.selectList(new LambdaQueryWrapper<MealPlan>().eq(MealPlan::getStatus, 1).orderByDesc(MealPlan::getId));
        return convertList(plans);
    }

    public List<MealPlan> listAllForMap() {
        return mealPlanMapper.selectList(new LambdaQueryWrapper<>());
    }

    public PageResult<MealPlanVO> page(Integer pageNum, Integer pageSize, String name, String suitableConstitution, Integer status) {
        LambdaQueryWrapper<MealPlan> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null && !name.trim().isEmpty(), MealPlan::getName, name == null ? null : name.trim());
        wrapper.like(suitableConstitution != null && !suitableConstitution.trim().isEmpty(), MealPlan::getSuitableConstitution, suitableConstitution == null ? null : suitableConstitution.trim());
        wrapper.eq(status != null, MealPlan::getStatus, status);
        wrapper.orderByDesc(MealPlan::getId);
        Page<MealPlan> page = mealPlanMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        PageResult<MealPlanVO> result = new PageResult<>();
        result.setTotal(page.getTotal());
        result.setRecords(convertList(page.getRecords()));
        return result;
    }

    public void save(MealPlan mealPlan) {
        if (mealPlan == null || mealPlan.getName() == null || mealPlan.getName().trim().isEmpty()) {
            throw new BusinessException("方案名称不能为空");
        }
        if (mealPlan.getFormulaId() != null && formulaInfoMapper.selectById(mealPlan.getFormulaId()) == null) {
            throw new BusinessException("关联经方不存在");
        }
        mealPlan.setName(mealPlan.getName().trim());
        mealPlan.setIngredientSummary(mealPlan.getIngredientSummary() == null ? null : mealPlan.getIngredientSummary().trim());
        mealPlan.setSuitableConstitution(mealPlan.getSuitableConstitution() == null ? null : mealPlan.getSuitableConstitution().trim());
        mealPlan.setMealTime(mealPlan.getMealTime() == null ? null : mealPlan.getMealTime().trim());
        mealPlan.setSteps(mealPlan.getSteps() == null ? null : mealPlan.getSteps().trim());
        mealPlan.setStatus(mealPlan.getStatus() == null ? 1 : mealPlan.getStatus());
        if (mealPlan.getId() == null) {
            mealPlanMapper.insert(mealPlan);
        } else {
            if (mealPlanMapper.selectById(mealPlan.getId()) == null) {
                throw new BusinessException("方案不存在");
            }
            mealPlanMapper.updateById(mealPlan);
        }
    }

    public void deleteById(Long id) {
        mealPlanMapper.deleteById(id);
    }

    public Long countAll() {
        return mealPlanMapper.selectCount(new LambdaQueryWrapper<>());
    }

    private List<MealPlanVO> convertList(List<MealPlan> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<FormulaInfo> formulas = formulaInfoMapper.selectList(new LambdaQueryWrapper<>());
        Map<Long, String> formulaMap = new HashMap<>();
        for (FormulaInfo formula : formulas) {
            formulaMap.put(formula.getId(), formula.getName());
        }
        List<MealPlanVO> result = new ArrayList<>();
        for (MealPlan item : list) {
            MealPlanVO vo = new MealPlanVO();
            BeanUtils.copyProperties(item, vo);
            vo.setFormulaName(formulaMap.getOrDefault(item.getFormulaId(), "未关联"));
            result.add(vo);
        }
        return result;
    }
}
