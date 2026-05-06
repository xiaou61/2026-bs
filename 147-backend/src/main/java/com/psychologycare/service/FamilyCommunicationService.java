package com.psychologycare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psychologycare.entity.FamilyCommunication;
import com.psychologycare.mapper.FamilyCommunicationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FamilyCommunicationService extends BaseCrudService<FamilyCommunication> {
    private final FamilyCommunicationMapper inventoryCheckMapper;

    @Override
    protected BaseMapper<FamilyCommunication> mapper() {
        return inventoryCheckMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"check_no", "lab_name", "consumable_name", "book_qty"};
    }
}







