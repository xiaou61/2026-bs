package com.psychologycare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psychologycare.entity.FamilyCommunication;
import com.psychologycare.mapper.FamilyCommunicationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FamilyCommunicationService extends BaseCrudService<FamilyCommunication> {
    private final FamilyCommunicationMapper familyCommunicationMapper;

    @Override
    protected BaseMapper<FamilyCommunication> mapper() {
        return familyCommunicationMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"communication_no", "case_theme", "communication_topic", "communication_method"};
    }
}







