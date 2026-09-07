package com.eldercare.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eldercare.entity.CheckAppointment;
import com.eldercare.mapper.CheckAppointmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class AppointmentService {

    @Autowired
    private CheckAppointmentMapper checkAppointmentMapper;

    public Page<CheckAppointment> page(int pageNum, int pageSize, String appointmentNo, Long elderId, Integer status) {
        Page<CheckAppointment> page = new Page<>(pageNum, pageSize);
        QueryWrapper<CheckAppointment> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(appointmentNo)) {
            wrapper.like("appointment_no", appointmentNo);
        }
        if (elderId != null) {
            wrapper.eq("elder_id", elderId);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        return checkAppointmentMapper.selectPage(page, wrapper);
    }

    public void add(CheckAppointment checkAppointment, Long userId) {
        if (!StringUtils.hasText(checkAppointment.getAppointmentNo())) {
            checkAppointment.setAppointmentNo(generateNo());
        }
        if (checkAppointment.getStatus() == null) {
            checkAppointment.setStatus(0);
        }
        checkAppointment.setCreateBy(userId);
        checkAppointmentMapper.insert(checkAppointment);
    }

    public void update(CheckAppointment checkAppointment) {
        checkAppointmentMapper.updateById(checkAppointment);
    }

    public void updateStatus(Long id, Integer status) {
        CheckAppointment appointment = new CheckAppointment();
        appointment.setId(id);
        appointment.setStatus(status);
        checkAppointmentMapper.updateById(appointment);
    }

    public void delete(Long id) {
        checkAppointmentMapper.deleteById(id);
    }

    public long countToday() {
        QueryWrapper<CheckAppointment> wrapper = new QueryWrapper<>();
        wrapper.eq("appointment_date", LocalDate.now());
        return checkAppointmentMapper.selectCount(wrapper);
    }

    public long countAll() {
        return checkAppointmentMapper.selectCount(new QueryWrapper<CheckAppointment>());
    }

    public long countFinished() {
        QueryWrapper<CheckAppointment> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 2);
        return checkAppointmentMapper.selectCount(wrapper);
    }

    private String generateNo() {
        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        return "AP" + date + System.currentTimeMillis() % 100000;
    }
}
