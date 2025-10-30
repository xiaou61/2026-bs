package com.xiaou.sport.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.sport.entity.FitnessPlan;
import com.xiaou.sport.mapper.FitnessPlanMapper;
import com.xiaou.sport.service.FitnessPlanService;
import org.springframework.stereotype.Service;

@Service
public class FitnessPlanServiceImpl extends ServiceImpl<FitnessPlanMapper, FitnessPlan> implements FitnessPlanService {
}
