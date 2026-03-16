package com.wallpaper.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wallpaper.common.BusinessException;
import com.wallpaper.dto.AuditDTO;
import com.wallpaper.entity.WallpaperAudit;
import com.wallpaper.entity.WallpaperInfo;
import com.wallpaper.mapper.WallpaperAuditMapper;
import com.wallpaper.mapper.WallpaperInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class AuditService {

    @Autowired
    private WallpaperInfoMapper wallpaperInfoMapper;

    @Autowired
    private WallpaperAuditMapper wallpaperAuditMapper;

    @Autowired
    private AuthService authService;

    public IPage<WallpaperInfo> list(String title, Integer auditStatus, Integer pageNum, Integer pageSize, Long userId) {
        authService.assertAdmin(userId);
        Page<WallpaperInfo> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<WallpaperInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(title), WallpaperInfo::getTitle, title)
                .eq(auditStatus != null, WallpaperInfo::getAuditStatus, auditStatus)
                .orderByAsc(WallpaperInfo::getAuditStatus)
                .orderByDesc(WallpaperInfo::getId);
        return wallpaperInfoMapper.selectPage(page, wrapper);
    }

    public void submit(AuditDTO auditDTO, Long userId) {
        authService.assertAdmin(userId);
        if (auditDTO.getWallpaperId() == null || auditDTO.getAuditStatus() == null) {
            throw new BusinessException("审核参数不能为空");
        }
        if (auditDTO.getAuditStatus() == 2 && !StringUtils.hasText(auditDTO.getAuditRemark())) {
            throw new BusinessException("驳回时请输入审核说明");
        }
        WallpaperInfo wallpaper = wallpaperInfoMapper.selectById(auditDTO.getWallpaperId());
        if (wallpaper == null) {
            throw new BusinessException("壁纸不存在");
        }
        wallpaper.setAuditStatus(auditDTO.getAuditStatus());
        wallpaper.setPublishStatus(auditDTO.getAuditStatus() == 1 ? 1 : 0);
        wallpaperInfoMapper.updateById(wallpaper);

        WallpaperAudit audit = new WallpaperAudit();
        audit.setWallpaperId(auditDTO.getWallpaperId());
        audit.setAuditStatus(auditDTO.getAuditStatus());
        audit.setAuditRemark(auditDTO.getAuditRemark());
        audit.setAuditorId(userId);
        wallpaperAuditMapper.insert(audit);
    }

    public List<WallpaperAudit> recordList(Long wallpaperId, Long userId) {
        authService.assertAdmin(userId);
        LambdaQueryWrapper<WallpaperAudit> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WallpaperAudit::getWallpaperId, wallpaperId).orderByDesc(WallpaperAudit::getId);
        return wallpaperAuditMapper.selectList(wrapper);
    }
}
