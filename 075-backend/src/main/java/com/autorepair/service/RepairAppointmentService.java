package com.autorepair.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.autorepair.common.BusinessException;
import com.autorepair.entity.RepairService;
import com.autorepair.entity.RepairAppointment;
import com.autorepair.entity.User;
import com.autorepair.mapper.RepairServiceMapper;
import com.autorepair.mapper.RepairAppointmentMapper;
import com.autorepair.mapper.UserMapper;
import com.autorepair.vo.RepairAppointmentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RepairAppointmentService {

    @Resource
    private RepairAppointmentMapper orderMapper;

    @Resource
    private RepairServiceService repairServiceService;

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RepairServiceMapper repairServiceMapper;

    @Transactional(rollbackFor = Exception.class)
    public RepairAppointment create(Long userId,
                                    Long serviceId,
                                    Integer quantity,
                                    String remark,
                                    LocalDate appointmentDate,
                                    LocalTime appointmentTime,
                                    String plateNo,
                                    String vehicleModel,
                                    String faultDesc) {
        if (quantity == null || quantity <= 0) {
            throw new BusinessException("预约数量必须大于0");
        }
        if (appointmentDate == null || appointmentTime == null) {
            throw new BusinessException("预约日期和预约时间不能为空");
        }
        LocalDate today = LocalDate.now();
        if (appointmentDate.isBefore(today)) {
            throw new BusinessException("预约日期不能早于今天");
        }
        if (appointmentDate.isAfter(today.plusDays(30))) {
            throw new BusinessException("最多支持预约30天内的时间");
        }
        if (appointmentDate.isEqual(today) && appointmentTime.isBefore(LocalTime.now())) {
            throw new BusinessException("预约时间不能早于当前时间");
        }
        if (plateNo == null || plateNo.trim().isEmpty()) {
            throw new BusinessException("车牌号不能为空");
        }
        plateNo = plateNo.trim();
        if (plateNo.length() > 20) {
            throw new BusinessException("车牌号不能超过20字符");
        }
        if (vehicleModel == null || vehicleModel.trim().isEmpty()) {
            throw new BusinessException("车型不能为空");
        }
        vehicleModel = vehicleModel.trim();
        if (vehicleModel.length() > 80) {
            throw new BusinessException("车型不能超过80字符");
        }
        if (faultDesc != null) {
            faultDesc = faultDesc.trim();
            if (faultDesc.length() > 500) {
                throw new BusinessException("故障描述不能超过500字符");
            }
        }
        if (remark != null) {
            remark = remark.trim();
            if (remark.length() > 255) {
                throw new BusinessException("备注长度不能超过255字符");
            }
        }
        RepairService service = repairServiceService.mustGetById(serviceId);
        if (service.getStatus() == null || service.getStatus() != 1) {
            throw new BusinessException("维修项目已下架");
        }
        if (service.getSellerId().equals(userId)) {
            throw new BusinessException("不能预约自己发布的项目");
        }
        int stock = service.getStock() == null ? 0 : service.getStock();
        if (stock < quantity) {
            throw new BusinessException("可预约名额不足");
        }
        Long duplicateCount = orderMapper.selectCount(new QueryWrapper<RepairAppointment>()
                .eq("user_id", userId)
                .eq("service_id", serviceId)
                .eq("appointment_date", appointmentDate)
                .eq("appointment_time", appointmentTime)
                .in("status", 0, 1, 2));
        if (duplicateCount != null && duplicateCount > 0) {
            throw new BusinessException("该时间段已预约过该项目");
        }
        RepairAppointment order = new RepairAppointment();
        order.setOrderNo("AR" + IdUtil.getSnowflakeNextIdStr());
        order.setUserId(userId);
        order.setServiceId(serviceId);
        order.setSellerId(service.getSellerId());
        order.setQuantity(quantity);
        order.setTotalPrice(service.getPrice().multiply(new BigDecimal(quantity)));
        order.setStatus(0);
        order.setRemark(remark);
        order.setAppointmentDate(appointmentDate);
        order.setAppointmentTime(appointmentTime);
        order.setPlateNo(plateNo);
        order.setVehicleModel(vehicleModel);
        order.setFaultDesc(faultDesc);
        orderMapper.insert(order);
        return order;
    }

    public Page<RepairAppointmentVO> page(Integer pageNum, Integer pageSize, String orderNo, Integer status, Long userId) {
        QueryWrapper<RepairAppointment> wrapper = new QueryWrapper<>();
        if (orderNo != null && !orderNo.trim().isEmpty()) {
            wrapper.like("order_no", orderNo.trim());
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        wrapper.orderByDesc("id");
        Page<RepairAppointment> orderPage = orderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return convertOrderPage(orderPage);
    }

    public Page<RepairAppointmentVO> myOrders(Long userId, Integer pageNum, Integer pageSize, Integer status, String orderNo) {
        QueryWrapper<RepairAppointment> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (orderNo != null && !orderNo.trim().isEmpty()) {
            wrapper.like("order_no", orderNo.trim());
        }
        wrapper.orderByDesc("id");
        Page<RepairAppointment> orderPage = orderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return convertOrderPage(orderPage);
    }

    public Page<RepairAppointmentVO> saleOrders(Long sellerId, Integer pageNum, Integer pageSize, Integer status, String orderNo) {
        QueryWrapper<RepairAppointment> wrapper = new QueryWrapper<>();
        wrapper.eq("seller_id", sellerId);
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (orderNo != null && !orderNo.trim().isEmpty()) {
            wrapper.like("order_no", orderNo.trim());
        }
        wrapper.orderByDesc("id");
        Page<RepairAppointment> orderPage = orderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return convertOrderPage(orderPage);
    }

    @Transactional(rollbackFor = Exception.class)
    public void pay(Long id, Long userId) {
        RepairAppointment order = mustGetById(id);
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException("无权限操作该预约单");
        }
        if (order.getStatus() != 0) {
            throw new BusinessException("预约单状态不支持支付");
        }
        repairServiceService.decreaseStockAndIncreaseBooked(order.getServiceId(), order.getQuantity());
        userService.updateBalance(order.getUserId(), order.getTotalPrice().negate());
        userService.updateBalance(order.getSellerId(), order.getTotalPrice());
        order.setStatus(1);
        order.setPayTime(LocalDateTime.now());
        orderMapper.updateById(order);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deliver(Long id, Long operatorId, String role) {
        RepairAppointment order = mustGetById(id);
        if (!"ADMIN".equals(role) && !order.getSellerId().equals(operatorId)) {
            throw new BusinessException("无权限确认预约");
        }
        if (order.getStatus() != 1) {
            throw new BusinessException("仅已支付预约可确认");
        }
        order.setStatus(2);
        orderMapper.updateById(order);
    }

    @Transactional(rollbackFor = Exception.class)
    public void complete(Long id, Long userId) {
        RepairAppointment order = mustGetById(id);
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException("无权限操作该预约单");
        }
        if (order.getStatus() != 2) {
            throw new BusinessException("仅已确认预约可完成");
        }
        order.setStatus(3);
        order.setFinishTime(LocalDateTime.now());
        orderMapper.updateById(order);
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancel(Long id, Long operatorId, String role) {
        RepairAppointment order = mustGetById(id);
        boolean isAdmin = "ADMIN".equals(role);
        if (!isAdmin && !order.getUserId().equals(operatorId)) {
            throw new BusinessException("无权限取消该预约单");
        }
        if (order.getStatus() == 3 || order.getStatus() == 4) {
            throw new BusinessException("预约单不可取消");
        }
        if (order.getStatus() == 5) {
            throw new BusinessException("预约申诉处理中，请先处理申诉");
        }
        if (!isAdmin && order.getStatus() == 2) {
            throw new BusinessException("预约已确认，请走申诉流程");
        }
        if (order.getStatus() == 1 || order.getStatus() == 2) {
            repairServiceService.restoreStockAndBooked(order.getServiceId(), order.getQuantity());
            userService.updateBalance(order.getSellerId(), order.getTotalPrice().negate());
            userService.updateBalance(order.getUserId(), order.getTotalPrice());
        }
        order.setStatus(4);
        orderMapper.updateById(order);
    }

    public RepairAppointment mustGetById(Long id) {
        RepairAppointment order = orderMapper.selectById(id);
        if (order == null) {
            throw new BusinessException("预约单不存在");
        }
        return order;
    }

    private Page<RepairAppointmentVO> convertOrderPage(Page<RepairAppointment> orderPage) {
        Page<RepairAppointmentVO> voPage = new Page<>(orderPage.getCurrent(), orderPage.getSize(), orderPage.getTotal());
        if (orderPage.getRecords() == null || orderPage.getRecords().isEmpty()) {
            voPage.setRecords(new ArrayList<>());
            return voPage;
        }
        Set<Long> userIds = new HashSet<>();
        Set<Long> serviceIds = new HashSet<>();
        for (RepairAppointment order : orderPage.getRecords()) {
            if (order.getUserId() != null) {
                userIds.add(order.getUserId());
            }
            if (order.getSellerId() != null) {
                userIds.add(order.getSellerId());
            }
            if (order.getServiceId() != null) {
                serviceIds.add(order.getServiceId());
            }
        }
        Map<Long, User> userMap = new HashMap<>();
        if (!userIds.isEmpty()) {
            List<User> users = userMapper.selectBatchIds(userIds);
            for (User user : users) {
                userMap.put(user.getId(), user);
            }
        }
        Map<Long, RepairService> serviceMap = new HashMap<>();
        if (!serviceIds.isEmpty()) {
            List<RepairService> services = repairServiceMapper.selectBatchIds(serviceIds);
            for (RepairService service : services) {
                serviceMap.put(service.getId(), service);
            }
        }
        List<RepairAppointmentVO> records = new ArrayList<>();
        for (RepairAppointment order : orderPage.getRecords()) {
            RepairAppointmentVO vo = new RepairAppointmentVO();
            BeanUtils.copyProperties(order, vo);
            User buyer = userMap.get(order.getUserId());
            User seller = userMap.get(order.getSellerId());
            RepairService service = serviceMap.get(order.getServiceId());
            vo.setBuyerName(buyer == null ? "未知用户" : (buyer.getNickname() == null || buyer.getNickname().trim().isEmpty() ? buyer.getUsername() : buyer.getNickname()));
            vo.setSellerName(seller == null ? "未知用户" : (seller.getNickname() == null || seller.getNickname().trim().isEmpty() ? seller.getUsername() : seller.getNickname()));
            vo.setServiceTitle(service == null ? "维修项目已删除" : service.getTitle());
            records.add(vo);
        }
        voPage.setRecords(records);
        return voPage;
    }
}





