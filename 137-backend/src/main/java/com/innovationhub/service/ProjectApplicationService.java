package com.innovationhub.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.innovationhub.entity.ProjectApplication;
import com.innovationhub.mapper.ProjectApplicationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectApplicationService extends BaseCrudService<ProjectApplication> {
    private final ProjectApplicationMapper stockItemMapper;

    @Override
    protected BaseMapper<ProjectApplication> mapper() {
        return stockItemMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"stock_no", "consumable_name", "lab_name", "current_qty"};
    }
}


