package com.labconsumable.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.labconsumable.entity.SupplierProfile;
import com.labconsumable.mapper.SupplierProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierProfileService extends BaseCrudService<SupplierProfile> {
    private final SupplierProfileMapper supplierProfileMapper;

    @Override
    protected BaseMapper<SupplierProfile> mapper() {
        return supplierProfileMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"supplier_no", "supplier_name", "contact_name", "phone_number"};
    }
}
