package com.xiaou.service;

import com.xiaou.entity.Favorite;
import com.xiaou.entity.FoundItem;
import com.xiaou.entity.ItemImage;
import com.xiaou.entity.LostItem;
import com.xiaou.mapper.FavoriteMapper;
import com.xiaou.mapper.FoundItemMapper;
import com.xiaou.mapper.ItemImageMapper;
import com.xiaou.mapper.LostItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private LostItemMapper lostItemMapper;

    @Autowired
    private FoundItemMapper foundItemMapper;

    @Autowired
    private ItemImageMapper itemImageMapper;

    public void addFavorite(Favorite favorite) {
        Favorite existing = favoriteMapper.findOne(favorite.getUserId(), favorite.getItemType(), favorite.getItemId());
        if (existing != null) {
            throw new RuntimeException("已收藏过该物品");
        }
        favoriteMapper.insert(favorite);
    }

    public void removeFavorite(Long userId, String itemType, Long itemId) {
        favoriteMapper.deleteByUserAndItem(userId, itemType, itemId);
    }

    public Map<String, Object> getUserFavorites(Long userId) {
        List<Favorite> favorites = favoriteMapper.findByUserId(userId);
        List<Object> lostItems = new ArrayList<>();
        List<Object> foundItems = new ArrayList<>();

        for (Favorite fav : favorites) {
            if ("lost".equals(fav.getItemType())) {
                LostItem item = lostItemMapper.findById(fav.getItemId());
                if (item != null) {
                    List<ItemImage> images = itemImageMapper.findByItem("lost", item.getId());
                    item.setImages(images);
                    lostItems.add(item);
                }
            } else {
                FoundItem item = foundItemMapper.findById(fav.getItemId());
                if (item != null) {
                    List<ItemImage> images = itemImageMapper.findByItem("found", item.getId());
                    item.setImages(images);
                    foundItems.add(item);
                }
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("lostItems", lostItems);
        result.put("foundItems", foundItems);
        return result;
    }

    public boolean isFavorite(Long userId, String itemType, Long itemId) {
        Favorite favorite = favoriteMapper.findOne(userId, itemType, itemId);
        return favorite != null;
    }
}

