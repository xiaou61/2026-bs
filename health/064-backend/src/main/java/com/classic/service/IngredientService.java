package com.classic.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.classic.common.BusinessException;
import com.classic.common.PageResult;
import com.classic.entity.Category;
import com.classic.entity.Ingredient;
import com.classic.mapper.CategoryMapper;
import com.classic.mapper.IngredientMapper;
import com.classic.vo.IngredientVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IngredientService {

    @Resource
    private IngredientMapper ingredientMapper;

    @Resource
    private CategoryMapper categoryMapper;

    public List<Ingredient> list() {
        return ingredientMapper.selectList(new LambdaQueryWrapper<Ingredient>().eq(Ingredient::getStatus, 1).orderByDesc(Ingredient::getId));
    }

    public PageResult<IngredientVO> page(Integer pageNum, Integer pageSize, String name, Long categoryId, Integer status) {
        LambdaQueryWrapper<Ingredient> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null && !name.trim().isEmpty(), Ingredient::getName, name == null ? null : name.trim());
        wrapper.eq(categoryId != null, Ingredient::getCategoryId, categoryId);
        wrapper.eq(status != null, Ingredient::getStatus, status);
        wrapper.orderByDesc(Ingredient::getId);
        Page<Ingredient> page = ingredientMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        PageResult<IngredientVO> result = new PageResult<>();
        result.setTotal(page.getTotal());
        result.setRecords(convertList(page.getRecords()));
        return result;
    }

    public void save(Ingredient ingredient) {
        if (ingredient == null || ingredient.getName() == null || ingredient.getName().trim().isEmpty()) {
            throw new BusinessException("食材名称不能为空");
        }
        if (ingredient.getCategoryId() == null || categoryMapper.selectById(ingredient.getCategoryId()) == null) {
            throw new BusinessException("食材分类不存在");
        }
        ingredient.setName(ingredient.getName().trim());
        ingredient.setNatureTaste(ingredient.getNatureTaste() == null ? null : ingredient.getNatureTaste().trim());
        ingredient.setMeridian(ingredient.getMeridian() == null ? null : ingredient.getMeridian().trim());
        ingredient.setEfficacy(ingredient.getEfficacy() == null ? null : ingredient.getEfficacy().trim());
        ingredient.setSuitablePeople(ingredient.getSuitablePeople() == null ? null : ingredient.getSuitablePeople().trim());
        ingredient.setTabooPeople(ingredient.getTabooPeople() == null ? null : ingredient.getTabooPeople().trim());
        ingredient.setStatus(ingredient.getStatus() == null ? 1 : ingredient.getStatus());
        if (ingredient.getId() == null) {
            ingredientMapper.insert(ingredient);
        } else {
            if (ingredientMapper.selectById(ingredient.getId()) == null) {
                throw new BusinessException("食材不存在");
            }
            ingredientMapper.updateById(ingredient);
        }
    }

    public void deleteById(Long id) {
        ingredientMapper.deleteById(id);
    }

    public Long countAll() {
        return ingredientMapper.selectCount(new LambdaQueryWrapper<>());
    }

    private List<IngredientVO> convertList(List<Ingredient> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<>());
        Map<Long, String> categoryMap = new HashMap<>();
        for (Category category : categories) {
            categoryMap.put(category.getId(), category.getName());
        }
        List<IngredientVO> result = new ArrayList<>();
        for (Ingredient item : list) {
            IngredientVO vo = new IngredientVO();
            BeanUtils.copyProperties(item, vo);
            vo.setCategoryName(categoryMap.getOrDefault(item.getCategoryId(), "未知分类"));
            result.add(vo);
        }
        return result;
    }
}
