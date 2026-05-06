package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.CommunityActivity;
import com.timebank.mapper.CommunityActivityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityActivityService extends BaseCrudService<CommunityActivity> {
    private final CommunityActivityMapper inventoryCheckMapper;

    @Override
    protected BaseMapper<CommunityActivity> mapper() {
        return inventoryCheckMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"check_no", "lab_name", "consumable_name", "book_qty"};
    }
}





