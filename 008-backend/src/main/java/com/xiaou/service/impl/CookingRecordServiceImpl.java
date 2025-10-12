package com.xiaou.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.dto.CookingRecordDTO;
import com.xiaou.entity.CookingRecord;
import com.xiaou.entity.Recipe;
import com.xiaou.entity.RecipeIngredient;
import com.xiaou.exception.BusinessException;
import com.xiaou.mapper.CookingRecordMapper;
import com.xiaou.service.CookingRecordService;
import com.xiaou.service.RecipeIngredientService;
import com.xiaou.service.RecipeService;
import com.xiaou.service.UserIngredientService;
import com.xiaou.vo.CookingRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CookingRecordServiceImpl extends ServiceImpl<CookingRecordMapper, CookingRecord> implements CookingRecordService {

    @Autowired
    private RecipeService recipeService;
    
    @Autowired
    private RecipeIngredientService recipeIngredientService;
    
    @Autowired
    private UserIngredientService userIngredientService;

    @Override
    @Transactional
    public void createRecord(Long userId, CookingRecordDTO dto) {
        Recipe recipe = recipeService.getById(dto.getRecipeId());
        if (recipe == null) {
            throw new BusinessException("菜谱不存在");
        }
        
        CookingRecord record = new CookingRecord();
        BeanUtil.copyProperties(dto, record);
        record.setUserId(userId);
        save(record);
        
        List<RecipeIngredient> ingredients = recipeIngredientService.list(
                new LambdaQueryWrapper<RecipeIngredient>()
                        .eq(RecipeIngredient::getRecipeId, dto.getRecipeId())
                        .eq(RecipeIngredient::getIsRequired, 1));
        
        for (RecipeIngredient ingredient : ingredients) {
            userIngredientService.consumeIngredient(userId, ingredient.getIngredientId(), ingredient.getQuantity());
        }
    }

    @Override
    public List<CookingRecordVO> getRecordList(Long userId) {
        List<CookingRecord> records = list(new LambdaQueryWrapper<CookingRecord>()
                .eq(CookingRecord::getUserId, userId)
                .orderByDesc(CookingRecord::getCreateTime));
        
        List<CookingRecordVO> result = new ArrayList<>();
        for (CookingRecord record : records) {
            CookingRecordVO vo = new CookingRecordVO();
            BeanUtil.copyProperties(record, vo);
            
            Recipe recipe = recipeService.getById(record.getRecipeId());
            if (recipe != null) {
                vo.setRecipeName(recipe.getName());
                vo.setRecipeImage(recipe.getImage());
            }
            
            result.add(vo);
        }
        
        return result;
    }

    @Override
    public Map<String, Object> getStats(Long userId) {
        Long totalCount = count(new LambdaQueryWrapper<CookingRecord>()
                .eq(CookingRecord::getUserId, userId));
        
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        Long weekCount = count(new LambdaQueryWrapper<CookingRecord>()
                .eq(CookingRecord::getUserId, userId)
                .ge(CookingRecord::getCreateTime, sevenDaysAgo));
        
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        Long monthCount = count(new LambdaQueryWrapper<CookingRecord>()
                .eq(CookingRecord::getUserId, userId)
                .ge(CookingRecord::getCreateTime, thirtyDaysAgo));
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalCount", totalCount);
        stats.put("weekCount", weekCount);
        stats.put("monthCount", monthCount);
        
        return stats;
    }
}

