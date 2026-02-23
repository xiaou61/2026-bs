package com.property.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.property.common.BusinessException;
import com.property.common.PageResult;
import com.property.entity.Building;
import com.property.entity.House;
import com.property.entity.User;
import com.property.mapper.BuildingMapper;
import com.property.mapper.HouseMapper;
import com.property.mapper.UserMapper;
import com.property.vo.HouseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HouseService {

    @Resource
    private HouseMapper houseMapper;

    @Resource
    private BuildingMapper buildingMapper;

    @Resource
    private UserMapper userMapper;

    public List<HouseVO> list() {
        return convertList(houseMapper.selectEnabledList());
    }

    public List<HouseVO> listByOwner(Long ownerId) {
        return convertList(houseMapper.selectByOwnerId(ownerId));
    }

    public List<House> listAllForMap() {
        return houseMapper.selectPageList(null, null, null, null);
    }

    public PageResult<HouseVO> page(Integer pageNum, Integer pageSize, Long buildingId, Long ownerId, Integer status, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        List<House> list = houseMapper.selectPageList(buildingId, ownerId, status, keyword);
        PageInfo<House> pageInfo = new PageInfo<>(list);
        PageResult<HouseVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(convertList(pageInfo.getList()));
        return result;
    }

    public void save(House house) {
        if (house == null) {
            throw new BusinessException("房屋参数不能为空");
        }
        if (house.getBuildingId() == null || buildingMapper.selectById(house.getBuildingId()) == null) {
            throw new BusinessException("楼栋不存在");
        }
        if (house.getUnitNo() == null || house.getUnitNo().trim().isEmpty()) {
            throw new BusinessException("单元不能为空");
        }
        if (house.getRoomNo() == null || house.getRoomNo().trim().isEmpty()) {
            throw new BusinessException("门牌号不能为空");
        }
        if (house.getOwnerId() != null) {
            User owner = userMapper.selectById(house.getOwnerId());
            if (owner == null || !"OWNER".equals(owner.getRole())) {
                throw new BusinessException("业主不存在");
            }
        }
        house.setUnitNo(house.getUnitNo().trim());
        house.setRoomNo(house.getRoomNo().trim());
        house.setArea(house.getArea() == null ? BigDecimal.ZERO : house.getArea());
        house.setStatus(house.getStatus() == null ? 1 : house.getStatus());
        if (house.getId() == null) {
            houseMapper.insert(house);
        } else {
            if (houseMapper.selectById(house.getId()) == null) {
                throw new BusinessException("房屋不存在");
            }
            houseMapper.update(house);
        }
    }

    public void deleteById(Long id) {
        houseMapper.deleteById(id);
    }

    public House mustGetById(Long id) {
        House house = houseMapper.selectById(id);
        if (house == null) {
            throw new BusinessException("房屋不存在");
        }
        return house;
    }

    public Long countAll() {
        Long count = houseMapper.countAll();
        return count == null ? 0L : count;
    }

    public Map<Long, String> buildHouseNameMap() {
        List<House> houses = houseMapper.selectPageList(null, null, null, null);
        List<Building> buildings = buildingMapper.selectPageList(null, null);
        Map<Long, String> buildingMap = new HashMap<>();
        for (Building building : buildings) {
            buildingMap.put(building.getId(), building.getName());
        }
        Map<Long, String> map = new HashMap<>();
        for (House house : houses) {
            String prefix = buildingMap.getOrDefault(house.getBuildingId(), "未知楼栋");
            map.put(house.getId(), prefix + "-" + house.getUnitNo() + "-" + house.getRoomNo());
        }
        return map;
    }

    private List<HouseVO> convertList(List<House> houses) {
        if (houses == null || houses.isEmpty()) {
            return new ArrayList<>();
        }
        List<Building> buildings = buildingMapper.selectPageList(null, null);
        List<User> users = userMapper.selectPageList(null, null, null);
        Map<Long, String> buildingMap = new HashMap<>();
        for (Building building : buildings) {
            buildingMap.put(building.getId(), building.getName());
        }
        Map<Long, String> userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getId(), user.getNickname() == null || user.getNickname().trim().isEmpty() ? user.getUsername() : user.getNickname());
        }
        List<HouseVO> result = new ArrayList<>();
        for (House item : houses) {
            HouseVO vo = new HouseVO();
            BeanUtils.copyProperties(item, vo);
            vo.setBuildingName(buildingMap.getOrDefault(item.getBuildingId(), "未知楼栋"));
            vo.setOwnerName(item.getOwnerId() == null ? "" : userMap.getOrDefault(item.getOwnerId(), "未知业主"));
            result.add(vo);
        }
        return result;
    }
}
