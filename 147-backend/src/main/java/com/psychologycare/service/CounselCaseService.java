package com.psychologycare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psychologycare.entity.CounselCase;
import com.psychologycare.mapper.CounselCaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CounselCaseService extends BaseCrudService<CounselCase> {
    private final CounselCaseMapper counselCaseMapper;

    @Override
    protected BaseMapper<CounselCase> mapper() {
        return counselCaseMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"case_no", "case_theme", "issue_type", "college_name"};
    }
}







