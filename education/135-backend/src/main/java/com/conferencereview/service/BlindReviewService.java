package com.conferencereview.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.conferencereview.entity.BlindReview;
import com.conferencereview.mapper.BlindReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlindReviewService extends BaseCrudService<BlindReview> {
    private final BlindReviewMapper purchaseOrderMapper;

    @Override
    protected BaseMapper<BlindReview> mapper() {
        return purchaseOrderMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"order_no", "supplier_name", "consumable_name", "order_amount"};
    }
}

