package com.xiaou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.RecipeStep;
import com.xiaou.mapper.RecipeStepMapper;
import com.xiaou.service.RecipeStepService;
import org.springframework.stereotype.Service;

@Service
public class RecipeStepServiceImpl extends ServiceImpl<RecipeStepMapper, RecipeStep> implements RecipeStepService {
}

