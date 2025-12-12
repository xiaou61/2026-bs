package com.xiaou.herbal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.herbal.entity.HealthProfile;

public interface HealthProfileService extends IService<HealthProfile> {

    HealthProfile getUserProfile(Long userId);

    void saveOrUpdateProfile(Long userId, HealthProfile profile);
}
