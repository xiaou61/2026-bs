package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Provider;
import com.xiaou.entity.ProviderServiceEntity;
import com.xiaou.mapper.ProviderMapper;
import com.xiaou.mapper.ProviderServiceMapper;
import com.xiaou.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl extends ServiceImpl<ProviderMapper, Provider> implements ProviderService {

    private final ProviderServiceMapper providerServiceMapper;

    @Override
    public IPage<Provider> page(Integer current, Integer size, String keyword) {
        LambdaQueryWrapper<Provider> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Provider::getStatus, 1);
        if (StringUtils.hasText(keyword)) {
            wrapper.like(Provider::getName, keyword).or().like(Provider::getAddress, keyword);
        }
        wrapper.orderByDesc(Provider::getRating);
        return page(new Page<>(current, size), wrapper);
    }

    @Override
    public Provider detail(Long id) {
        return getById(id);
    }

    @Override
    public List<ProviderServiceEntity> listServices(Long providerId) {
        return providerServiceMapper.selectList(
                new LambdaQueryWrapper<ProviderServiceEntity>()
                        .eq(ProviderServiceEntity::getProviderId, providerId)
                        .eq(ProviderServiceEntity::getStatus, 1));
    }
}
