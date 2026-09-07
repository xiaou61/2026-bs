package com.harbin.tourism.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.harbin.tourism.entity.Favorite;
import com.harbin.tourism.mapper.FavoriteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    public void add(Long userId, Long targetId, String targetType) {
        Favorite existing = favoriteMapper.selectOne(new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, userId)
                .eq(Favorite::getTargetId, targetId)
                .eq(Favorite::getTargetType, targetType));
        if (existing == null) {
            Favorite favorite = new Favorite();
            favorite.setUserId(userId);
            favorite.setTargetId(targetId);
            favorite.setTargetType(targetType);
            favoriteMapper.insert(favorite);
        }
    }

    public void remove(Long userId, Long targetId, String targetType) {
        favoriteMapper.delete(new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, userId)
                .eq(Favorite::getTargetId, targetId)
                .eq(Favorite::getTargetType, targetType));
    }

    public boolean isFavorite(Long userId, Long targetId, String targetType) {
        return favoriteMapper.selectCount(new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, userId)
                .eq(Favorite::getTargetId, targetId)
                .eq(Favorite::getTargetType, targetType)) > 0;
    }

    public List<Favorite> getUserFavorites(Long userId, String targetType) {
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, userId);
        if (targetType != null) {
            wrapper.eq(Favorite::getTargetType, targetType);
        }
        wrapper.orderByDesc(Favorite::getCreatedAt);
        return favoriteMapper.selectList(wrapper);
    }
}
