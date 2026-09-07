package com.servicequality.service;

import com.servicequality.common.BusinessException;
import com.servicequality.entity.QualityTask;
import com.servicequality.mapper.QualityTaskMapper;
import com.servicequality.entity.QualityResult;
import com.servicequality.entity.WorkOrder;
import com.servicequality.mapper.QualityResultMapper;
import com.servicequality.mapper.WorkOrderMapper;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class QualityTaskService extends ServiceImpl<QualityTaskMapper, QualityTask> {
    public Page<QualityTask> page(Integer pageNum, Integer pageSize, String keyword, Long orderId, Integer status, String priority) {
        LambdaQueryWrapper<QualityTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(QualityTask::getTaskNo, keyword).or().like(QualityTask::getTaskName, keyword));
        wrapper.eq(orderId != null, QualityTask::getOrderId, orderId);
        wrapper.eq(status != null, QualityTask::getStatus, status);
        wrapper.eq(StringUtils.hasText(priority), QualityTask::getPriority, priority);
        wrapper.orderByDesc(QualityTask::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(QualityTask entity) {
        if (entity.getId() == null) {
            entity.setTaskNo(entity.getTaskNo() == null ? "QT" + System.currentTimeMillis() : entity.getTaskNo());
            entity.setStatus(entity.getStatus() == null ? 0 : entity.getStatus());
            entity.setCreateTime(LocalDateTime.now());
        }
        
        saveOrUpdate(entity);
    }

    @Autowired
    private QualityResultMapper qualityResultMapper;

    @Autowired
    private WorkOrderMapper workOrderMapper;

    public void run(Long id) {
        QualityTask task = getById(id);
        if (task == null) {
            throw new BusinessException(400, "质检任务不存在");
        }
        WorkOrder order = workOrderMapper.selectById(task.getOrderId());
        if (order == null) {
            throw new BusinessException(400, "工单不存在");
        }
        QualityResult result = new QualityResult();
        result.setTaskId(id);
        result.setOrderId(order.getId());
        result.setAgentId(order.getAgentId());
        BigDecimal score = new BigDecimal("92.00");
        String content = order.getContent() == null ? "" : order.getContent();
        if (content.contains("投诉") || content.contains("退款")) {
            score = new BigDecimal("78.00");
        }
        result.setScore(score);
        result.setRiskLevel(score.compareTo(new BigDecimal("85")) >= 0 ? "低" : "中");
        result.setHitRules(score.compareTo(new BigDecimal("85")) >= 0 ? "流程完整" : "高风险关键词");
        result.setSuggestion(score.compareTo(new BigDecimal("85")) >= 0 ? "服务过程规范，可归档" : "建议主管复盘并补充安抚话术");
        result.setReviewStatus(0);
        result.setCreateTime(LocalDateTime.now());
        result.setUpdateTime(LocalDateTime.now());
        qualityResultMapper.insert(result);
        task.setStatus(1);
        updateById(task);
    }

    public void finish(Long id) {
        QualityTask task = getById(id);
        if (task == null) {
            throw new BusinessException(400, "质检任务不存在");
        }
        task.setStatus(2);
        task.setFinishTime(LocalDateTime.now());
        updateById(task);
    }

    public void reject(Long id) {
        QualityTask task = getById(id);
        if (task == null) {
            throw new BusinessException(400, "质检任务不存在");
        }
        task.setStatus(3);
        updateById(task);
    }

}
