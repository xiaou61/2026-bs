package com.agriculture.service;

import com.agriculture.entity.ExpertAppointment;
import com.agriculture.mapper.ExpertAppointmentMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService extends ServiceImpl<ExpertAppointmentMapper, ExpertAppointment> {

    public Page<ExpertAppointment> getPage(Integer pageNum, Integer pageSize, Integer status) {
        Page<ExpertAppointment> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ExpertAppointment> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(ExpertAppointment::getStatus, status);
        }
        wrapper.orderByDesc(ExpertAppointment::getAppointmentDate);
        return this.page(page, wrapper);
    }

    public Page<ExpertAppointment> getMyAppointments(Long userId, Integer pageNum, Integer pageSize) {
        Page<ExpertAppointment> page = new Page<>(pageNum, pageSize);
        return this.page(page, new LambdaQueryWrapper<ExpertAppointment>()
                .eq(ExpertAppointment::getFarmerId, userId)
                .or()
                .eq(ExpertAppointment::getExpertId, userId)
                .orderByDesc(ExpertAppointment::getAppointmentDate));
    }

    public void confirm(Long id) {
        this.lambdaUpdate().eq(ExpertAppointment::getId, id).set(ExpertAppointment::getStatus, 1).update();
    }

    public void complete(Long id) {
        this.lambdaUpdate().eq(ExpertAppointment::getId, id).set(ExpertAppointment::getStatus, 2).update();
    }

    public void cancel(Long id) {
        this.lambdaUpdate().eq(ExpertAppointment::getId, id).set(ExpertAppointment::getStatus, 3).update();
    }

    public void rate(Long id, Integer rating, String feedback) {
        this.lambdaUpdate().eq(ExpertAppointment::getId, id)
                .set(ExpertAppointment::getRating, rating)
                .set(ExpertAppointment::getFeedback, feedback)
                .update();
    }
}
