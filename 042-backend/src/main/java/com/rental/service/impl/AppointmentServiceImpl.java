package com.rental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rental.common.BusinessException;
import com.rental.entity.Appointment;
import com.rental.entity.House;
import com.rental.entity.User;
import com.rental.mapper.AppointmentMapper;
import com.rental.mapper.HouseMapper;
import com.rental.mapper.UserMapper;
import com.rental.service.AppointmentService;
import com.rental.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MessageService messageService;

    @Override
    public void create(Long tenantId, Long houseId, LocalDateTime appointmentTime, String contactPhone, String remark) {
        House house = houseMapper.selectById(houseId);
        if (house == null) {
            throw new BusinessException("房源不存在");
        }
        if (house.getStatus() != 1) {
            throw new BusinessException("该房源暂不可预约");
        }

        // 检查是否已有待确认的预约
        LambdaQueryWrapper<Appointment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Appointment::getTenantId, tenantId);
        wrapper.eq(Appointment::getHouseId, houseId);
        wrapper.in(Appointment::getStatus, 0, 1);
        if (appointmentMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("您已有待处理的预约");
        }

        Appointment appointment = new Appointment();
        appointment.setHouseId(houseId);
        appointment.setTenantId(tenantId);
        appointment.setLandlordId(house.getLandlordId());
        appointment.setAppointmentTime(appointmentTime);
        appointment.setContactPhone(contactPhone);
        appointment.setRemark(remark);
        appointment.setStatus(0);

        appointmentMapper.insert(appointment);

        // 发送消息通知房东
        messageService.send(house.getLandlordId(), "新的预约申请", 
                "有租客申请预约看房：" + house.getTitle(), "appointment");
    }

    @Override
    public IPage<Map<String, Object>> getList(Long userId, String role, int page, int size, Integer status) {
        LambdaQueryWrapper<Appointment> wrapper = new LambdaQueryWrapper<>();
        
        if ("landlord".equals(role)) {
            wrapper.eq(Appointment::getLandlordId, userId);
        } else {
            wrapper.eq(Appointment::getTenantId, userId);
        }
        
        if (status != null) {
            wrapper.eq(Appointment::getStatus, status);
        }
        
        wrapper.orderByDesc(Appointment::getCreateTime);
        
        IPage<Appointment> appointmentPage = appointmentMapper.selectPage(new Page<>(page, size), wrapper);
        
        return appointmentPage.convert(this::convertToVO);
    }

    @Override
    public void confirm(Long landlordId, Long appointmentId) {
        Appointment appointment = appointmentMapper.selectById(appointmentId);
        if (appointment == null) {
            throw new BusinessException("预约不存在");
        }
        if (!appointment.getLandlordId().equals(landlordId)) {
            throw new BusinessException("无权操作此预约");
        }
        if (appointment.getStatus() != 0) {
            throw new BusinessException("预约状态不正确");
        }

        appointment.setStatus(1);
        appointmentMapper.updateById(appointment);

        // 通知租客
        House house = houseMapper.selectById(appointment.getHouseId());
        messageService.send(appointment.getTenantId(), "预约已确认",
                "您的预约已被确认：" + (house != null ? house.getTitle() : ""), "appointment");
    }

    @Override
    public void reject(Long landlordId, Long appointmentId, String reason) {
        Appointment appointment = appointmentMapper.selectById(appointmentId);
        if (appointment == null) {
            throw new BusinessException("预约不存在");
        }
        if (!appointment.getLandlordId().equals(landlordId)) {
            throw new BusinessException("无权操作此预约");
        }
        if (appointment.getStatus() != 0) {
            throw new BusinessException("预约状态不正确");
        }

        appointment.setStatus(2);
        appointment.setRejectReason(reason);
        appointmentMapper.updateById(appointment);

        // 通知租客
        House house = houseMapper.selectById(appointment.getHouseId());
        messageService.send(appointment.getTenantId(), "预约被拒绝",
                "您的预约被拒绝：" + (house != null ? house.getTitle() : "") + "，原因：" + reason, "appointment");
    }

    @Override
    public void complete(Long landlordId, Long appointmentId) {
        Appointment appointment = appointmentMapper.selectById(appointmentId);
        if (appointment == null) {
            throw new BusinessException("预约不存在");
        }
        if (!appointment.getLandlordId().equals(landlordId)) {
            throw new BusinessException("无权操作此预约");
        }
        if (appointment.getStatus() != 1) {
            throw new BusinessException("预约状态不正确");
        }

        appointment.setStatus(3);
        appointmentMapper.updateById(appointment);
    }

    @Override
    public void cancel(Long tenantId, Long appointmentId) {
        Appointment appointment = appointmentMapper.selectById(appointmentId);
        if (appointment == null) {
            throw new BusinessException("预约不存在");
        }
        if (!appointment.getTenantId().equals(tenantId)) {
            throw new BusinessException("无权操作此预约");
        }
        if (appointment.getStatus() != 0 && appointment.getStatus() != 1) {
            throw new BusinessException("预约状态不正确");
        }

        appointment.setStatus(4);
        appointmentMapper.updateById(appointment);

        // 通知房东
        House house = houseMapper.selectById(appointment.getHouseId());
        messageService.send(appointment.getLandlordId(), "预约已取消",
                "租客取消了预约：" + (house != null ? house.getTitle() : ""), "appointment");
    }

    private Map<String, Object> convertToVO(Appointment appointment) {
        Map<String, Object> vo = new HashMap<>();
        vo.put("id", appointment.getId());
        vo.put("houseId", appointment.getHouseId());
        vo.put("appointmentTime", appointment.getAppointmentTime());
        vo.put("contactPhone", appointment.getContactPhone());
        vo.put("remark", appointment.getRemark());
        vo.put("status", appointment.getStatus());
        vo.put("rejectReason", appointment.getRejectReason());
        vo.put("createTime", appointment.getCreateTime());

        // 获取房源信息
        House house = houseMapper.selectById(appointment.getHouseId());
        if (house != null) {
            Map<String, Object> houseVO = new HashMap<>();
            houseVO.put("id", house.getId());
            houseVO.put("title", house.getTitle());
            houseVO.put("address", house.getAddress());
            houseVO.put("price", house.getPrice());
            houseVO.put("images", house.getImages());
            vo.put("house", houseVO);
        }

        // 获取租客信息
        User tenant = userMapper.selectById(appointment.getTenantId());
        if (tenant != null) {
            Map<String, Object> tenantVO = new HashMap<>();
            tenantVO.put("id", tenant.getId());
            tenantVO.put("realName", tenant.getRealName());
            tenantVO.put("phone", tenant.getPhone());
            vo.put("tenant", tenantVO);
        }

        // 获取房东信息
        User landlord = userMapper.selectById(appointment.getLandlordId());
        if (landlord != null) {
            Map<String, Object> landlordVO = new HashMap<>();
            landlordVO.put("id", landlord.getId());
            landlordVO.put("realName", landlord.getRealName());
            landlordVO.put("phone", landlord.getPhone());
            vo.put("landlord", landlordVO);
        }

        return vo;
    }
}
