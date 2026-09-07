package com.hospital.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hospital.common.BusinessException;
import com.hospital.entity.DoctorInfo;
import com.hospital.entity.DoctorSchedule;
import com.hospital.mapper.DoctorScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private DoctorScheduleMapper doctorScheduleMapper;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    public PageInfo<DoctorSchedule> page(Long departmentId, Long doctorId, Integer status, LocalDate scheduleDate, Integer pageNum, Integer pageSize, String role) {
        authService.assertAdmin(role);
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(doctorScheduleMapper.selectPage(departmentId, doctorId, status, scheduleDate));
    }

    public PageInfo<DoctorSchedule> myPage(Long userId, Integer status, LocalDate scheduleDate, Integer pageNum, Integer pageSize) {
        DoctorInfo doctor = doctorService.getByUserId(userId);
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(doctorScheduleMapper.selectMyPage(doctor.getId(), status, scheduleDate));
    }

    public List<DoctorSchedule> publicList(Long departmentId, Long doctorId, LocalDate scheduleDate) {
        return doctorScheduleMapper.selectPublicList(departmentId, doctorId, scheduleDate);
    }

    public DoctorSchedule getById(Long id) {
        DoctorSchedule schedule = doctorScheduleMapper.selectById(id);
        if (schedule == null) {
            throw new BusinessException("排班不存在");
        }
        return schedule;
    }

    public void save(DoctorSchedule entity, Long userId, String role) {
        authService.assertDoctor(role);
        if (entity == null || entity.getScheduleDate() == null || entity.getTotalSource() == null || entity.getTotalSource() <= 0) {
            throw new BusinessException("排班日期和号源数量不能为空");
        }
        DoctorInfo doctor;
        if ("DOCTOR".equals(role)) {
            doctor = doctorService.getByUserId(userId);
            entity.setDoctorId(doctor.getId());
            entity.setDepartmentId(doctor.getDepartmentId());
        } else {
            if (entity.getDoctorId() == null) {
                throw new BusinessException("请选择医生");
            }
            doctor = doctorService.getById(entity.getDoctorId());
            entity.setDepartmentId(doctor.getDepartmentId());
        }
        if (entity.getId() == null) {
            entity.setRemainingSource(entity.getTotalSource());
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setFee(entity.getFee() == null ? doctor.getConsultationFee() : entity.getFee());
            entity.setCreateTime(LocalDateTime.now());
            entity.setUpdateTime(LocalDateTime.now());
            doctorScheduleMapper.insert(entity);
            operationLogService.record(userId, role, "排班", "新增", "新增医生排班");
            return;
        }
        DoctorSchedule db = getById(entity.getId());
        if ("DOCTOR".equals(role) && !db.getDoctorId().equals(doctor.getId())) {
            throw new BusinessException("无权限编辑该排班");
        }
        int usedCount = db.getTotalSource() - db.getRemainingSource();
        if (entity.getTotalSource() < usedCount) {
            throw new BusinessException("总号源不能小于已预约数量");
        }
        db.setDepartmentId(entity.getDepartmentId());
        db.setDoctorId(entity.getDoctorId());
        db.setScheduleDate(entity.getScheduleDate());
        db.setTimeSlot(entity.getTimeSlot());
        db.setTotalSource(entity.getTotalSource());
        db.setRemainingSource(entity.getTotalSource() - usedCount);
        db.setStatus(entity.getStatus() == null ? db.getStatus() : entity.getStatus());
        db.setFee(entity.getFee() == null ? db.getFee() : entity.getFee());
        db.setClinicRoom(entity.getClinicRoom());
        db.setUpdateTime(LocalDateTime.now());
        doctorScheduleMapper.update(db);
        operationLogService.record(userId, role, "排班", "编辑", "编辑医生排班");
    }

    public void delete(Long id, Long userId, String role) {
        authService.assertDoctor(role);
        DoctorSchedule schedule = getById(id);
        if ("DOCTOR".equals(role)) {
            DoctorInfo doctor = doctorService.getByUserId(userId);
            if (!schedule.getDoctorId().equals(doctor.getId())) {
                throw new BusinessException("无权限删除该排班");
            }
        }
        doctorScheduleMapper.deleteById(id);
        operationLogService.record(userId, role, "排班", "删除", "删除医生排班");
    }

    public long countByDoctorId(Long doctorId) {
        return doctorScheduleMapper.countByDoctorId(doctorId);
    }
}
