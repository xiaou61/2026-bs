package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.CompetencyProfile;
import com.learningpath.mapper.CompetencyProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompetencyProfileService extends BaseCrudService<CompetencyProfile> {
    private final CompetencyProfileMapper inventoryCheckMapper;

    @Override
    protected BaseMapper<CompetencyProfile> mapper() {
        return inventoryCheckMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"check_no", "lab_name", "consumable_name", "book_qty"};
    }
}



