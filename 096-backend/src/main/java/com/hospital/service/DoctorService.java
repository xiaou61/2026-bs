package com.hospital.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hospital.common.BusinessException;
import com.hospital.entity.DepartmentInfo;
import com.hospital.entity.DoctorInfo;
import com.hospital.entity.SysUser;
import com.hospital.mapper.DoctorInfoMapper;
import com.hospital.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class DoctorService {

    @Autowired
    private DoctorInfoMapper doctorInfoMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    public PageInfo<DoctorInfo> page(Long departmentId, String keyword, Integer status, Integer pageNum, Integer pageSize, String role) {
        authService.assertAdmin(role);
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(doctorInfoMapper.selectPage(departmentId, keyword, status));
    }

    public List<DoctorInfo> publicList(Long departmentId, String keyword) {
        return doctorInfoMapper.selectPublicList(departmentId, keyword);
    }

    public List<DoctorInfo> enabledList() {
        return doctorInfoMapper.selectEnabled();
    }

    public DoctorInfo getById(Long id) {
        DoctorInfo doctor = doctorInfoMapper.selectById(id);
        if (doctor == null) {
            throw new BusinessException("医生不存在");
        }
        return doctor;
    }

    public DoctorInfo getByUserId(Long userId) {
        DoctorInfo doctor = doctorInfoMapper.selectByUserId(userId);
        if (doctor == null) {
            throw new BusinessException("医生档案不存在");
        }
        return doctor;
    }

    public void save(DoctorInfo entity, Long userId, String role) {
        authService.assertAdmin(role);
        if (entity == null || entity.getDepartmentId() == null || !StringUtils.hasText(entity.getDoctorName())) {
            throw new BusinessException("医生姓名和所属科室不能为空");
        }
        DepartmentInfo department = departmentService.getById(entity.getDepartmentId());
        if (department.getStatus() == null || department.getStatus() != 1) {
            throw new BusinessException("所属科室不可用");
        }
        if (entity.getId() == null) {
            if (!StringUtils.hasText(entity.getUsername())) {
                throw new BusinessException("医生登录账号不能为空");
            }
            if (sysUserMapper.selectByUsername(entity.getUsername()) != null) {
                throw new BusinessException("登录账号已存在");
            }
            SysUser doctorUser = new SysUser();
            doctorUser.setUsername(entity.getUsername());
            doctorUser.setPassword(StringUtils.hasText(entity.getPassword()) ? entity.getPassword() : "123456");
            doctorUser.setNickname(StringUtils.hasText(entity.getNickname()) ? entity.getNickname() : entity.getDoctorName());
            doctorUser.setPhone(entity.getPhone());
            doctorUser.setEmail(entity.getEmail());
            doctorUser.setGender(entity.getGender());
            doctorUser.setRole("DOCTOR");
            doctorUser.setStatus(1);
            doctorUser.setBalance(BigDecimal.ZERO);
            doctorUser.setCreateTime(LocalDateTime.now());
            doctorUser.setUpdateTime(LocalDateTime.now());
            sysUserMapper.insert(doctorUser);

            entity.setUserId(doctorUser.getId());
            entity.setConsultationFee(entity.getConsultationFee() == null ? BigDecimal.valueOf(20) : entity.getConsultationFee());
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setVisitCount(0);
            entity.setCreateTime(LocalDateTime.now());
            entity.setUpdateTime(LocalDateTime.now());
            doctorInfoMapper.insert(entity);
            operationLogService.record(userId, role, "医生", "新增", "新增医生档案");
            return;
        }
        DoctorInfo db = getById(entity.getId());
        SysUser doctorUser = authService.getById(db.getUserId());
        if (StringUtils.hasText(entity.getUsername()) && !entity.getUsername().equals(doctorUser.getUsername())) {
            SysUser exists = sysUserMapper.selectByUsername(entity.getUsername());
            if (exists != null && !exists.getId().equals(doctorUser.getId())) {
                throw new BusinessException("登录账号已存在");
            }
            doctorUser.setUsername(entity.getUsername());
        }
        if (StringUtils.hasText(entity.getPassword())) {
            doctorUser.setPassword(entity.getPassword());
        }
        doctorUser.setNickname(StringUtils.hasText(entity.getNickname()) ? entity.getNickname() : entity.getDoctorName());
        doctorUser.setPhone(entity.getPhone());
        doctorUser.setEmail(entity.getEmail());
        doctorUser.setGender(entity.getGender());
        doctorUser.setUpdateTime(LocalDateTime.now());
        sysUserMapper.update(doctorUser);

        db.setDepartmentId(entity.getDepartmentId());
        db.setDoctorName(entity.getDoctorName());
        db.setTitle(entity.getTitle());
        db.setGender(entity.getGender());
        db.setSpecialty(entity.getSpecialty());
        db.setIntroduction(entity.getIntroduction());
        db.setAvatar(entity.getAvatar());
        db.setConsultationFee(entity.getConsultationFee());
        db.setStatus(entity.getStatus() == null ? db.getStatus() : entity.getStatus());
        db.setUpdateTime(LocalDateTime.now());
        doctorInfoMapper.update(db);
        operationLogService.record(userId, role, "医生", "编辑", "编辑医生档案");
    }

    public void delete(Long id, Long userId, String role) {
        authService.assertAdmin(role);
        DoctorInfo doctor = getById(id);
        doctorInfoMapper.deleteById(id);
        sysUserMapper.deleteById(doctor.getUserId());
        operationLogService.record(userId, role, "医生", "删除", "删除医生档案");
    }

    public long countAll() {
        return doctorInfoMapper.countAll();
    }

    public List<Map<String, Object>> hotDoctorRank() {
        return doctorInfoMapper.selectHotDoctorRank();
    }
}
