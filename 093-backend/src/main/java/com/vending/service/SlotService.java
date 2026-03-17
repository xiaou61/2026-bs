package com.vending.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vending.common.BusinessException;
import com.vending.common.PageResult;
import com.vending.entity.MachineSlot;
import com.vending.entity.ProductInfo;
import com.vending.entity.VendingMachine;
import com.vending.mapper.MachineSlotMapper;
import com.vending.mapper.ProductInfoMapper;
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
public class SlotService {

    @Resource
    private MachineSlotMapper slotMapper;

    @Resource
    private VendingMachineMapper machineMapper;

    @Resource
    private ProductInfoMapper productMapper;

    public PageResult<MachineSlot> page(Integer pageNum, Integer pageSize, Long machineId, String status) {
        Page<MachineSlot> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<MachineSlot> wrapper = new LambdaQueryWrapper<MachineSlot>()
                .eq(machineId != null, MachineSlot::getMachineId, machineId)
                .eq(StringUtils.hasText(status), MachineSlot::getStatus, status == null ? null : status.trim().toUpperCase())
                .orderByDesc(MachineSlot::getId);
        Page<MachineSlot> resultPage = slotMapper.selectPage(page, wrapper);
        fillDetail(resultPage.getRecords());
        PageResult<MachineSlot> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<MachineSlot> listByMachine(Long machineId) {
        List<MachineSlot> list = slotMapper.selectList(new LambdaQueryWrapper<MachineSlot>()
                .eq(machineId != null, MachineSlot::getMachineId, machineId)
                .orderByAsc(MachineSlot::getSlotNo));
        fillDetail(list);
        return list;
    }

    public MachineSlot getById(Long id) {
        MachineSlot slot = slotMapper.selectById(id);
        if (slot == null) {
            throw new BusinessException("货道不存在");
        }
        fillDetail(java.util.Collections.singletonList(slot));
        return slot;
    }

    public MachineSlot requireById(Long id) {
        MachineSlot slot = slotMapper.selectById(id);
        if (slot == null) {
            throw new BusinessException("货道不存在");
        }
        return slot;
    }

    public void save(MachineSlot slot) {
        if (slot == null || slot.getMachineId() == null || !StringUtils.hasText(slot.getSlotNo()) || slot.getProductId() == null) {
            throw new BusinessException("货道信息不完整");
        }
        VendingMachine machine = machineMapper.selectById(slot.getMachineId());
        if (machine == null) {
            throw new BusinessException("设备不存在");
        }
        ProductInfo product = productMapper.selectById(slot.getProductId());
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        validateUniqueSlot(slot);
        if (slot.getCapacity() == null || slot.getCapacity() <= 0) {
            throw new BusinessException("货道容量必须大于0");
        }
        int currentStock = slot.getCurrentStock() == null ? 0 : slot.getCurrentStock();
        if (currentStock > slot.getCapacity()) {
            throw new BusinessException("当前库存不能大于货道容量");
        }
        if (slot.getId() == null) {
            slot.setCurrentStock(currentStock);
            slot.setStatus(resolveStatus(slot.getStatus(), currentStock));
            slot.setLastSyncTime(LocalDateTime.now());
            slotMapper.insert(slot);
        } else {
            MachineSlot db = requireById(slot.getId());
            db.setMachineId(slot.getMachineId());
            db.setSlotNo(slot.getSlotNo().trim());
            db.setProductId(slot.getProductId());
            db.setCapacity(slot.getCapacity());
            db.setCurrentStock(currentStock);
            db.setStatus(resolveStatus(slot.getStatus(), currentStock));
            db.setLastSyncTime(LocalDateTime.now());
            slotMapper.updateById(db);
        }
    }

    public void deleteById(Long id) {
        slotMapper.deleteById(id);
    }

    public void deductStock(Long slotId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new BusinessException("购买数量不合法");
        }
        MachineSlot slot = requireById(slotId);
        if ("DISABLED".equalsIgnoreCase(slot.getStatus())) {
            throw new BusinessException("该货道已停用");
        }
        int currentStock = slot.getCurrentStock() == null ? 0 : slot.getCurrentStock();
        if (currentStock < quantity) {
            throw new BusinessException("货道库存不足");
        }
        slot.setCurrentStock(currentStock - quantity);
        slot.setStatus(slot.getCurrentStock() <= 0 ? "SOLD_OUT" : "NORMAL");
        slot.setLastSyncTime(LocalDateTime.now());
        slotMapper.updateById(slot);
    }

    public void replenishStock(Long slotId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new BusinessException("补货数量不合法");
        }
        MachineSlot slot = requireById(slotId);
        int before = slot.getCurrentStock() == null ? 0 : slot.getCurrentStock();
        int capacity = slot.getCapacity() == null ? 0 : slot.getCapacity();
        if (before + quantity > capacity) {
            throw new BusinessException("补货后库存超过货道容量");
        }
        slot.setCurrentStock(before + quantity);
        slot.setStatus(slot.getCurrentStock() <= 0 ? "SOLD_OUT" : "NORMAL");
        slot.setLastSyncTime(LocalDateTime.now());
        slotMapper.updateById(slot);
    }

    private void validateUniqueSlot(MachineSlot slot) {
        MachineSlot exist = slotMapper.selectOne(new LambdaQueryWrapper<MachineSlot>()
                .eq(MachineSlot::getMachineId, slot.getMachineId())
                .eq(MachineSlot::getSlotNo, slot.getSlotNo().trim())
                .last("limit 1"));
        if (exist != null && (slot.getId() == null || !exist.getId().equals(slot.getId()))) {
            throw new BusinessException("同一设备下货道编号已存在");
        }
    }

    private String resolveStatus(String status, Integer currentStock) {
        if (StringUtils.hasText(status) && "DISABLED".equalsIgnoreCase(status.trim())) {
            return "DISABLED";
        }
        return currentStock != null && currentStock > 0 ? "NORMAL" : "SOLD_OUT";
    }

    private void fillDetail(List<MachineSlot> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        List<Long> machineIds = list.stream().map(MachineSlot::getMachineId).distinct().collect(Collectors.toList());
        List<Long> productIds = list.stream().map(MachineSlot::getProductId).distinct().collect(Collectors.toList());
        Map<Long, String> machineMap = machineMapper.selectList(new LambdaQueryWrapper<VendingMachine>().in(VendingMachine::getId, machineIds))
                .stream()
                .collect(Collectors.toMap(VendingMachine::getId, VendingMachine::getName, (a, b) -> a, HashMap::new));
        Map<Long, String> productMap = productMapper.selectList(new LambdaQueryWrapper<ProductInfo>().in(ProductInfo::getId, productIds))
                .stream()
                .collect(Collectors.toMap(ProductInfo::getId, ProductInfo::getName, (a, b) -> a, HashMap::new));
        list.forEach(item -> {
            item.setMachineName(machineMap.get(item.getMachineId()));
            item.setProductName(productMap.get(item.getProductId()));
        });
    }
}
