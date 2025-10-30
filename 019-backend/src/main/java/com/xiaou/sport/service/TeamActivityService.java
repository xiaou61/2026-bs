package com.xiaou.sport.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.sport.entity.TeamActivity;

public interface TeamActivityService extends IService<TeamActivity> {
    boolean joinActivity(Long activityId, Long userId);

    boolean cancelJoin(Long activityId, Long userId);
}
