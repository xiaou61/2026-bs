package com.rental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rental.entity.Repair;

import java.util.Map;

public interface RepairService {

    void create(Long tenantId, Repair repair);

    IPage<Map<String, Object>> getList(Long userId, String role, int page, int size, Integer status);

    void process(Long landlordId, Long repairId);

    void complete(Long landlordId, Long repairId, String result);
}
