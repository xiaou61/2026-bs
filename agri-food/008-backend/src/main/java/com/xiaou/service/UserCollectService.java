package com.xiaou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.Recipe;
import com.xiaou.entity.UserCollect;

import java.util.List;

public interface UserCollectService extends IService<UserCollect> {
    
    void collectRecipe(Long userId, Long recipeId);
    
    void cancelCollect(Long userId, Long recipeId);
    
    List<Recipe> getCollectList(Long userId);
}

