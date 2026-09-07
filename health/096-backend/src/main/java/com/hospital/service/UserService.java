package com.hospital.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hospital.common.BusinessException;
import com.hospital.entity.PatientProfile;
import com.hospital.entity.SysUser;
import com.hospital.mapper.PatientProfileMapper;
import com.hospital.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PatientProfileMapper patientProfileMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    public PageInfo<SysUser> page(String role, String keyword, Integer status, Integer pageNum, Integer pageSize, String currentRole) {
        authService.assertAdmin(currentRole);
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> list = sysUserMapper.selectPage(role, keyword, status);
        list.forEach(item -> item.setPassword(null));
        return new PageInfo<>(list);
    }

    public void save(SysUser entity, String currentRole) {
        authService.assertAdmin(currentRole);
        if (entity == null || !StringUtils.hasText(entity.getUsername()) || !StringUtils.hasText(entity.getNickname())) {
            throw new BusinessException("用户名和昵称不能为空");
        }
        if ("DOCTOR".equals(entity.getRole())) {
            throw new BusinessException("医生账号请在医生管理中维护");
        }
        if (entity.getId() == null) {
            if (!StringUtils.hasText(entity.getPassword())) {
                throw new BusinessException("新增用户密码不能为空");
            }
            if (sysUserMapper.selectByUsername(entity.getUsername()) != null) {
                throw new BusinessException("用户名已存在");
            }
            if (!StringUtils.hasText(entity.getRole())) {
                entity.setRole("PATIENT");
            }
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setBalance(entity.getBalance() == null ? BigDecimal.ZERO : entity.getBalance());
            entity.setCreateTime(LocalDateTime.now());
            entity.setUpdateTime(LocalDateTime.now());
            sysUserMapper.insert(entity);
            if ("PATIENT".equals(entity.getRole())) {
                PatientProfile profile = new PatientProfile();
                profile.setUserId(entity.getId());
                profile.setRealName(entity.getNickname());
                profile.setCreateTime(LocalDateTime.now());
                profile.setUpdateTime(LocalDateTime.now());
                patientProfileMapper.insert(profile);
            }
            operationLogService.record(null, currentRole, "用户", "新增", "新增用户账号");
            return;
        }
        SysUser db = authService.getById(entity.getId());
        db.setNickname(entity.getNickname());
        db.setPhone(entity.getPhone());
        db.setEmail(entity.getEmail());
        db.setGender(entity.getGender());
        db.setAge(entity.getAge());
        if (StringUtils.hasText(entity.getPassword())) {
            db.setPassword(entity.getPassword());
        }
        if (entity.getStatus() != null) {
            db.setStatus(entity.getStatus());
        }
        db.setUpdateTime(LocalDateTime.now());
        sysUserMapper.update(db);
        operationLogService.record(db.getId(), currentRole, "用户", "编辑", "编辑用户账号");
    }

    public void updateStatus(Long id, Integer status, String currentRole) {
        authService.assertAdmin(currentRole);
        authService.getById(id);
        sysUserMapper.updateStatus(id, status);
        operationLogService.record(id, currentRole, "用户", "状态变更", "变更用户状态");
    }

    public void delete(Long id, String currentRole) {
        authService.assertAdmin(currentRole);
        authService.getById(id);
        sysUserMapper.deleteById(id);
        operationLogService.record(id, currentRole, "用户", "删除", "删除用户账号");
    }

    public void updateProfile(SysUser entity, Long userId) {
        SysUser db = authService.getById(userId);
        db.setNickname(entity.getNickname());
        db.setPhone(entity.getPhone());
        db.setEmail(entity.getEmail());
        db.setAvatar(entity.getAvatar());
        db.setGender(entity.getGender());
        db.setAge(entity.getAge());
        db.setUpdateTime(LocalDateTime.now());
        sysUserMapper.update(db);
        operationLogService.record(userId, db.getRole(), "个人中心", "编辑", "更新个人资料");
    }
}
