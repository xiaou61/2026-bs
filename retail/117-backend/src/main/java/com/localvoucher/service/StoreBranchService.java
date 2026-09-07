package com.localvoucher.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.localvoucher.entity.StoreBranch;
import com.localvoucher.mapper.StoreBranchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreBranchService extends BaseCrudService<StoreBranch> {
    private final StoreBranchMapper mapper;

    @Override
    protected BaseMapper<StoreBranch> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"branch_name", "branch_no", "merchant_name", "city_name"};
    }
}
