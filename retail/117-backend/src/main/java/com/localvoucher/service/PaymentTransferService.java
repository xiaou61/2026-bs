package com.localvoucher.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.localvoucher.entity.PaymentTransfer;
import com.localvoucher.mapper.PaymentTransferMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentTransferService extends BaseCrudService<PaymentTransfer> {
    private final PaymentTransferMapper mapper;

    @Override
    protected BaseMapper<PaymentTransfer> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"transfer_no", "settlement_no", "merchant_name", "bank_account"};
    }
}
