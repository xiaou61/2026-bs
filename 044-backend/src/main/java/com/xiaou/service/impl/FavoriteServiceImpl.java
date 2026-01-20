package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.common.BusinessException;
import com.xiaou.entity.Favorite;
import com.xiaou.entity.Homestay;
import com.xiaou.mapper.FavoriteMapper;
import com.xiaou.mapper.HomestayMapper;
import com.xiaou.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements FavoriteService {

    private final HomestayMapper homestayMapper;

    @Override
    public void addFavorite(Long userId, Long homestayId) {
        if (isFavorite(userId, homestayId)) {
            throw new BusinessException("已收藏该民宿");
        }
        Homestay homestay = homestayMapper.selectById(homestayId);
        if (homestay == null) {
            throw new BusinessException("民宿不存在");
        }
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setHomestayId(homestayId);
        save(favorite);
    }

    @Override
    public void removeFavorite(Long userId, Long homestayId) {
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, userId).eq(Favorite::getHomestayId, homestayId);
        remove(wrapper);
    }

    @Override
    public boolean isFavorite(Long userId, Long homestayId) {
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, userId).eq(Favorite::getHomestayId, homestayId);
        return count(wrapper) > 0;
    }

    @Override
    public IPage<Homestay> pageFavorites(Long userId, Integer current, Integer size) {
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, userId).orderByDesc(Favorite::getCreateTime);
        IPage<Favorite> favPage = page(new Page<>(current, size), wrapper);
        List<Long> homestayIds = favPage.getRecords().stream().map(Favorite::getHomestayId).toList();
        if (homestayIds.isEmpty()) {
            return new Page<>(current, size);
        }
        List<Homestay> homestays = homestayMapper.selectBatchIds(homestayIds);
        IPage<Homestay> result = new Page<>(current, size, favPage.getTotal());
        result.setRecords(homestays);
        return result;
    }
}
