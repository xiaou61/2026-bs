package com.rental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.util.Map;

public interface AppointmentService {

    /**
     * 提交预约
     */
    void create(Long tenantId, Long houseId, LocalDateTime appointmentTime, String contactPhone, String remark);

    /**
     * 获取预约列表（根据角色返回不同数据）
     */
    IPage<Map<String, Object>> getList(Long userId, String role, int page, int size, Integer status);

    /**
     * 确认预约（房东）
     */
    void confirm(Long landlordId, Long appointmentId);

    /**
     * 拒绝预约（房东）
     */
    void reject(Long landlordId, Long appointmentId, String reason);

    /**
     * 完成预约（房东）
     */
    void complete(Long landlordId, Long appointmentId);

    /**
     * 取消预约（租客）
     */
    void cancel(Long tenantId, Long appointmentId);
}
