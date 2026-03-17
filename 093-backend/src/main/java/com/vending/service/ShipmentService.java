package com.vending.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vending.common.BusinessException;
import com.vending.common.PageResult;
import com.vending.entity.MachineSlot;
import com.vending.entity.ProductInfo;
import com.vending.entity.SaleOrder;
import com.vending.entity.ShipmentRecord;
import com.vending.entity.VendingMachine;
import com.vending.mapper.ProductInfoMapper;
import com.vending.mapper.ShipmentRecordMapper;
import com.vending.mapper.VendingMachineMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShipmentService {

    @Resource
    private ShipmentRecordMapper shipmentMapper;

    @Resource
    private SlotService slotService;

    @Resource
    private OrderService orderService;

    @Resource
    private VendingMachineMapper machineMapper;

    @Resource
    private ProductInfoMapper productMapper;

    public PageResult<ShipmentRecord> page(Integer pageNum, Integer pageSize, String shipmentStatus, Long userId, String role) {
        Page<ShipmentRecord> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ShipmentRecord> wrapper = new LambdaQueryWrapper<ShipmentRecord>()
                .eq(shipmentStatus != null && !shipmentStatus.trim().isEmpty(), ShipmentRecord::getShipmentStatus, shipmentStatus == null ? null : shipmentStatus.trim().toUpperCase())
                .eq("CUSTOMER".equalsIgnoreCase(role), ShipmentRecord::getUserId, userId)
                .orderByDesc(ShipmentRecord::getId);
        Page<ShipmentRecord> resultPage = shipmentMapper.selectPage(page, wrapper);
        fillDetail(resultPage.getRecords());
        PageResult<ShipmentRecord> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public void createForOrder(SaleOrder order) {
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        slotService.deductStock(order.getSlotId(), order.getQuantity());
        ShipmentRecord record = new ShipmentRecord();
        record.setOrderId(order.getId());
        record.setUserId(order.getUserId());
        record.setMachineId(order.getMachineId());
        record.setSlotId(order.getSlotId());
        record.setProductId(order.getProductId());
        record.setQuantity(order.getQuantity());
        record.setShipmentStatus("SUCCESS");
        record.setShipmentTime(LocalDateTime.now());
        record.setResultMsg("设备已完成自动出货");
        shipmentMapper.insert(record);
    }

    private void fillDetail(List<ShipmentRecord> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Map<Long, SaleOrder> orderMap = list.stream()
                .map(item -> orderService.getById(item.getOrderId(), item.getUserId(), "ADMIN"))
                .collect(Collectors.toMap(SaleOrder::getId, item -> item, (a, b) -> a, HashMap::new));
        Map<Long, String> machineMap = machineMapper.selectList(new LambdaQueryWrapper<VendingMachine>()
                        .in(VendingMachine::getId, list.stream().map(ShipmentRecord::getMachineId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(VendingMachine::getId, VendingMachine::getName, (a, b) -> a, HashMap::new));
        Map<Long, MachineSlot> slotMap = list.stream()
                .map(item -> slotService.requireById(item.getSlotId()))
                .collect(Collectors.toMap(MachineSlot::getId, item -> item, (a, b) -> a, HashMap::new));
        Map<Long, String> productMap = productMapper.selectList(new LambdaQueryWrapper<ProductInfo>()
                        .in(ProductInfo::getId, list.stream().map(ShipmentRecord::getProductId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(ProductInfo::getId, ProductInfo::getName, (a, b) -> a, HashMap::new));
        list.forEach(item -> {
            SaleOrder order = orderMap.get(item.getOrderId());
            MachineSlot slot = slotMap.get(item.getSlotId());
            item.setOrderNo(order == null ? null : order.getOrderNo());
            item.setMachineName(machineMap.get(item.getMachineId()));
            item.setSlotNo(slot == null ? null : slot.getSlotNo());
            item.setProductName(productMap.get(item.getProductId()));
        });
    }
}
