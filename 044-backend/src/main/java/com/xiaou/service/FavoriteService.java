package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.Favorite;
import com.xiaou.entity.Homestay;

public interface FavoriteService extends IService<Favorite> {
    void addFavorite(Long userId, Long homestayId);
    void removeFavorite(Long userId, Long homestayId);
    boolean isFavorite(Long userId, Long homestayId);
    IPage<Homestay> pageFavorites(Long userId, Integer current, Integer size);
}
