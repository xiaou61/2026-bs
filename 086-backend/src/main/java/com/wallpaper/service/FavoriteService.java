package com.wallpaper.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wallpaper.common.BusinessException;
import com.wallpaper.entity.WallpaperFavorite;
import com.wallpaper.entity.WallpaperInfo;
import com.wallpaper.mapper.WallpaperFavoriteMapper;
import com.wallpaper.mapper.WallpaperInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    @Autowired
    private WallpaperFavoriteMapper favoriteMapper;

    @Autowired
    private WallpaperInfoMapper wallpaperInfoMapper;

    public List<WallpaperInfo> list(Long userId) {
        LambdaQueryWrapper<WallpaperFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WallpaperFavorite::getUserId, userId).orderByDesc(WallpaperFavorite::getId);
        List<WallpaperFavorite> favoriteList = favoriteMapper.selectList(wrapper);
        if (favoriteList.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> ids = favoriteList.stream().map(WallpaperFavorite::getWallpaperId).distinct().collect(Collectors.toList());
        return wallpaperInfoMapper.selectBatchIds(ids);
    }

    public void add(Long wallpaperId, Long userId) {
        WallpaperInfo wallpaper = wallpaperInfoMapper.selectById(wallpaperId);
        if (wallpaper == null) {
            throw new BusinessException("壁纸不存在");
        }
        LambdaQueryWrapper<WallpaperFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WallpaperFavorite::getWallpaperId, wallpaperId)
                .eq(WallpaperFavorite::getUserId, userId)
                .last("limit 1");
        if (favoriteMapper.selectOne(wrapper) != null) {
            throw new BusinessException("已收藏该壁纸");
        }
        WallpaperFavorite favorite = new WallpaperFavorite();
        favorite.setWallpaperId(wallpaperId);
        favorite.setUserId(userId);
        favoriteMapper.insert(favorite);
        wallpaper.setFavoriteCount((wallpaper.getFavoriteCount() == null ? 0 : wallpaper.getFavoriteCount()) + 1);
        wallpaperInfoMapper.updateById(wallpaper);
    }

    public void cancel(Long wallpaperId, Long userId) {
        LambdaQueryWrapper<WallpaperFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WallpaperFavorite::getWallpaperId, wallpaperId)
                .eq(WallpaperFavorite::getUserId, userId);
        int deleted = favoriteMapper.delete(wrapper);
        WallpaperInfo wallpaper = wallpaperInfoMapper.selectById(wallpaperId);
        if (deleted > 0 && wallpaper != null && wallpaper.getFavoriteCount() != null && wallpaper.getFavoriteCount() > 0) {
            wallpaper.setFavoriteCount(wallpaper.getFavoriteCount() - 1);
            wallpaperInfoMapper.updateById(wallpaper);
        }
    }
}
