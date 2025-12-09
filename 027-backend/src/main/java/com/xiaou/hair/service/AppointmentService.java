package com.xiaou.hair.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.hair.dto.CreateAppointmentDTO;
import com.xiaou.hair.entity.Appointment;
import com.xiaou.hair.entity.ServiceEntity;
import com.xiaou.hair.mapper.AppointmentMapper;
import com.xiaou.hair.mapper.ServiceMapper;
import com.xiaou.hair.util.UserContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * 预约服务类
 */
@Service
public class AppointmentService {

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private ServiceMapper serviceMapper;

    /**
     * 创建预约
     */
    @Transactional
    public void createAppointment(CreateAppointmentDTO dto) {
        Long userId = UserContext.getUserId();

        // 1. 校验：最多提前7天预约
        LocalDate today = LocalDate.now();
        LocalDate maxDate = today.plusDays(7);
        if (dto.getAppointmentDate().isBefore(today)) {
            throw new RuntimeException("不能预约过去的日期");
        }
        if (dto.getAppointmentDate().isAfter(maxDate)) {
            throw new RuntimeException("最多只能提前7天预约");
        }

        // 2. 校验：用户未完成预约数不能超过3个
        LambdaQueryWrapper<Appointment> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(Appointment::getUserId, userId)
                   .in(Appointment::getStatus, "待确认", "已确认", "服务中");
        long unfinishedCount = appointmentMapper.selectCount(userWrapper);
        if (unfinishedCount >= 3) {
            throw new RuntimeException("您有" + unfinishedCount + "个未完成的预约，最多只能同时预约3个");
        }

        // 3. 校验：时段是否已被占用
        if (!isTimeSlotAvailable(dto.getHairdresserId(), dto.getAppointmentDate(), dto.getAppointmentTime())) {
            throw new RuntimeException("该时段已被预约，请选择其他时段");
        }

        // 4. 创建预约
        Appointment appointment = new Appointment();
        BeanUtils.copyProperties(dto, appointment);
        appointment.setUserId(userId);
        appointment.setStatus("待确认");

        appointmentMapper.insert(appointment);

        // TODO: 发送预约通知
    }

    /**
     * 检查时段是否可用
     */
    public boolean isTimeSlotAvailable(Long hairdresserId, LocalDate date, LocalTime time) {
        LambdaQueryWrapper<Appointment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Appointment::getHairdresserId, hairdresserId)
               .eq(Appointment::getAppointmentDate, date)
               .eq(Appointment::getAppointmentTime, time)
               .in(Appointment::getStatus, "待确认", "已确认", "服务中");

        return appointmentMapper.selectCount(wrapper) == 0;
    }

    /**
     * 我的预约列表
     */
    public Page<Appointment> getMyAppointments(Integer pageNum, Integer pageSize, String status) {
        Long userId = UserContext.getUserId();
        Page<Appointment> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Appointment> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Appointment::getUserId, userId);

        // 按状态筛选
        if (status != null && !status.isEmpty()) {
            wrapper.eq(Appointment::getStatus, status);
        }

        wrapper.orderByDesc(Appointment::getCreatedAt);
        return appointmentMapper.selectPage(page, wrapper);
    }

    /**
     * 预约详情
     */
    public Appointment getAppointmentById(Long id) {
        Appointment appointment = appointmentMapper.selectById(id);
        if (appointment == null) {
            throw new RuntimeException("预约不存在");
        }

        // 验证是否是本人的预约
        Long userId = UserContext.getUserId();
        if (!appointment.getUserId().equals(userId)) {
            throw new RuntimeException("无权查看他人的预约");
        }

        return appointment;
    }

    /**
     * 取消预约
     */
    @Transactional
    public void cancelAppointment(Long id, String reason) {
        Appointment appointment = appointmentMapper.selectById(id);
        if (appointment == null) {
            throw new RuntimeException("预约不存在");
        }

        // 验证是否是本人的预约
        Long userId = UserContext.getUserId();
        if (!appointment.getUserId().equals(userId)) {
            throw new RuntimeException("无权取消他人的预约");
        }

        // 验证预约状态
        if (!"待确认".equals(appointment.getStatus()) && !"已确认".equals(appointment.getStatus())) {
            throw new RuntimeException("该预约无法取消");
        }

        // 验证是否在可取消时间范围内（预约开始前2小时）
        LocalDateTime appointmentDateTime = LocalDateTime.of(
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime()
        );
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime cancelDeadline = appointmentDateTime.minusHours(2);

        if (now.isAfter(cancelDeadline)) {
            throw new RuntimeException("距离预约时间不足2小时，无法取消");
        }

        // 取消预约
        appointment.setStatus("已取消");
        appointment.setCancelReason(reason != null ? reason : "用户主动取消");
        appointmentMapper.updateById(appointment);

        // TODO: 发送取消通知
    }

    /**
     * 确认预约（管理员操作）
     */
    @Transactional
    public void confirmAppointment(Long id) {
        Appointment appointment = appointmentMapper.selectById(id);
        if (appointment == null) {
            throw new RuntimeException("预约不存在");
        }

        if (!"待确认".equals(appointment.getStatus())) {
            throw new RuntimeException("预约状态不正确");
        }

        appointment.setStatus("已确认");
        appointmentMapper.updateById(appointment);

        // TODO: 发送确认通知
    }

    /**
     * 开始服务
     */
    @Transactional
    public void startService(Long id) {
        Appointment appointment = appointmentMapper.selectById(id);
        if (appointment == null) {
            throw new RuntimeException("预约不存在");
        }

        if (!"已确认".equals(appointment.getStatus())) {
            throw new RuntimeException("预约状态不正确");
        }

        appointment.setStatus("服务中");
        appointmentMapper.updateById(appointment);
    }

    /**
     * 完成服务
     */
    @Transactional
    public void completeService(Long id) {
        Appointment appointment = appointmentMapper.selectById(id);
        if (appointment == null) {
            throw new RuntimeException("预约不存在");
        }

        if (!"服务中".equals(appointment.getStatus())) {
            throw new RuntimeException("预约状态不正确");
        }

        appointment.setStatus("已完成");
        appointmentMapper.updateById(appointment);

        // TODO: 创建订单
    }

    /**
     * 超时自动取消（定时任务调用）
     */
    @Transactional
    public void autoCancel Overtime Appointments() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deadline = now.minusMinutes(15);

        LambdaQueryWrapper<Appointment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Appointment::getStatus, "已确认");

        List<Appointment> appointments = appointmentMapper.selectList(wrapper);
        for (Appointment appointment : appointments) {
            LocalDateTime appointmentTime = LocalDateTime.of(
                    appointment.getAppointmentDate(),
                    appointment.getAppointmentTime()
            );

            // 超过预约时间15分钟未签到
            if (appointmentTime.isBefore(deadline)) {
                appointment.setStatus("超时取消");
                appointment.setCancelReason("超时未到店");
                appointmentMapper.updateById(appointment);

                // TODO: 发送通知、扣除信用分
            }
        }
    }

    /**
     * 获取理发师在指定日期的可预约时段
     */
    public List<LocalTime> getAvailableTimeSlots(Long hairdresserId, LocalDate date) {
        // 返回可预约的时段列表（每30分钟一个时段）
        // 这里简化处理，实际应该根据理发师排班和已有预约计算
        List<LocalTime> timeSlots = new java.util.ArrayList<>();
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(18, 0);

        LocalTime current = start;
        while (current.isBefore(end)) {
            if (isTimeSlotAvailable(hairdresserId, date, current)) {
                timeSlots.add(current);
            }
            current = current.plusMinutes(30);
        }

        return timeSlots;
    }
}
