package com.xiaou.herbal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.herbal.common.Constants;
import com.xiaou.herbal.entity.Ingredient;
import com.xiaou.herbal.entity.IngredientTaboo;
import com.xiaou.herbal.mapper.IngredientMapper;
import com.xiaou.herbal.mapper.IngredientTabooMapper;
import com.xiaou.herbal.service.IngredientService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl extends ServiceImpl<IngredientMapper, Ingredient> implements IngredientService {

    private final IngredientTabooMapper tabooMapper;

    public IngredientServiceImpl(IngredientTabooMapper tabooMapper) {
        this.tabooMapper = tabooMapper;
    }

    @Override
    public Ingredient getIngredientDetail(Long ingredientId) {
        return baseMapper.selectById(ingredientId);
    }

    @Override
    public List<Ingredient> listByCategory(String category) {
        LambdaQueryWrapper<Ingredient> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Ingredient::getCategory, category)
                .eq(Ingredient::getStatus, Constants.IngredientStatus.NORMAL);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<Ingredient> searchIngredients(String keyword) {
        LambdaQueryWrapper<Ingredient> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Ingredient::getName, keyword)
                .eq(Ingredient::getStatus, Constants.IngredientStatus.NORMAL);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<IngredientTaboo> getTaboos(Long ingredientId) {
        LambdaQueryWrapper<IngredientTaboo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(IngredientTaboo::getIngredientId1, ingredientId)
                .or()
                .eq(IngredientTaboo::getIngredientId2, ingredientId);
        return tabooMapper.selectList(wrapper);
    }

    @Override
    public void addTaboo(Long ingredientId1, Long ingredientId2, String reason) {
        IngredientTaboo taboo = IngredientTaboo.builder()
                .ingredientId1(ingredientId1)
                .ingredientId2(ingredientId2)
                .reason(reason)
                .createTime(LocalDateTime.now())
                .build();
        tabooMapper.insert(taboo);
    }

    @Override
    public List<Ingredient> getTabooedIngredients(Long ingredientId) {
        List<IngredientTaboo> taboos = getTaboos(ingredientId);
        return taboos.stream()
                .map(t -> {
                    Long id = t.getIngredientId1().equals(ingredientId) ? t.getIngredientId2() : t.getIngredientId1();
                    return baseMapper.selectById(id);
                })
                .collect(Collectors.toList());
    }
}
