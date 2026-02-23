package com.property.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.property.common.BusinessException;
import com.property.common.PageResult;
import com.property.entity.Building;
import com.property.mapper.BuildingMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BuildingService {

    @Resource
    private BuildingMapper buildingMapper;

    public List<Building> list() {
        return buildingMapper.selectEnabledList();
    }

    public List<Building> listAllForMap() {
        return buildingMapper.selectPageList(null, null);
    }

    public PageResult<Building> page(Integer pageNum, Integer pageSize, String name, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        List<Building> list = buildingMapper.selectPageList(name, status);
        PageInfo<Building> pageInfo = new PageInfo<>(list);
        PageResult<Building> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(pageInfo.getList());
        return result;
    }

    public void save(Building building) {
        if (building == null || building.getName() == null || building.getName().trim().isEmpty()) {
            throw new BusinessException("楼栋名称不能为空");
        }
        building.setName(building.getName().trim());
        building.setFloors(building.getFloors() == null ? 0 : building.getFloors());
        building.setStatus(building.getStatus() == null ? 1 : building.getStatus());
        if (building.getId() == null) {
            buildingMapper.insert(building);
        } else {
            if (buildingMapper.selectById(building.getId()) == null) {
                throw new BusinessException("楼栋不存在");
            }
            buildingMapper.update(building);
        }
    }

    public void deleteById(Long id) {
        buildingMapper.deleteById(id);
    }
}
