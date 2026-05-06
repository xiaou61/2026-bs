package com.conferencereview.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.conferencereview.entity.PaperSubmission;
import com.conferencereview.mapper.PaperSubmissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaperSubmissionService extends BaseCrudService<PaperSubmission> {
    private final PaperSubmissionMapper stockItemMapper;

    @Override
    protected BaseMapper<PaperSubmission> mapper() {
        return stockItemMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"stock_no", "consumable_name", "lab_name", "current_qty"};
    }
}

