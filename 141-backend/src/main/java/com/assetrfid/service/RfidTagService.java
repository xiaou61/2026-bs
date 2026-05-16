package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.RfidTag;
import com.assetrfid.mapper.RfidTagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RfidTagService extends BaseCrudService<RfidTag> {
    private final RfidTagMapper rfidTagMapper;

    @Override
    protected BaseMapper<RfidTag> mapper() {
        return rfidTagMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"tag_no", "epc_code", "asset_name", "storage_area"};
    }
}
