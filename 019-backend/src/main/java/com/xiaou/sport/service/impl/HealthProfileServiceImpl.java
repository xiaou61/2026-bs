package com.xiaou.sport.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.sport.entity.HealthProfile;
import com.xiaou.sport.mapper.HealthProfileMapper;
import com.xiaou.sport.service.HealthProfileService;
import org.springframework.stereotype.Service;

@Service
public class HealthProfileServiceImpl extends ServiceImpl<HealthProfileMapper, HealthProfile>
        implements HealthProfileService {
}
