package com.rental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rental.entity.Bill;
import com.rental.entity.Contract;

import java.util.Map;

public interface BillService {

    /**
     * 生成首期账单
     */
    void generateFirstBill(Contract contract);

    /**
     * 获取账单列表
     */
    IPage<Map<String, Object>> getList(Long userId, String role, int page, int size, Integer status);

    /**
     * 支付账单
     */
    void pay(Long tenantId, Long billId);

    /**
     * 获取账单统计
     */
    Map<String, Object> getStatistics(Long userId, String role);
}
