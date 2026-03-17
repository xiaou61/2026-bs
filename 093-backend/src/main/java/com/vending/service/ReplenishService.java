package com.vending.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vending.common.BusinessException;
import com.vending.common.PageResult;
import com.vending.dto.ReplenishDTO;
import com.vending.entity.MachineSlot;
import com.vending.entity.ProductInfo;
import com.vending.entity.ReplenishRecord;
import com.vending.entity.User;
import com.vending.entity.VendingMachine;
import com.vending.mapper.ProductInfoMapper;
import com.vending.mapper.ReplenishRecordMapper;
import com.vending.mapper.UserMapper;
import com.vending.mapper.VendingMachineMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReplenishService {

    @Resource
    private ReplenishRecordMapper replenishMapper;

    @Resource
    private SlotService slotService;

    @Resource
    private VendingMachineMapper machineMapper;

    @Resource
    private ProductInfoMapper productMapper;

    @Resource
    private UserMapper userMapper;

    public PageResult<ReplenishRecord> page(Integer pageNum, Integer pageSize, Long machineId, Long operatorId) {
        Page<ReplenishRecord> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ReplenishRecord> wrapper = new LambdaQueryWrapper<ReplenishRecord>()
                .eq(machineId != null, ReplenishRecord::getMachineId, machineId)
                .eq(operatorId != null, ReplenishRecord::getOperatorId, operatorId)
                .orderByDesc(ReplenishRecord::getId);
        Page<ReplenishRecord> resultPage = replenishMapper.selectPage(page, wrapper);
        fillDetail(resultPage.getRecords());
        PageResult<ReplenishRecord> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(Long operatorId, ReplenishDTO dto) {
        if (dto == null || dto.getSlotId() == null || dto.getQuantity() == null || dto.getQuantity() <= 0) {
            throw new BusinessException("补货参数不完整");
        }
        MachineSlot slot = slotService.requireById(dto.getSlotId());
        if (dto.getMachineId() != null && !dto.getMachineId().equals(slot.getMachineId())) {
            throw new BusinessException("设备与货道不匹配");
        }
        VendingMachine machine = machineMapper.selectById(slot.getMachineId());
        if (machine == null) {
            throw new BusinessException("设备不存在");
        }
        int before = slot.getCurrentStock() == null ? 0 : slot.getCurrentStock();
        slotService.replenishStock(slot.getId(), dto.getQuantity());
        MachineSlot latestSlot = slotService.requireById(slot.getId());
        ReplenishRecord record = new ReplenishRecord();
        record.setReplenishNo("RP" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + RandomUtil.randomNumbers(4));
        record.setMachineId(machine.getId());
        record.setSlotId(slot.getId());
        record.setProductId(slot.getProductId());
        record.setQuantity(dto.getQuantity());
        record.setBeforeStock(before);
        record.setAfterStock(latestSlot.getCurrentStock());
        record.setOperatorId(operatorId);
        record.setRemark(dto.getRemark());
        replenishMapper.insert(record);
        machine.setLastReplenishTime(LocalDateTime.now());
        machineMapper.updateById(machine);
    }

    private void fillDetail(List<ReplenishRecord> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Map<Long, String> machineMap = machineMapper.selectList(new LambdaQueryWrapper<VendingMachine>()
                        .in(VendingMachine::getId, list.stream().map(ReplenishRecord::getMachineId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(VendingMachine::getId, VendingMachine::getName, (a, b) -> a, HashMap::new));
        Map<Long, MachineSlot> slotMap = list.stream()
                .map(item -> slotService.requireById(item.getSlotId()))
                .collect(Collectors.toMap(MachineSlot::getId, item -> item, (a, b) -> a, HashMap::new));
        Map<Long, String> productMap = productMapper.selectList(new LambdaQueryWrapper<ProductInfo>()
                        .in(ProductInfo::getId, list.stream().map(ReplenishRecord::getProductId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(ProductInfo::getId, ProductInfo::getName, (a, b) -> a, HashMap::new));
        Map<Long, String> userMap = userMapper.selectList(new LambdaQueryWrapper<User>()
                        .in(User::getId, list.stream().map(ReplenishRecord::getOperatorId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(User::getId, User::getNickname, (a, b) -> a, HashMap::new));
        list.forEach(item -> {
            item.setMachineName(machineMap.get(item.getMachineId()));
            MachineSlot slot = slotMap.get(item.getSlotId());
            item.setSlotNo(slot == null ? null : slot.getSlotNo());
            item.setProductName(productMap.get(item.getProductId()));
            item.setOperatorName(userMap.get(item.getOperatorId()));
        });
    }
}
