package com.innovationhub.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.innovationhub.entity.MilestoneTask;
import com.innovationhub.mapper.MilestoneTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MilestoneTaskService extends BaseCrudService<MilestoneTask> {
    private final MilestoneTaskMapper inventoryCheckMapper;

    @Override
    protected BaseMapper<MilestoneTask> mapper() {
        return inventoryCheckMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"check_no", "lab_name", "consumable_name", "book_qty"};
    }
}


