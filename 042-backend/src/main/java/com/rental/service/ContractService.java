package com.rental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rental.entity.Contract;

import java.util.Map;

public interface ContractService {

    /**
     * 创建合同（房东）
     */
    void create(Long landlordId, Contract contract);

    /**
     * 获取合同列表
     */
    IPage<Map<String, Object>> getList(Long userId, String role, int page, int size, Integer status);

    /**
     * 获取合同详情
     */
    Map<String, Object> getDetail(Long userId, Long contractId);

    /**
     * 签署合同（租客）
     */
    void sign(Long tenantId, Long contractId);

    /**
     * 终止合同
     */
    void terminate(Long userId, Long contractId);
}
