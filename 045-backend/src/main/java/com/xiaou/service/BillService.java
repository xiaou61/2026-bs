package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.Bill;
import com.xiaou.entity.Payment;

import java.math.BigDecimal;

public interface BillService extends IService<Bill> {
    IPage<Bill> pageList(Long elderId, Integer current, Integer size, Integer status);
    void pay(Long billId, BigDecimal amount, Integer payMethod, Long operatorId);
}
