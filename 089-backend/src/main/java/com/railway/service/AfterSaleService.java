package com.railway.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.railway.common.BusinessException;
import com.railway.common.PageResult;
import com.railway.dto.AfterSaleApplyDTO;
import com.railway.entity.AfterSaleRecord;
import com.railway.entity.Schedule;
import com.railway.entity.TicketOrder;
import com.railway.mapper.AfterSaleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class AfterSaleService {

    @Resource
    private AfterSaleMapper afterSaleMapper;

    @Resource
    private OrderService orderService;

    @Resource
    private ScheduleService scheduleService;

    @Resource
    private TrainService trainService;

    @Resource
    private UserService userService;

    public PageResult<AfterSaleRecord> page(Integer pageNum, Integer pageSize, String serviceType, String reviewStatus, Long userId, String role) {
        Page<AfterSaleRecord> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<AfterSaleRecord> wrapper = new LambdaQueryWrapper<AfterSaleRecord>()
                .eq(StringUtils.hasText(serviceType), AfterSaleRecord::getServiceType, trim(serviceType))
                .eq(StringUtils.hasText(reviewStatus), AfterSaleRecord::getReviewStatus, trim(reviewStatus))
                .eq("USER".equalsIgnoreCase(role), AfterSaleRecord::getUserId, userId)
                .orderByDesc(AfterSaleRecord::getId);
        Page<AfterSaleRecord> resultPage = afterSaleMapper.selectPage(page, wrapper);
        PageResult<AfterSaleRecord> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public void apply(Long userId, AfterSaleApplyDTO dto) {
        if (dto == null || dto.getOrderId() == null || !StringUtils.hasText(dto.getServiceType())) {
            throw new BusinessException("售后参数不完整");
        }
        TicketOrder order = orderService.getById(dto.getOrderId(), userId, "USER");
        if (!OrderService.STATUS_PAID.equals(order.getStatus()) && !OrderService.STATUS_FINISHED.equals(order.getStatus())) {
            throw new BusinessException("当前订单不可申请退改签");
        }
        AfterSaleRecord exist = afterSaleMapper.selectOne(new LambdaQueryWrapper<AfterSaleRecord>()
                .eq(AfterSaleRecord::getOrderId, dto.getOrderId())
                .eq(AfterSaleRecord::getReviewStatus, "PENDING")
                .last("limit 1"));
        if (exist != null) {
            throw new BusinessException("该订单已有待处理售后申请");
        }
        AfterSaleRecord record = new AfterSaleRecord();
        record.setOrderId(order.getId());
        record.setUserId(userId);
        record.setServiceType(dto.getServiceType().trim().toUpperCase());
        record.setReason(StringUtils.hasText(dto.getReason()) ? dto.getReason().trim() : "");
        record.setReviewStatus("PENDING");
        if ("CHANGE".equalsIgnoreCase(record.getServiceType())) {
            if (dto.getTargetScheduleId() == null) {
                throw new BusinessException("改签目标班次不能为空");
            }
            Schedule schedule = scheduleService.getById(dto.getTargetScheduleId());
            record.setTargetScheduleId(dto.getTargetScheduleId());
            record.setTargetScheduleInfo(trainService.getById(schedule.getTrainId()).getTrainCode() + " " + schedule.getDepartureTime());
        }
        afterSaleMapper.insert(record);
    }

    @Transactional(rollbackFor = Exception.class)
    public void review(Long id, String reviewStatus, String reviewRemark) {
        AfterSaleRecord record = afterSaleMapper.selectById(id);
        if (record == null) {
            throw new BusinessException("售后记录不存在");
        }
        if (!"PENDING".equals(record.getReviewStatus())) {
            throw new BusinessException("当前记录已处理");
        }
        String status = trim(reviewStatus);
        if (!"APPROVED".equalsIgnoreCase(status) && !"REJECTED".equalsIgnoreCase(status)) {
            throw new BusinessException("审核状态不合法");
        }
        record.setReviewStatus(status.toUpperCase());
        record.setReviewRemark(StringUtils.hasText(reviewRemark) ? reviewRemark.trim() : "");
        record.setReviewTime(LocalDateTime.now());
        if ("APPROVED".equalsIgnoreCase(status)) {
            if ("REFUND".equalsIgnoreCase(record.getServiceType())) {
                TicketOrder order = orderService.refundBySystem(record.getOrderId());
                userService.changeBalance(order.getUserId(), order.getPayAmount());
            } else if ("CHANGE".equalsIgnoreCase(record.getServiceType())) {
                orderService.markChanged(record.getOrderId());
            }
        }
        afterSaleMapper.updateById(record);
    }

    private String trim(String value) {
        return value == null ? null : value.trim();
    }
}
