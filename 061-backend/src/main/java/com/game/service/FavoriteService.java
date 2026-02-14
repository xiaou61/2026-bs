package com.game.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.game.common.BusinessException;
import com.game.entity.Favorite;
import com.game.entity.GameItem;
import com.game.mapper.FavoriteMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteService {

    @Resource
    private FavoriteMapper favoriteMapper;

    @Resource
    private GameItemService itemService;

    public Boolean toggle(Long userId, Long itemId) {
        if (itemId == null) {
            throw new BusinessException("商品ID不能为空");
        }
        itemService.mustGetById(itemId);
        Favorite favorite = favoriteMapper.selectOne(new QueryWrapper<Favorite>()
                .eq("user_id", userId)
                .eq("item_id", itemId));
        if (favorite != null) {
            favoriteMapper.deleteById(favorite.getId());
            return false;
        }
        Favorite entity = new Favorite();
        entity.setUserId(userId);
        entity.setItemId(itemId);
        favoriteMapper.insert(entity);
        return true;
    }

    public List<GameItem> myFavorites(Long userId) {
        List<Favorite> favorites = favoriteMapper.selectList(new QueryWrapper<Favorite>()
                .eq("user_id", userId)
                .orderByDesc("id"));
        List<GameItem> items = new ArrayList<>();
        for (Favorite favorite : favorites) {
            try {
                items.add(itemService.mustGetById(favorite.getItemId()));
            } catch (Exception ignored) {
            }
        }
        return items;
    }

    public Boolean check(Long userId, Long itemId) {
        if (itemId == null) {
            throw new BusinessException("商品ID不能为空");
        }
        Long count = favoriteMapper.selectCount(new QueryWrapper<Favorite>()
                .eq("user_id", userId)
                .eq("item_id", itemId));
        return count > 0;
    }
}
