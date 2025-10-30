package com.xiaou.sport.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.sport.entity.ActivityParticipant;
import com.xiaou.sport.mapper.ActivityParticipantMapper;
import com.xiaou.sport.service.ActivityParticipantService;
import org.springframework.stereotype.Service;

@Service
public class ActivityParticipantServiceImpl extends ServiceImpl<ActivityParticipantMapper, ActivityParticipant>
        implements ActivityParticipantService {
}
