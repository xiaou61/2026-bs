package com.psychologycare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psychologycare.entity.CounselRoom;
import com.psychologycare.mapper.CounselRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CounselRoomService extends BaseCrudService<CounselRoom> {
    private final CounselRoomMapper supplierProfileMapper;

    @Override
    protected BaseMapper<CounselRoom> mapper() {
        return supplierProfileMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"supplier_no", "supplier_name", "contact_name", "phone_number"};
    }
}







