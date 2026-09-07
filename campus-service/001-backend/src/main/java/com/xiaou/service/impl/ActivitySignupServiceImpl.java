package com.xiaou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.ActivitySignup;
import com.xiaou.mapper.ActivitySignupMapper;
import com.xiaou.service.ActivitySignupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 活动报名Service实现类
 * @author xiaou
 */
@Slf4j
@Service
public class ActivitySignupServiceImpl extends ServiceImpl<ActivitySignupMapper, ActivitySignup> implements ActivitySignupService {
}
