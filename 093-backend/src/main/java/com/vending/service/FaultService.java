package com.vending.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vending.common.BusinessException;
import com.vending.common.PageResult;
import com.vending.entity.FaultReport;
import com.vending.entity.SaleOrder;
import com.vending.entity.User;
import com.vending.entity.VendingMachine;
import com.vending.mapper.FaultReportMapper;
import com.vending.mapper.SaleOrderMapper;
import com.vending.mapper.UserMapper;
import com.vending.mapper.VendingMachineMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FaultService {

    @Resource
    private FaultReportMapper faultMapper;

    @Resource
    private SaleOrderMapper orderMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private VendingMachineMapper machineMapper;

    public PageResult<FaultReport> page(Integer pageNum, Integer pageSize, String handleStatus, Long userId, String role) {
        Page<FaultReport> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<FaultReport> wrapper = new LambdaQueryWrapper<FaultReport>()
                .eq(handleStatus != null && !handleStatus.trim().isEmpty(), FaultReport::getHandleStatus, handleStatus == null ? null : handleStatus.trim().toUpperCase())
                .eq("CUSTOMER".equalsIgnoreCase(role), FaultReport::getUserId, userId)
                .orderByDesc(FaultReport::getId);
        Page<FaultReport> resultPage = faultMapper.selectPage(page, wrapper);
        fillDetail(resultPage.getRecords());
        PageResult<FaultReport> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public void add(Long userId, FaultReport fault) {
        if (fault == null || !StringUtils.hasText(fault.getContent())) {
            throw new BusinessException("反馈内容不能为空");
        }
        if (fault.getOrderId() != null) {
            SaleOrder order = orderMapper.selectById(fault.getOrderId());
            if (order == null || !order.getUserId().equals(userId)) {
                throw new BusinessException("订单不存在");
            }
        }
        fault.setUserId(userId);
        fault.setHandleStatus("PENDING");
        faultMapper.insert(fault);
    }

    public void handle(Long id, Long handlerId, String handleStatus, String handleResult) {
        FaultReport fault = faultMapper.selectById(id);
        if (fault == null) {
            throw new BusinessException("反馈不存在");
        }
        fault.setHandleStatus(StringUtils.hasText(handleStatus) ? handleStatus.trim().toUpperCase() : "DONE");
        fault.setHandleResult(handleResult);
        fault.setHandlerId(handlerId);
        fault.setHandleTime(LocalDateTime.now());
        faultMapper.updateById(fault);
    }

    public void deleteById(Long id) {
        faultMapper.deleteById(id);
    }

    private void fillDetail(List<FaultReport> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Map<Long, String> orderMap = orderMapper.selectList(new LambdaQueryWrapper<SaleOrder>()
                        .in(SaleOrder::getId, list.stream().map(FaultReport::getOrderId).filter(java.util.Objects::nonNull).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(SaleOrder::getId, SaleOrder::getOrderNo, (a, b) -> a, HashMap::new));
        Map<Long, String> userMap = userMapper.selectList(new LambdaQueryWrapper<User>()
                        .in(User::getId, list.stream().flatMap(item -> java.util.stream.Stream.of(item.getUserId(), item.getHandlerId())).filter(java.util.Objects::nonNull).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(User::getId, User::getNickname, (a, b) -> a, HashMap::new));
        Map<Long, String> machineMap = machineMapper.selectList(new LambdaQueryWrapper<VendingMachine>()
                        .in(VendingMachine::getId, list.stream().map(FaultReport::getMachineId).filter(java.util.Objects::nonNull).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(VendingMachine::getId, VendingMachine::getName, (a, b) -> a, HashMap::new));
        list.forEach(item -> {
            item.setOrderNo(item.getOrderId() == null ? null : orderMap.get(item.getOrderId()));
            item.setUsername(userMap.get(item.getUserId()));
            item.setMachineName(item.getMachineId() == null ? null : machineMap.get(item.getMachineId()));
            item.setHandlerName(item.getHandlerId() == null ? null : userMap.get(item.getHandlerId()));
        });
    }
}
