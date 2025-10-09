package com.xiaou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.RepairRequest;

/**
 * 报修申请Service接口
 * @author xiaou
 */
public interface RepairRequestService extends IService<RepairRequest> {
    
    /**
     * 学生提交报修申请
     */
    void submitRepairRequest(RepairRequest repairRequest);
    
    /**
     * 处理报修申请
     */
    void handleRepairRequest(Long id, Long handlerId, Integer status, String comment);
    
    /**
     * 分页查询报修申请
     */
    Page<RepairRequest> getRepairRequestPage(int pageNum, int pageSize, Long studentId, Integer status);
    
    /**
     * 获取报修统计数据
     */
    Object getRepairStatistics();
}

