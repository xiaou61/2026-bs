package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.common.BusinessException;
import com.xiaou.entity.RepairRequest;
import com.xiaou.entity.User;
import com.xiaou.mapper.RepairRequestMapper;
import com.xiaou.service.RepairRequestService;
import com.xiaou.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 报修申请Service实现类
 * @author xiaou
 */
@Slf4j
@Service
public class RepairRequestServiceImpl extends ServiceImpl<RepairRequestMapper, RepairRequest> implements RepairRequestService {
    
    @Autowired
    private UserService userService;
    
    @Override
    public void submitRepairRequest(RepairRequest repairRequest) {
        // 设置状态为未处理
        repairRequest.setStatus(0);
        save(repairRequest);
    }
    
    @Override
    public void handleRepairRequest(Long id, Long handlerId, Integer status, String comment) {
        RepairRequest repairRequest = getById(id);
        if (repairRequest == null) {
            throw new BusinessException("报修申请不存在");
        }
        
        repairRequest.setHandlerId(handlerId);
        repairRequest.setStatus(status);
        repairRequest.setHandleComment(comment);
        repairRequest.setHandleTime(LocalDateTime.now());
        
        updateById(repairRequest);
    }
    
    @Override
    public Page<RepairRequest> getRepairRequestPage(int pageNum, int pageSize, Long studentId, Integer status) {
        Page<RepairRequest> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<RepairRequest> wrapper = new LambdaQueryWrapper<>();
        
        if (studentId != null) {
            wrapper.eq(RepairRequest::getStudentId, studentId);
        }
        
        if (status != null) {
            wrapper.eq(RepairRequest::getStatus, status);
        }
        
        wrapper.orderByDesc(RepairRequest::getCreateTime);
        Page<RepairRequest> resultPage = page(page, wrapper);
        
        // 填充学生姓名和处理人姓名
        for (RepairRequest record : resultPage.getRecords()) {
            User student = userService.getById(record.getStudentId());
            if (student != null) {
                record.setStudentName(student.getRealName() != null ? student.getRealName() : student.getUsername());
            }
            
            if (record.getHandlerId() != null) {
                User handler = userService.getById(record.getHandlerId());
                if (handler != null) {
                    record.setHandlerName(handler.getRealName() != null ? handler.getRealName() : handler.getUsername());
                }
            }
        }
        
        return resultPage;
    }
    
    @Override
    public Object getRepairStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        // 统计各状态的报修数量
        LambdaQueryWrapper<RepairRequest> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RepairRequest::getStatus, 0);
        long unhandledCount = count(wrapper);
        
        wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RepairRequest::getStatus, 1);
        long handlingCount = count(wrapper);
        
        wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RepairRequest::getStatus, 2);
        long completedCount = count(wrapper);
        
        statistics.put("unhandledCount", unhandledCount);
        statistics.put("handlingCount", handlingCount);
        statistics.put("completedCount", completedCount);
        statistics.put("totalCount", unhandledCount + handlingCount + completedCount);
        
        return statistics;
    }
}

