package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.Provider;
import com.xiaou.entity.ProviderServiceEntity;

import java.util.List;

public interface ProviderService extends IService<Provider> {
    IPage<Provider> page(Integer current, Integer size, String keyword);
    Provider detail(Long id);
    List<ProviderServiceEntity> listServices(Long providerId);
}
