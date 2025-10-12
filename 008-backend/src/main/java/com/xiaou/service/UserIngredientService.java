package com.xiaou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.UserIngredient;
import com.xiaou.vo.UserIngredientVO;

import java.util.List;

public interface UserIngredientService extends IService<UserIngredient> {
    
    List<UserIngredientVO> getUserIngredientList(Long userId);
    
    void addUserIngredient(UserIngredient userIngredient);
    
    void updateUserIngredient(Long id, UserIngredient userIngredient);
    
    void deleteUserIngredient(Long id, Long userId);
    
    void consumeIngredient(Long userId, Long ingredientId, java.math.BigDecimal quantity);
}

