package com.craft.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.craft.common.BusinessException;
import com.craft.entity.Favorite;
import com.craft.entity.CraftItem;
import com.craft.mapper.FavoriteMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteService {

    @Resource
    private FavoriteMapper favoriteMapper;

    @Resource
    private CraftItemService itemService;

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

    public List<CraftItem> myFavorites(Long userId) {
        List<Favorite> favorites = favoriteMapper.selectList(new QueryWrapper<Favorite>()
                .eq("user_id", userId)
                .orderByDesc("id"));
        List<CraftItem> items = new ArrayList<>();
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

