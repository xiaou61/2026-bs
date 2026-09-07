package com.xiaou.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.dto.ShoppingGenerateDTO;
import com.xiaou.entity.*;
import com.xiaou.exception.BusinessException;
import com.xiaou.mapper.ShoppingListMapper;
import com.xiaou.service.*;
import com.xiaou.vo.ShoppingListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShoppingListServiceImpl extends ServiceImpl<ShoppingListMapper, ShoppingList> implements ShoppingListService {

    @Autowired
    private RecipeIngredientService recipeIngredientService;
    
    @Autowired
    private UserIngredientService userIngredientService;
    
    @Autowired
    private IngredientService ingredientService;

    @Override
    @Transactional
    public void generateShoppingList(Long userId, ShoppingGenerateDTO dto) {
        Map<Long, BigDecimal> neededMap = new HashMap<>();
        
        for (Long recipeId : dto.getRecipeIds()) {
            List<RecipeIngredient> ingredients = recipeIngredientService.list(
                    new LambdaQueryWrapper<RecipeIngredient>()
                            .eq(RecipeIngredient::getRecipeId, recipeId)
                            .eq(RecipeIngredient::getIsRequired, 1));
            
            for (RecipeIngredient ingredient : ingredients) {
                Long ingredientId = ingredient.getIngredientId();
                BigDecimal quantity = neededMap.getOrDefault(ingredientId, BigDecimal.ZERO);
                neededMap.put(ingredientId, quantity.add(ingredient.getQuantity()));
            }
        }
        
        List<UserIngredient> userIngredients = userIngredientService.list(
                new LambdaQueryWrapper<UserIngredient>()
                        .eq(UserIngredient::getUserId, userId)
                        .eq(UserIngredient::getStatus, 1));
        
        Map<Long, BigDecimal> ownedMap = new HashMap<>();
        for (UserIngredient ui : userIngredients) {
            Long ingredientId = ui.getIngredientId();
            BigDecimal quantity = ownedMap.getOrDefault(ingredientId, BigDecimal.ZERO);
            ownedMap.put(ingredientId, quantity.add(ui.getQuantity()));
        }
        
        for (Map.Entry<Long, BigDecimal> entry : neededMap.entrySet()) {
            Long ingredientId = entry.getKey();
            BigDecimal needed = entry.getValue();
            BigDecimal owned = ownedMap.getOrDefault(ingredientId, BigDecimal.ZERO);
            
            if (needed.compareTo(owned) > 0) {
                BigDecimal toBuy = needed.subtract(owned);
                
                Ingredient ingredient = ingredientService.getById(ingredientId);
                if (ingredient != null) {
                    ShoppingList item = new ShoppingList();
                    item.setUserId(userId);
                    item.setIngredientId(ingredientId);
                    item.setQuantity(toBuy);
                    item.setUnit(ingredient.getUnit());
                    item.setStatus(0);
                    save(item);
                }
            }
        }
    }

    @Override
    public List<ShoppingListVO> getShoppingList(Long userId) {
        List<ShoppingList> list = list(new LambdaQueryWrapper<ShoppingList>()
                .eq(ShoppingList::getUserId, userId)
                .orderByAsc(ShoppingList::getStatus)
                .orderByDesc(ShoppingList::getCreateTime));
        
        List<ShoppingListVO> result = new ArrayList<>();
        for (ShoppingList item : list) {
            ShoppingListVO vo = new ShoppingListVO();
            BeanUtil.copyProperties(item, vo);
            
            Ingredient ingredient = ingredientService.getById(item.getIngredientId());
            if (ingredient != null) {
                vo.setIngredientName(ingredient.getName());
            }
            
            result.add(vo);
        }
        
        return result;
    }

    @Override
    public void checkItem(Long id, Long userId) {
        ShoppingList item = getById(id);
        if (item == null || !item.getUserId().equals(userId)) {
            throw new BusinessException("购物清单项不存在");
        }
        
        item.setStatus(item.getStatus() == 0 ? 1 : 0);
        updateById(item);
    }

    @Override
    @Transactional
    public void addToStock(Long userId) {
        List<ShoppingList> checkedItems = list(new LambdaQueryWrapper<ShoppingList>()
                .eq(ShoppingList::getUserId, userId)
                .eq(ShoppingList::getStatus, 1));
        
        for (ShoppingList item : checkedItems) {
            UserIngredient ui = new UserIngredient();
            ui.setUserId(userId);
            ui.setIngredientId(item.getIngredientId());
            ui.setQuantity(item.getQuantity());
            ui.setUnit(item.getUnit());
            ui.setPurchaseDate(LocalDate.now());
            ui.setStatus(1);
            
            userIngredientService.addUserIngredient(ui);
        }
        
        remove(new LambdaQueryWrapper<ShoppingList>()
                .eq(ShoppingList::getUserId, userId)
                .eq(ShoppingList::getStatus, 1));
    }

    @Override
    public void clearList(Long userId) {
        remove(new LambdaQueryWrapper<ShoppingList>()
                .eq(ShoppingList::getUserId, userId));
    }
}

