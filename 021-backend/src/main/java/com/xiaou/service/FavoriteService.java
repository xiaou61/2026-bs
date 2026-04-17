package com.xiaou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.vo.FavoriteVO;

public interface FavoriteService {

    void addFavorite(Long userId, Long productId);

    void removeFavorite(Long userId, Long productId);

    Page<FavoriteVO> getMyFavorites(Long userId, Integer current, Integer size);

    boolean checkFavorite(Long userId, Long productId);
}
