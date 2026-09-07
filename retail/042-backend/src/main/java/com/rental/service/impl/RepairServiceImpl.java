package com.rental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rental.common.BusinessException;
import com.rental.entity.House;
import com.rental.entity.Repair;
import com.rental.entity.User;
import com.rental.mapper.HouseMapper;
import com.rental.mapper.RepairMapper;
import com.rental.mapper.UserMapper;
import com.rental.service.MessageService;
import com.rental.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RepairServiceImpl implements RepairService {

    @Autowired
    private RepairMapper repairMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MessageService messageService;

    @Override
    public void create(Long tenantId, Repair repair) {
        House house = houseMapper.selectById(repair.getHouseId());
        if (house == null) {
            throw new BusinessException("房源不存在");
        }

        repair.setTenantId(tenantId);
        repair.setLandlordId(house.getLandlordId());
        repair.setStatus(0);
        repairMapper.insert(repair);

        messageService.send(house.getLandlordId(), "新报修申请",
                "租客提交了报修申请：" + repair.getType(), "repair");
    }

    @Override
    public IPage<Map<String, Object>> getList(Long userId, String role, int page, int size, Integer status) {
        LambdaQueryWrapper<Repair> wrapper = new LambdaQueryWrapper<>();
        if ("landlord".equals(role)) {
            wrapper.eq(Repair::getLandlordId, userId);
        } else {
            wrapper.eq(Repair::getTenantId, userId);
        }
        if (status != null) {
            wrapper.eq(Repair::getStatus, status);
        }
        wrapper.orderByDesc(Repair::getCreateTime);

        IPage<Repair> repairPage = repairMapper.selectPage(new Page<>(page, size), wrapper);
        return repairPage.convert(this::convertToVO);
    }

    @Override
    public void process(Long landlordId, Long repairId) {
        Repair repair = repairMapper.selectById(repairId);
        if (repair == null || !repair.getLandlordId().equals(landlordId)) {
            throw new BusinessException("无权操作");
        }
        repair.setStatus(1);
        repairMapper.updateById(repair);

        messageService.send(repair.getTenantId(), "报修处理中",
                "房东已开始处理您的报修", "repair");
    }

    @Override
    public void complete(Long landlordId, Long repairId, String result) {
        Repair repair = repairMapper.selectById(repairId);
        if (repair == null || !repair.getLandlordId().equals(landlordId)) {
            throw new BusinessException("无权操作");
        }
        repair.setStatus(2);
        repair.setResult(result);
        repairMapper.updateById(repair);

        messageService.send(repair.getTenantId(), "报修已完成",
                "您的报修已处理完成", "repair");
    }

    private Map<String, Object> convertToVO(Repair repair) {
        Map<String, Object> vo = new HashMap<>();
        vo.put("id", repair.getId());
        vo.put("houseId", repair.getHouseId());
        vo.put("type", repair.getType());
        vo.put("description", repair.getDescription());
        vo.put("images", repair.getImages());
        vo.put("status", repair.getStatus());
        vo.put("result", repair.getResult());
        vo.put("createTime", repair.getCreateTime());

        House house = houseMapper.selectById(repair.getHouseId());
        if (house != null) {
            Map<String, Object> houseVO = new HashMap<>();
            houseVO.put("title", house.getTitle());
            houseVO.put("address", house.getAddress());
            vo.put("house", houseVO);
        }

        User tenant = userMapper.selectById(repair.getTenantId());
        if (tenant != null) {
            Map<String, Object> tenantVO = new HashMap<>();
            tenantVO.put("realName", tenant.getRealName());
            tenantVO.put("phone", tenant.getPhone());
            vo.put("tenant", tenantVO);
        }

        return vo;
    }
}
