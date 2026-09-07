package com.xiaou.herbal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.herbal.entity.Ingredient;
import com.xiaou.herbal.entity.IngredientTaboo;

import java.util.List;

public interface IngredientService extends IService<Ingredient> {

    Ingredient getIngredientDetail(Long ingredientId);

    List<Ingredient> listByCategory(String category);

    List<Ingredient> searchIngredients(String keyword);

    List<IngredientTaboo> getTaboos(Long ingredientId);

    void addTaboo(Long ingredientId1, Long ingredientId2, String reason);

    List<Ingredient> getTabooedIngredients(Long ingredientId);
}
