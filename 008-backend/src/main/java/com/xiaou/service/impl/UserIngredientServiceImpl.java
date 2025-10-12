package com.xiaou.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Ingredient;
import com.xiaou.entity.UserIngredient;
import com.xiaou.exception.BusinessException;
import com.xiaou.mapper.UserIngredientMapper;
import com.xiaou.service.IngredientService;
import com.xiaou.service.UserIngredientService;
import com.xiaou.vo.UserIngredientVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserIngredientServiceImpl extends ServiceImpl<UserIngredientMapper, UserIngredient> implements UserIngredientService {

    @Autowired
    private IngredientService ingredientService;

    @Override
    public List<UserIngredientVO> getUserIngredientList(Long userId) {
        List<UserIngredient> list = list(new LambdaQueryWrapper<UserIngredient>()
                .eq(UserIngredient::getUserId, userId)
                .eq(UserIngredient::getStatus, 1));
        
        List<UserIngredientVO> result = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate threeDaysLater = today.plusDays(3);
        
        for (UserIngredient ui : list) {
            UserIngredientVO vo = new UserIngredientVO();
            BeanUtil.copyProperties(ui, vo);
            
            Ingredient ingredient = ingredientService.getById(ui.getIngredientId());
            if (ingredient != null) {
                vo.setIngredientName(ingredient.getName());
                vo.setIngredientCategory(ingredient.getCategory());
            }
            
            if (ui.getExpireDate() != null) {
                vo.setIsExpiring(ui.getExpireDate().isBefore(threeDaysLater) && !ui.getExpireDate().isBefore(today));
            } else {
                vo.setIsExpiring(false);
            }
            
            result.add(vo);
        }
        
        return result;
    }

    @Override
    public void addUserIngredient(UserIngredient userIngredient) {
        if (userIngredient.getPurchaseDate() != null && userIngredient.getExpireDate() == null) {
            Ingredient ingredient = ingredientService.getById(userIngredient.getIngredientId());
            if (ingredient != null && ingredient.getShelfLife() != null) {
                userIngredient.setExpireDate(userIngredient.getPurchaseDate().plusDays(ingredient.getShelfLife()));
            }
        }
        save(userIngredient);
    }

    @Override
    public void updateUserIngredient(Long id, UserIngredient userIngredient) {
        UserIngredient existing = getById(id);
        if (existing == null) {
            throw new BusinessException("食材库存不存在");
        }
        
        existing.setQuantity(userIngredient.getQuantity());
        existing.setUnit(userIngredient.getUnit());
        existing.setPurchaseDate(userIngredient.getPurchaseDate());
        existing.setExpireDate(userIngredient.getExpireDate());
        existing.setStatus(userIngredient.getStatus());
        
        updateById(existing);
    }

    @Override
    public void deleteUserIngredient(Long id, Long userId) {
        UserIngredient existing = getById(id);
        if (existing == null || !existing.getUserId().equals(userId)) {
            throw new BusinessException("食材库存不存在");
        }
        removeById(id);
    }

    @Override
    public void consumeIngredient(Long userId, Long ingredientId, BigDecimal quantity) {
        List<UserIngredient> list = list(new LambdaQueryWrapper<UserIngredient>()
                .eq(UserIngredient::getUserId, userId)
                .eq(UserIngredient::getIngredientId, ingredientId)
                .eq(UserIngredient::getStatus, 1));
        
        if (list.isEmpty()) {
            return;
        }
        
        BigDecimal remaining = quantity;
        for (UserIngredient ui : list) {
            if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }
            
            if (ui.getQuantity().compareTo(remaining) > 0) {
                ui.setQuantity(ui.getQuantity().subtract(remaining));
                updateById(ui);
                remaining = BigDecimal.ZERO;
            } else {
                remaining = remaining.subtract(ui.getQuantity());
                ui.setQuantity(BigDecimal.ZERO);
                ui.setStatus(0);
                updateById(ui);
            }
        }
    }
}

