package com.xiaou.herbal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.herbal.entity.CreatorAuth;

import java.util.List;

public interface CreatorAuthService extends IService<CreatorAuth> {

    void applyAuth(Long userId, String authType, String credentials);

    List<CreatorAuth> getPendingAuths();

    void approveAuth(Long authId);

    void rejectAuth(Long authId);

    CreatorAuth getUserAuth(Long userId);
}
