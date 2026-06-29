package com.smartwarehouse.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smartwarehouse.common.BusinessException;
import com.smartwarehouse.entity.SysUser;
import com.smartwarehouse.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class SysUserService {

    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_PASSWORD_LENGTH = 64;
    private static final int MAX_PAGE_SIZE = 500;

    private final SysUserMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public PageInfo<SysUser> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        int size = pageSize == null ? 10 : Math.min(pageSize, MAX_PAGE_SIZE);
        PageHelper.startPage(pageNum == null ? 1 : pageNum, size);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    @Transactional
    public void save(SysUser entity) {
        if (entity == null) {
            throw new BusinessException("参数不能为空");
        }
        if (entity.getPassword() == null || entity.getPassword().isEmpty()) {
            entity.setPassword(generateRandomPassword());
        }
        validatePassword(entity.getPassword());
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        if (entity.getId() == null) mapper.insert(entity);
        else mapper.update(entity);
    }

    @Transactional
    public void delete(Long id) {
        mapper.deleteById(id);
    }

    @Transactional
    public void updateStatus(Long id, String status) {
        mapper.updateStatus(id, status);
    }

    private void validatePassword(String password) {
        if (!StringUtils.hasText(password) || password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            throw new BusinessException("密码长度必须在 " + MIN_PASSWORD_LENGTH + " 到 " + MAX_PASSWORD_LENGTH + " 位之间");
        }
    }

    private String generateRandomPassword() {
        byte[] bytes = new byte[12];
        new SecureRandom().nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }
}
