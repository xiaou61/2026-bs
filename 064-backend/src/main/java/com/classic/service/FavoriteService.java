package com.classic.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.classic.common.BusinessException;
import com.classic.common.PageResult;
import com.classic.entity.Favorite;
import com.classic.entity.FormulaInfo;
import com.classic.entity.MealPlan;
import com.classic.mapper.FavoriteMapper;
import com.classic.mapper.FormulaInfoMapper;
import com.classic.mapper.MealPlanMapper;
import com.classic.vo.FavoriteVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FavoriteService {

    @Resource
    private FavoriteMapper favoriteMapper;

    @Resource
    private FormulaInfoMapper formulaInfoMapper;

    @Resource
    private MealPlanMapper mealPlanMapper;

    public void toggle(Long userId, String targetType, Long targetId) {
        if (targetType == null || targetId == null) {
            throw new BusinessException("参数不完整");
        }
        String type = targetType.trim().toUpperCase();
        if (!"FORMULA".equals(type) && !"PLAN".equals(type)) {
            throw new BusinessException("收藏类型不合法");
        }
        if ("FORMULA".equals(type)) {
            if (formulaInfoMapper.selectById(targetId) == null) {
                throw new BusinessException("经方不存在");
            }
        } else {
            if (mealPlanMapper.selectById(targetId) == null) {
                throw new BusinessException("方案不存在");
            }
        }
        Favorite exist = favoriteMapper.selectOne(new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, userId)
                .eq(Favorite::getTargetType, type)
                .eq(Favorite::getTargetId, targetId));
        if (exist == null) {
            Favorite favorite = new Favorite();
            favorite.setUserId(userId);
            favorite.setTargetType(type);
            favorite.setTargetId(targetId);
            favoriteMapper.insert(favorite);
        } else {
            favoriteMapper.deleteById(exist.getId());
        }
    }

    public PageResult<FavoriteVO> myPage(Long userId, Integer pageNum, Integer pageSize, String targetType) {
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, userId);
        wrapper.eq(targetType != null && !targetType.trim().isEmpty(), Favorite::getTargetType, targetType == null ? null : targetType.trim().toUpperCase());
        wrapper.orderByDesc(Favorite::getId);
        Page<Favorite> page = favoriteMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        PageResult<FavoriteVO> result = new PageResult<>();
        result.setTotal(page.getTotal());
        result.setRecords(convertList(page.getRecords()));
        return result;
    }

    private List<FavoriteVO> convertList(List<Favorite> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<FormulaInfo> formulas = formulaInfoMapper.selectList(new LambdaQueryWrapper<>());
        List<MealPlan> plans = mealPlanMapper.selectList(new LambdaQueryWrapper<>());
        Map<Long, String> formulaMap = new HashMap<>();
        for (FormulaInfo formula : formulas) {
            formulaMap.put(formula.getId(), formula.getName());
        }
        Map<Long, String> planMap = new HashMap<>();
        for (MealPlan plan : plans) {
            planMap.put(plan.getId(), plan.getName());
        }
        List<FavoriteVO> result = new ArrayList<>();
        for (Favorite item : list) {
            FavoriteVO vo = new FavoriteVO();
            BeanUtils.copyProperties(item, vo);
            if ("FORMULA".equals(item.getTargetType())) {
                vo.setTargetName(formulaMap.getOrDefault(item.getTargetId(), "未知经方"));
            } else {
                vo.setTargetName(planMap.getOrDefault(item.getTargetId(), "未知方案"));
            }
            result.add(vo);
        }
        return result;
    }
}
