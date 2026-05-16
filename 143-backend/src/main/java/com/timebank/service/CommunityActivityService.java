package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.CommunityActivity;
import com.timebank.mapper.CommunityActivityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityActivityService extends BaseCrudService<CommunityActivity> {
    private final CommunityActivityMapper communityActivityMapper;

    @Override
    protected BaseMapper<CommunityActivity> mapper() {
        return communityActivityMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"activity_no", "project_name", "activity_theme", "activity_location"};
    }
}

