package com.vending.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vending.common.BusinessException;
import com.vending.common.PageResult;
import com.vending.entity.MachineLocation;
import com.vending.entity.MachineSlot;
import com.vending.entity.VendingMachine;
import com.vending.mapper.MachineLocationMapper;
import com.vending.mapper.MachineSlotMapper;
import com.vending.mapper.VendingMachineMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MachineService {

    @Resource
    private VendingMachineMapper machineMapper;

    @Resource
    private MachineLocationMapper locationMapper;

    @Resource
    private MachineSlotMapper slotMapper;

    public PageResult<VendingMachine> page(Integer pageNum, Integer pageSize, String name, String status, Long locationId) {
        Page<VendingMachine> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<VendingMachine> wrapper = new LambdaQueryWrapper<VendingMachine>()
                .like(StringUtils.hasText(name), VendingMachine::getName, name == null ? null : name.trim())
                .eq(StringUtils.hasText(status), VendingMachine::getStatus, status == null ? null : status.trim().toUpperCase())
                .eq(locationId != null, VendingMachine::getLocationId, locationId)
                .orderByDesc(VendingMachine::getId);
        Page<VendingMachine> resultPage = machineMapper.selectPage(page, wrapper);
        fillLocationName(resultPage.getRecords());
        PageResult<VendingMachine> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<VendingMachine> listAll() {
        List<VendingMachine> list = machineMapper.selectList(new LambdaQueryWrapper<VendingMachine>().orderByDesc(VendingMachine::getId));
        fillLocationName(list);
        return list;
    }

    public List<VendingMachine> publicList() {
        List<VendingMachine> list = machineMapper.selectList(new LambdaQueryWrapper<VendingMachine>()
                .eq(VendingMachine::getStatus, "ONLINE")
                .orderByDesc(VendingMachine::getId));
        fillLocationName(list);
        return list;
    }

    public VendingMachine getById(Long id) {
        VendingMachine machine = machineMapper.selectById(id);
        if (machine == null) {
            throw new BusinessException("设备不存在");
        }
        fillLocationName(java.util.Collections.singletonList(machine));
        return machine;
    }

    public void save(VendingMachine machine) {
        if (machine == null || !StringUtils.hasText(machine.getMachineNo()) || !StringUtils.hasText(machine.getName()) || machine.getLocationId() == null) {
            throw new BusinessException("设备信息不完整");
        }
        MachineLocation location = locationMapper.selectById(machine.getLocationId());
        if (location == null) {
            throw new BusinessException("点位不存在");
        }
        validateMachineNo(machine);
        if (machine.getId() == null) {
            machine.setStatus(StringUtils.hasText(machine.getStatus()) ? machine.getStatus().trim().toUpperCase() : "ONLINE");
            machineMapper.insert(machine);
        } else {
            VendingMachine db = getById(machine.getId());
            db.setMachineNo(machine.getMachineNo().trim());
            db.setLocationId(machine.getLocationId());
            db.setName(machine.getName().trim());
            db.setTemperatureType(StringUtils.hasText(machine.getTemperatureType()) ? machine.getTemperatureType().trim().toUpperCase() : null);
            db.setStatus(StringUtils.hasText(machine.getStatus()) ? machine.getStatus().trim().toUpperCase() : db.getStatus());
            db.setManagerName(StringUtils.hasText(machine.getManagerName()) ? machine.getManagerName().trim() : null);
            db.setManagerPhone(StringUtils.hasText(machine.getManagerPhone()) ? machine.getManagerPhone().trim() : null);
            db.setRemark(StringUtils.hasText(machine.getRemark()) ? machine.getRemark().trim() : null);
            machineMapper.updateById(db);
        }
    }

    public void deleteById(Long id) {
        if (slotMapper.selectCount(new LambdaQueryWrapper<MachineSlot>().eq(MachineSlot::getMachineId, id)) > 0) {
            throw new BusinessException("该设备下存在货道，无法删除");
        }
        machineMapper.deleteById(id);
    }

    public Long countAll() {
        return machineMapper.selectCount(null);
    }

    private void validateMachineNo(VendingMachine machine) {
        VendingMachine exist = machineMapper.selectOne(new LambdaQueryWrapper<VendingMachine>()
                .eq(VendingMachine::getMachineNo, machine.getMachineNo().trim())
                .last("limit 1"));
        if (exist != null && (machine.getId() == null || !exist.getId().equals(machine.getId()))) {
            throw new BusinessException("设备编号已存在");
        }
    }

    private void fillLocationName(List<VendingMachine> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        List<Long> locationIds = list.stream().map(VendingMachine::getLocationId).distinct().collect(Collectors.toList());
        Map<Long, String> locationMap = locationMapper.selectList(new LambdaQueryWrapper<MachineLocation>().in(MachineLocation::getId, locationIds))
                .stream()
                .collect(Collectors.toMap(MachineLocation::getId, MachineLocation::getName, (a, b) -> a, HashMap::new));
        list.forEach(item -> item.setLocationName(locationMap.get(item.getLocationId())));
    }
}
