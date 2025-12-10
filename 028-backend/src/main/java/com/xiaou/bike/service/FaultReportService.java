package com.xiaou.bike.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.bike.dto.FaultReportDTO;
import com.xiaou.bike.entity.Bicycle;
import com.xiaou.bike.entity.CreditRecord;
import com.xiaou.bike.entity.FaultReport;
import com.xiaou.bike.entity.User;
import com.xiaou.bike.mapper.BicycleMapper;
import com.xiaou.bike.mapper.CreditRecordMapper;
import com.xiaou.bike.mapper.FaultReportMapper;
import com.xiaou.bike.mapper.UserMapper;
import com.xiaou.bike.util.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 故障上报服务
 */
@Service
@RequiredArgsConstructor
public class FaultReportService {

    private final FaultReportMapper faultReportMapper;
    private final BicycleMapper bicycleMapper;
    private final UserMapper userMapper;
    private final CreditRecordMapper creditRecordMapper;

    @Value("${credit.report-fault:5}")
    private Integer reportFaultCredit;

    /**
     * 上报故障
     */
    @Transactional
    public void report(FaultReportDTO dto) {
        Long userId = UserContext.getUserId();

        FaultReport report = new FaultReport();
        report.setBikeId(dto.getBikeId());
        report.setUserId(userId);
        report.setFaultType(dto.getFaultType());
        report.setDescription(dto.getDescription());
        report.setImages(dto.getImages());
        report.setStatus(0);
        faultReportMapper.insert(report);

        // 增加信用分
        addCredit(userId, reportFaultCredit, 3, "上报故障");
    }

    /**
     * 增加信用分
     */
    private void addCredit(Long userId, Integer value, Integer type, String reason) {
        User user = userMapper.selectById(userId);
        int newScore = user.getCreditScore() + value;

        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setCreditScore(newScore);
        userMapper.updateById(updateUser);

        CreditRecord record = new CreditRecord();
        record.setUserId(userId);
        record.setChangeValue(value);
        record.setChangeType(type);
        record.setReason(reason);
        record.setScoreAfter(newScore);
        creditRecordMapper.insert(record);
    }

    /**
     * 获取用户上报记录
     */
    public Page<FaultReport> getUserReports(Integer page, Integer size) {
        Long userId = UserContext.getUserId();
        Page<FaultReport> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<FaultReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FaultReport::getUserId, userId);
        wrapper.orderByDesc(FaultReport::getCreateTime);
        return faultReportMapper.selectPage(pageParam, wrapper);
    }

    /**
     * 分页查询故障（管理端）
     */
    public Page<FaultReport> listFaults(Integer page, Integer size, Integer status, Long bikeId) {
        Page<FaultReport> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<FaultReport> wrapper = new LambdaQueryWrapper<>();

        if (status != null) {
            wrapper.eq(FaultReport::getStatus, status);
        }

        if (bikeId != null) {
            wrapper.eq(FaultReport::getBikeId, bikeId);
        }

        wrapper.orderByDesc(FaultReport::getCreateTime);
        return faultReportMapper.selectPage(pageParam, wrapper);
    }

    /**
     * 处理故障
     */
    @Transactional
    public void handleFault(Long id, Integer status, String handleResult) {
        Long adminId = UserContext.getUserId();

        FaultReport report = new FaultReport();
        report.setId(id);
        report.setStatus(status);
        report.setHandlerId(adminId);
        report.setHandleTime(LocalDateTime.now());
        report.setHandleResult(handleResult);
        faultReportMapper.updateById(report);

        // 如果标记为已完成，更新车辆状态
        if (status == 2) {
            FaultReport fault = faultReportMapper.selectById(id);
            Bicycle bicycle = new Bicycle();
            bicycle.setId(fault.getBikeId());
            bicycle.setStatus(0); // 恢复为空闲
            bicycle.setLastMaintenance(LocalDateTime.now());
            bicycleMapper.updateById(bicycle);
        }
    }

    /**
     * 统计待处理故障数量
     */
    public long countPending() {
        LambdaQueryWrapper<FaultReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FaultReport::getStatus, 0);
        return faultReportMapper.selectCount(wrapper);
    }
}
