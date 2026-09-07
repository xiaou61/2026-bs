package com.xiaou.service;

import com.xiaou.entity.ItemImage;
import com.xiaou.entity.LostItem;
import com.xiaou.mapper.ItemImageMapper;
import com.xiaou.mapper.LostItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LostItemService {

    @Autowired
    private LostItemMapper lostItemMapper;

    @Autowired
    private ItemImageMapper itemImageMapper;

    public Map<String, Object> getLostItems(String keyword, Long categoryId, Integer status, 
                                             String location, Integer page, Integer pageSize) {
        Map<String, Object> params = new HashMap<>();
        if (keyword != null && !keyword.isEmpty()) {
            params.put("keyword", keyword);
        }
        if (categoryId != null) {
            params.put("categoryId", categoryId);
        }
        if (status != null) {
            params.put("status", status);
        }
        if (location != null && !location.isEmpty()) {
            params.put("location", location);
        }

        int total = lostItemMapper.countAll(params);

        if (page != null && pageSize != null) {
            params.put("offset", (page - 1) * pageSize);
            params.put("limit", pageSize);
        }

        List<LostItem> items = lostItemMapper.findAll(params);
        for (LostItem item : items) {
            List<ItemImage> images = itemImageMapper.findByItem("lost", item.getId());
            item.setImages(images);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("items", items);
        result.put("total", total);
        return result;
    }

    public LostItem getLostItemById(Long id) {
        LostItem item = lostItemMapper.findById(id);
        if (item != null) {
            List<ItemImage> images = itemImageMapper.findByItem("lost", id);
            item.setImages(images);
            lostItemMapper.incrementViews(id);
        }
        return item;
    }

    public List<LostItem> getUserLostItems(Long userId) {
        List<LostItem> items = lostItemMapper.findByUserId(userId);
        for (LostItem item : items) {
            List<ItemImage> images = itemImageMapper.findByItem("lost", item.getId());
            item.setImages(images);
        }
        return items;
    }

    @Transactional
    public void addLostItem(LostItem lostItem, List<String> imageUrls) {
        if (lostItem.getStatus() == null) {
            lostItem.setStatus(0);
        }
        if (lostItem.getViews() == null) {
            lostItem.setViews(0);
        }
        lostItemMapper.insert(lostItem);

        if (imageUrls != null && !imageUrls.isEmpty()) {
            for (int i = 0; i < imageUrls.size(); i++) {
                ItemImage image = new ItemImage();
                image.setItemType("lost");
                image.setItemId(lostItem.getId());
                image.setImageUrl(imageUrls.get(i));
                image.setSort(i);
                itemImageMapper.insert(image);
            }
        }
    }

    @Transactional
    public void updateLostItem(LostItem lostItem, List<String> imageUrls) {
        lostItemMapper.update(lostItem);

        if (imageUrls != null) {
            itemImageMapper.deleteByItem("lost", lostItem.getId());
            for (int i = 0; i < imageUrls.size(); i++) {
                ItemImage image = new ItemImage();
                image.setItemType("lost");
                image.setItemId(lostItem.getId());
                image.setImageUrl(imageUrls.get(i));
                image.setSort(i);
                itemImageMapper.insert(image);
            }
        }
    }

    @Transactional
    public void deleteLostItem(Long id) {
        itemImageMapper.deleteByItem("lost", id);
        lostItemMapper.deleteById(id);
    }

    public void updateStatus(Long id, Integer status) {
        lostItemMapper.updateStatus(id, status);
    }
}

