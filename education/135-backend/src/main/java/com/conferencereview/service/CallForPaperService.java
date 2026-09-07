package com.conferencereview.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.conferencereview.entity.CallForPaper;
import com.conferencereview.mapper.CallForPaperMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CallForPaperService extends BaseCrudService<CallForPaper> {
    private final CallForPaperMapper supplierProfileMapper;

    @Override
    protected BaseMapper<CallForPaper> mapper() {
        return supplierProfileMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"supplier_no", "supplier_name", "contact_name", "phone_number"};
    }
}

