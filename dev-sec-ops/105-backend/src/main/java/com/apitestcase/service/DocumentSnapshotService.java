package com.apitestcase.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.apitestcase.entity.DocumentSnapshot;
import com.apitestcase.mapper.DocumentSnapshotMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentSnapshotService extends BaseCrudService<DocumentSnapshot> {
    private final DocumentSnapshotMapper mapper;

    @Override
    protected BaseMapper<DocumentSnapshot> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"snapshot_no", "title", "version_no", "content_summary"};
    }


}
