package com.hospital.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hospital.common.BusinessException;
import com.hospital.entity.AppointmentRecord;
import com.hospital.entity.DoctorInfo;
import com.hospital.entity.DoctorSchedule;
import com.hospital.entity.MedicalCard;
import com.hospital.entity.PaymentOrder;
import com.hospital.mapper.AppointmentRecordMapper;
import com.hospital.mapper.DoctorScheduleMapper;
import com.hospital.mapper.PaymentOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRecordMapper appointmentRecordMapper;

    @Autowired
    private PaymentOrderMapper paymentOrderMapper;

    @Autowired
    private DoctorScheduleMapper doctorScheduleMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private CardService cardService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private OperationLogService operationLogService;

    public PageInfo<AppointmentRecord> page(String keyword, Integer status, Long doctorId, Long departmentId, Integer pageNum, Integer pageSize, String role) {
        authService.assertAdmin(role);
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(appointmentRecordMapper.selectPage(keyword, status, doctorId, departmentId));
    }

    public PageInfo<AppointmentRecord> myPage(Long userId, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(appointmentRecordMapper.selectMyPage(userId, status));
    }

    public PageInfo<AppointmentRecord> doctorPage(Long userId, Integer status, Integer pageNum, Integer pageSize) {
        DoctorInfo doctor = doctorService.getByUserId(userId);
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(appointmentRecordMapper.selectDoctorPage(doctor.getId(), status));
    }

    public AppointmentRecord getById(Long id) {
        AppointmentRecord appointment = appointmentRecordMapper.selectById(id);
        if (appointment == null) {
            throw new BusinessException("挂号记录不存在");
        }
        return appointment;
    }

    @Transactional
    public Map<String, Object> create(Long scheduleId, Long cardId, String remark, Long userId, String role) {
        authService.assertPatient(role);
        DoctorSchedule schedule = doctorScheduleMapper.selectById(scheduleId);
        if (schedule == null) {
            throw new BusinessException("排班不存在");
        }
        if (schedule.getStatus() == null || schedule.getStatus() != 1) {
            throw new BusinessException("排班未开放预约");
        }
        if (schedule.getRemainingSource() == null || schedule.getRemainingSource() <= 0) {
            throw new BusinessException("当前排班已无剩余号源");
        }
        MedicalCard card = cardService.getOwnedCard(cardId, userId);
        int updated = doctorScheduleMapper.decreaseSource(scheduleId);
        if (updated <= 0) {
            throw new BusinessException("号源不足，请刷新后重试");
        }
        DoctorInfo doctor = doctorService.getById(schedule.getDoctorId());

        AppointmentRecord appointment = new AppointmentRecord();
        appointment.setAppointmentNo(generateNo("AP"));
        appointment.setUserId(userId);
        appointment.setCardId(cardId);
        appointment.setDepartmentId(schedule.getDepartmentId());
        appointment.setDoctorId(schedule.getDoctorId());
        appointment.setScheduleId(scheduleId);
        appointment.setPatientName(card.getPatientName());
        appointment.setContactPhone(card.getPhone());
        appointment.setRemark(remark);
        appointment.setStatus(0);
        appointment.setAppointmentDate(schedule.getScheduleDate());
        appointment.setTimeSlot(schedule.getTimeSlot());
        appointment.setAmount(schedule.getFee());
        appointment.setCreateTime(LocalDateTime.now());
        appointment.setUpdateTime(LocalDateTime.now());
        appointmentRecordMapper.insert(appointment);

        PaymentOrder order = new PaymentOrder();
        order.setOrderNo(generateNo("OD"));
        order.setAppointmentId(appointment.getId());
        order.setUserId(userId);
        order.setAmount(schedule.getFee());
        order.setStatus(0);
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        paymentOrderMapper.insert(order);
        appointmentRecordMapper.bindOrderId(appointment.getId(), order.getId());

        Map<String, Object> result = new HashMap<>();
        result.put("appointmentId", appointment.getId());
        result.put("appointmentNo", appointment.getAppointmentNo());
        result.put("orderId", order.getId());
        result.put("orderNo", order.getOrderNo());
        result.put("doctorName", doctor.getDoctorName());
        operationLogService.record(userId, role, "挂号", "创建", "创建挂号预约");
        return result;
    }

    @Transactional
    public void cancel(Long id, Long userId, String role) {
        AppointmentRecord appointment = getById(id);
        if ("PATIENT".equals(role) && !appointment.getUserId().equals(userId)) {
            throw new BusinessException("无权限取消该挂号");
        }
        if (appointment.getStatus() == null || (appointment.getStatus() != 0 && appointment.getStatus() != 1)) {
            throw new BusinessException("当前状态不可取消");
        }
        appointmentRecordMapper.updateStatus(id, 2, null, null);
        doctorScheduleMapper.increaseSource(appointment.getScheduleId());
        if (appointment.getOrderId() != null) {
            paymentOrderMapper.updateStatus(appointment.getOrderId(), 2);
        }
        operationLogService.record(userId, role, "挂号", "取消", "取消挂号预约");
    }

    public void finish(Long id, Long userId, String role) {
        authService.assertDoctor(role);
        AppointmentRecord appointment = getById(id);
        if ("DOCTOR".equals(role)) {
            DoctorInfo doctor = doctorService.getByUserId(userId);
            if (!appointment.getDoctorId().equals(doctor.getId())) {
                throw new BusinessException("无权限完成该挂号");
            }
        }
        if (appointment.getStatus() == null || appointment.getStatus() != 1) {
            throw new BusinessException("仅已支付预约可以完成");
        }
        appointmentRecordMapper.updateStatus(id, 3, null, LocalDateTime.now());
        operationLogService.record(userId, role, "挂号", "完成", "完成就诊预约");
    }

    public long countAll() {
        return appointmentRecordMapper.countAll();
    }

    public long countByUserId(Long userId) {
        return appointmentRecordMapper.countByUserId(userId);
    }

    public long countPaidByUserId(Long userId) {
        return appointmentRecordMapper.countPaidByUserId(userId);
    }

    public long countByDoctorId(Long doctorId) {
        return appointmentRecordMapper.countByDoctorId(doctorId);
    }

    public long countFinishedByDoctorId(Long doctorId) {
        return appointmentRecordMapper.countFinishedByDoctorId(doctorId);
    }

    private String generateNo(String prefix) {
        return prefix + System.currentTimeMillis() + ThreadLocalRandom.current().nextInt(100, 999);
    }
}
