package com.property.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.property.common.BusinessException;
import com.property.common.PageResult;
import com.property.entity.ParkingSlot;
import com.property.entity.User;
import com.property.mapper.ParkingSlotMapper;
import com.property.mapper.UserMapper;
import com.property.vo.ParkingSlotVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParkingSlotService {

    @Resource
    private ParkingSlotMapper parkingSlotMapper;

    @Resource
    private UserMapper userMapper;

    public List<ParkingSlot> list() {
        return parkingSlotMapper.selectFreeList();
    }

    public PageResult<ParkingSlotVO> page(Integer pageNum, Integer pageSize, String slotNo, Integer status, Long ownerId) {
        PageHelper.startPage(pageNum, pageSize);
        List<ParkingSlot> list = parkingSlotMapper.selectPageList(slotNo, status, ownerId);
        PageInfo<ParkingSlot> pageInfo = new PageInfo<>(list);
        PageResult<ParkingSlotVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(convertList(pageInfo.getList()));
        return result;
    }

    public void save(ParkingSlot slot) {
        if (slot == null || slot.getSlotNo() == null || slot.getSlotNo().trim().isEmpty()) {
            throw new BusinessException("车位编号不能为空");
        }
        slot.setSlotNo(slot.getSlotNo().trim());
        slot.setLocation(slot.getLocation() == null ? null : slot.getLocation().trim());
        if (slot.getOwnerId() != null) {
            User owner = userMapper.selectById(slot.getOwnerId());
            if (owner == null || !"OWNER".equals(owner.getRole())) {
                throw new BusinessException("业主不存在");
            }
            slot.setStatus(1);
        } else {
            slot.setStatus(0);
        }
        if (slot.getId() == null) {
            parkingSlotMapper.insert(slot);
        } else {
            if (parkingSlotMapper.selectById(slot.getId()) == null) {
                throw new BusinessException("车位不存在");
            }
            parkingSlotMapper.update(slot);
        }
    }

    public void deleteById(Long id) {
        parkingSlotMapper.deleteById(id);
    }

    private List<ParkingSlotVO> convertList(List<ParkingSlot> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<User> users = userMapper.selectPageList(null, null, null);
        Map<Long, String> userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getId(), user.getNickname() == null || user.getNickname().trim().isEmpty() ? user.getUsername() : user.getNickname());
        }
        List<ParkingSlotVO> result = new ArrayList<>();
        for (ParkingSlot item : list) {
            ParkingSlotVO vo = new ParkingSlotVO();
            BeanUtils.copyProperties(item, vo);
            vo.setOwnerName(item.getOwnerId() == null ? "" : userMap.getOrDefault(item.getOwnerId(), "未知业主"));
            result.add(vo);
        }
        return result;
    }
}
