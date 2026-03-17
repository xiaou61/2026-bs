package com.vending.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vending.common.BusinessException;
import com.vending.common.PageResult;
import com.vending.entity.MachineLocation;
import com.vending.entity.VendingMachine;
import com.vending.mapper.MachineLocationMapper;
import com.vending.mapper.VendingMachineMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LocationService {

    @Resource
    private MachineLocationMapper locationMapper;

    @Resource
    private VendingMachineMapper machineMapper;

    public PageResult<MachineLocation> page(Integer pageNum, Integer pageSize, String name, Integer status) {
        Page<MachineLocation> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<MachineLocation> wrapper = new LambdaQueryWrapper<MachineLocation>()
                .like(StringUtils.hasText(name), MachineLocation::getName, name == null ? null : name.trim())
                .eq(status != null, MachineLocation::getStatus, status)
                .orderByDesc(MachineLocation::getId);
        Page<MachineLocation> resultPage = locationMapper.selectPage(page, wrapper);
        PageResult<MachineLocation> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<MachineLocation> listAll() {
        return locationMapper.selectList(new LambdaQueryWrapper<MachineLocation>().orderByDesc(MachineLocation::getId));
    }

    public MachineLocation getById(Long id) {
        MachineLocation location = locationMapper.selectById(id);
        if (location == null) {
            throw new BusinessException("点位不存在");
        }
        return location;
    }

    public void save(MachineLocation location) {
        if (location == null || !StringUtils.hasText(location.getName())) {
            throw new BusinessException("点位名称不能为空");
        }
        if (location.getId() == null) {
            location.setStatus(location.getStatus() == null ? 1 : location.getStatus());
            locationMapper.insert(location);
        } else {
            MachineLocation db = getById(location.getId());
            db.setLocationNo(StringUtils.hasText(location.getLocationNo()) ? location.getLocationNo().trim() : db.getLocationNo());
            db.setName(location.getName().trim());
            db.setSceneType(StringUtils.hasText(location.getSceneType()) ? location.getSceneType().trim() : null);
            db.setContactPerson(StringUtils.hasText(location.getContactPerson()) ? location.getContactPerson().trim() : null);
            db.setContactPhone(StringUtils.hasText(location.getContactPhone()) ? location.getContactPhone().trim() : null);
            db.setAddress(StringUtils.hasText(location.getAddress()) ? location.getAddress().trim() : null);
            db.setRemark(StringUtils.hasText(location.getRemark()) ? location.getRemark().trim() : null);
            if (location.getStatus() != null) {
                db.setStatus(location.getStatus());
            }
            locationMapper.updateById(db);
        }
    }

    public void deleteById(Long id) {
        if (machineMapper.selectCount(new LambdaQueryWrapper<VendingMachine>().eq(VendingMachine::getLocationId, id)) > 0) {
            throw new BusinessException("该点位下存在设备，无法删除");
        }
        locationMapper.deleteById(id);
    }
}
