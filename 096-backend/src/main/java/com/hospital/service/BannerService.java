package com.hospital.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hospital.common.BusinessException;
import com.hospital.entity.BannerInfo;
import com.hospital.mapper.BannerInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BannerService {

    @Autowired
    private BannerInfoMapper bannerInfoMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    public PageInfo<BannerInfo> page(String keyword, Integer status, Integer pageNum, Integer pageSize, String role) {
        authService.assertAdmin(role);
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(bannerInfoMapper.selectPage(keyword, status));
    }

    public List<BannerInfo> publicList() {
        return bannerInfoMapper.selectPublicList();
    }

    public void save(BannerInfo entity, Long userId, String role) {
        authService.assertAdmin(role);
        if (entity == null || !StringUtils.hasText(entity.getTitle())) {
            throw new BusinessException("轮播标题不能为空");
        }
        if (entity.getId() == null) {
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setSort(entity.getSort() == null ? 0 : entity.getSort());
            entity.setCreateTime(LocalDateTime.now());
            entity.setUpdateTime(LocalDateTime.now());
            bannerInfoMapper.insert(entity);
            operationLogService.record(userId, role, "轮播", "新增", "新增轮播");
            return;
        }
        entity.setUpdateTime(LocalDateTime.now());
        bannerInfoMapper.update(entity);
        operationLogService.record(userId, role, "轮播", "编辑", "编辑轮播");
    }

    public void delete(Long id, Long userId, String role) {
        authService.assertAdmin(role);
        bannerInfoMapper.deleteById(id);
        operationLogService.record(userId, role, "轮播", "删除", "删除轮播");
    }
}
