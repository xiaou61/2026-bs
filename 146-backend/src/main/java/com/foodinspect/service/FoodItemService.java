package com.foodinspect.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.foodinspect.entity.FoodItem;
import com.foodinspect.mapper.FoodItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodItemService {
    private final FoodItemMapper budgetCategoryMapper;

    public PageInfo<FoodItem> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(budgetCategoryMapper.selectPage(keyword, status));
    }

    public void save(FoodItem entity) {
        if (entity.getId() == null) budgetCategoryMapper.insert(entity);
        else budgetCategoryMapper.update(entity);
    }

    public void delete(Long id) {
        budgetCategoryMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        budgetCategoryMapper.updateStatus(id, status);
    }
}






