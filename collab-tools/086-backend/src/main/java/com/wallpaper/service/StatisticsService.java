package com.wallpaper.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wallpaper.entity.CarouselBanner;
import com.wallpaper.entity.SystemNotice;
import com.wallpaper.entity.SysUser;
import com.wallpaper.entity.WallpaperFavorite;
import com.wallpaper.entity.WallpaperInfo;
import com.wallpaper.mapper.CarouselBannerMapper;
import com.wallpaper.mapper.SystemNoticeMapper;
import com.wallpaper.mapper.SysUserMapper;
import com.wallpaper.mapper.WallpaperFavoriteMapper;
import com.wallpaper.mapper.WallpaperInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private WallpaperInfoMapper wallpaperInfoMapper;

    @Autowired
    private WallpaperFavoriteMapper favoriteMapper;

    @Autowired
    private CarouselBannerMapper bannerMapper;

    @Autowired
    private SystemNoticeMapper noticeMapper;

    @Autowired
    private AuthService authService;

    public Map<String, Object> dashboard(Long userId) {
        authService.assertAdmin(userId);
        Map<String, Object> result = new HashMap<>();
        result.put("userCount", sysUserMapper.selectCount(new LambdaQueryWrapper<SysUser>()));
        result.put("wallpaperCount", wallpaperInfoMapper.selectCount(new LambdaQueryWrapper<WallpaperInfo>()));
        result.put("pendingAuditCount", wallpaperInfoMapper.selectCount(new LambdaQueryWrapper<WallpaperInfo>().eq(WallpaperInfo::getAuditStatus, 0)));
        result.put("publishCount", wallpaperInfoMapper.selectCount(new LambdaQueryWrapper<WallpaperInfo>().eq(WallpaperInfo::getPublishStatus, 1)));
        result.put("favoriteCount", favoriteMapper.selectCount(new LambdaQueryWrapper<WallpaperFavorite>()));
        result.put("bannerCount", bannerMapper.selectCount(new LambdaQueryWrapper<CarouselBanner>()));
        result.put("noticeCount", noticeMapper.selectCount(new LambdaQueryWrapper<SystemNotice>()));
        List<WallpaperInfo> topWallpapers = wallpaperInfoMapper.selectList(new LambdaQueryWrapper<WallpaperInfo>()
                .orderByDesc(WallpaperInfo::getDownloadCount)
                .orderByDesc(WallpaperInfo::getFavoriteCount)
                .last("limit 5"));
        result.put("topWallpapers", topWallpapers);
        List<WallpaperInfo> latestUploads = wallpaperInfoMapper.selectList(new LambdaQueryWrapper<WallpaperInfo>()
                .orderByDesc(WallpaperInfo::getCreateTime)
                .last("limit 5"));
        result.put("latestUploads", latestUploads);
        return result;
    }
}
