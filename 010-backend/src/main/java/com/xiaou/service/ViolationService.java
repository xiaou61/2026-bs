package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.entity.CreditLog;
import com.xiaou.entity.Violation;
import com.xiaou.mapper.CreditLogMapper;
import com.xiaou.mapper.ViolationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ViolationService {

    @Autowired
    private ViolationMapper violationMapper;
    
    @Autowired
    private CreditLogMapper creditLogMapper;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private NotificationService notificationService;

    @Transactional
    public void createViolation(Long userId, Long bookingId, String violationType, Integer deductScore) {
        Violation violation = new Violation();
        violation.setUserId(userId);
        violation.setBookingId(bookingId);
        violation.setViolationType(violationType);
        violation.setDeductScore(deductScore);
        violation.setViolationTime(LocalDateTime.now());
        violation.setCreateTime(LocalDateTime.now());
        violationMapper.insert(violation);
        
        userService.updateCreditScore(userId, -deductScore, violationType, bookingId);
        
        CreditLog creditLog = new CreditLog();
        creditLog.setUserId(userId);
        creditLog.setChangeType("VIOLATION");
        creditLog.setChangeScore(-deductScore);
        creditLog.setRelatedId(violation.getId());
        creditLog.setReason(violationType);
        creditLog.setCreateTime(LocalDateTime.now());
        creditLogMapper.insert(creditLog);
        
        String message = "违约类型：" + getViolationTypeName(violationType) + "，扣除" + deductScore + "分信用分";
        notificationService.sendNotification(userId, "违约通知", message, "VIOLATION");
    }

    public List<Violation> listMyViolations(Long userId) {
        LambdaQueryWrapper<Violation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Violation::getUserId, userId);
        wrapper.orderByDesc(Violation::getCreateTime);
        return violationMapper.selectList(wrapper);
    }

    public List<Violation> listAllViolations() {
        LambdaQueryWrapper<Violation> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Violation::getCreateTime);
        return violationMapper.selectList(wrapper);
    }

    @Transactional
    public void appeal(Long violationId, String reason, String images) {
        Violation violation = violationMapper.selectById(violationId);
        if (violation.getAppealStatus() != null) {
            throw new RuntimeException("已提交过申诉");
        }
        violation.setAppealStatus("PENDING");
        violation.setAppealReason(reason);
        violation.setAppealImages(images);
        violation.setAppealTime(LocalDateTime.now());
        violationMapper.updateById(violation);
        
        notificationService.sendNotification(violation.getUserId(), "申诉已提交", "您的违约申诉已提交，请等待管理员审核", "APPEAL");
    }

    @Transactional
    public void handleAppeal(Long violationId, Long adminId, boolean approved, String result) {
        Violation violation = violationMapper.selectById(violationId);
        if (!"PENDING".equals(violation.getAppealStatus())) {
            throw new RuntimeException("申诉状态不正确");
        }
        
        violation.setAppealStatus(approved ? "APPROVED" : "REJECTED");
        violation.setHandleAdminId(adminId);
        violation.setHandleResult(result);
        violation.setHandleTime(LocalDateTime.now());
        violationMapper.updateById(violation);
        
        if (approved) {
            userService.updateCreditScore(violation.getUserId(), violation.getDeductScore(), "申诉通过恢复", violationId);
            
            CreditLog creditLog = new CreditLog();
            creditLog.setUserId(violation.getUserId());
            creditLog.setChangeType("APPEAL_SUCCESS");
            creditLog.setChangeScore(violation.getDeductScore());
            creditLog.setRelatedId(violationId);
            creditLog.setReason("申诉通过恢复信用分");
            creditLog.setCreateTime(LocalDateTime.now());
            creditLogMapper.insert(creditLog);
        }
        
        String message = approved ? "申诉已通过，信用分已恢复" : "申诉未通过：" + result;
        notificationService.sendNotification(violation.getUserId(), "申诉处理结果", message, "APPEAL");
    }

    private String getViolationTypeName(String type) {
        return switch (type) {
            case "NO_CHECKIN" -> "预约后未签到";
            case "NO_CHECKOUT" -> "签到后未签退";
            case "LEAVE_TIMEOUT" -> "临时离开超时";
            default -> type;
        };
    }
}

