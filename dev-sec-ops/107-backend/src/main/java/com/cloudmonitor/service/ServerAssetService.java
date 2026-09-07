package com.cloudmonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudmonitor.entity.ServerAsset;
import com.cloudmonitor.mapper.ServerAssetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServerAssetService extends BaseCrudService<ServerAsset> {
    private final ServerAssetMapper mapper;

    @Override
    protected BaseMapper<ServerAsset> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"server_name", "instance_id", "public_ip", "private_ip"};
    }


}
