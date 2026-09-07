package com.hospital.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hospital.common.BusinessException;
import com.hospital.entity.AppointmentRecord;
import com.hospital.entity.DoctorReview;
import com.hospital.entity.SysUser;
import com.hospital.mapper.DoctorReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private DoctorReviewMapper doctorReviewMapper;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    public PageInfo<DoctorReview> page(Long doctorId, String keyword, Integer pageNum, Integer pageSize, String role) {
        authService.assertAdmin(role);
        PageHelper.startPage(pageNum, pageSize);
        List<DoctorReview> list = doctorReviewMapper.selectPage(doctorId, keyword);
        return new PageInfo<>(list);
    }

    public List<DoctorReview> byDoctorId(Long doctorId) {
        return doctorReviewMapper.selectByDoctorId(doctorId);
    }

    public void save(DoctorReview entity, Long userId, String role) {
        authService.assertPatient(role);
        if (entity == null || entity.getAppointmentId() == null || entity.getRating() == null) {
            throw new BusinessException("评价信息不能为空");
        }
        AppointmentRecord appointment = appointmentService.getById(entity.getAppointmentId());
        if (!appointment.getUserId().equals(userId)) {
            throw new BusinessException(403, "无权限评价该预约");
        }
        if (appointment.getStatus() == null || appointment.getStatus() != 3) {
            throw new BusinessException("仅已完成预约可以评价");
        }
        if (doctorReviewMapper.selectByAppointmentId(entity.getAppointmentId()) != null) {
            throw new BusinessException("该预约已评价");
        }
        SysUser user = authService.getById(userId);
        DoctorReview review = new DoctorReview();
        review.setAppointmentId(entity.getAppointmentId());
        review.setDoctorId(appointment.getDoctorId());
        review.setUserId(userId);
        review.setRating(entity.getRating());
        review.setContent(StringUtils.hasText(entity.getContent()) ? entity.getContent() : "本次就诊体验良好");
        review.setStatus(1);
        review.setPatientName(user.getNickname());
        review.setCreateTime(LocalDateTime.now());
        doctorReviewMapper.insert(review);
        operationLogService.record(userId, role, "评价", "新增", "新增医生评价");
    }

    public void delete(Long id, Long userId, String role) {
        authService.assertAdmin(role);
        doctorReviewMapper.deleteById(id);
        operationLogService.record(userId, role, "评价", "删除", "删除医生评价");
    }

    public long countByUserId(Long userId) {
        return doctorReviewMapper.countByUserId(userId);
    }

    public long countByDoctorId(Long doctorId) {
        return doctorReviewMapper.countByDoctorId(doctorId);
    }
}
