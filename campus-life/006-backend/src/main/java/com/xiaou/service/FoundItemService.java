package com.xiaou.service;

import com.xiaou.entity.FoundItem;
import com.xiaou.entity.ItemImage;
import com.xiaou.mapper.FoundItemMapper;
import com.xiaou.mapper.ItemImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FoundItemService {

    @Autowired
    private FoundItemMapper foundItemMapper;

    @Autowired
    private ItemImageMapper itemImageMapper;

    public Map<String, Object> getFoundItems(String keyword, Long categoryId, Integer status, 
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

        int total = foundItemMapper.countAll(params);

        if (page != null && pageSize != null) {
            params.put("offset", (page - 1) * pageSize);
            params.put("limit", pageSize);
        }

        List<FoundItem> items = foundItemMapper.findAll(params);
        for (FoundItem item : items) {
            List<ItemImage> images = itemImageMapper.findByItem("found", item.getId());
            item.setImages(images);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("items", items);
        result.put("total", total);
        return result;
    }

    public FoundItem getFoundItemById(Long id) {
        FoundItem item = foundItemMapper.findById(id);
        if (item != null) {
            List<ItemImage> images = itemImageMapper.findByItem("found", id);
            item.setImages(images);
            foundItemMapper.incrementViews(id);
        }
        return item;
    }

    public List<FoundItem> getUserFoundItems(Long userId) {
        List<FoundItem> items = foundItemMapper.findByUserId(userId);
        for (FoundItem item : items) {
            List<ItemImage> images = itemImageMapper.findByItem("found", item.getId());
            item.setImages(images);
        }
        return items;
    }

    @Transactional
    public void addFoundItem(FoundItem foundItem, List<String> imageUrls) {
        if (foundItem.getStatus() == null) {
            foundItem.setStatus(0);
        }
        if (foundItem.getViews() == null) {
            foundItem.setViews(0);
        }
        foundItemMapper.insert(foundItem);

        if (imageUrls != null && !imageUrls.isEmpty()) {
            for (int i = 0; i < imageUrls.size(); i++) {
                ItemImage image = new ItemImage();
                image.setItemType("found");
                image.setItemId(foundItem.getId());
                image.setImageUrl(imageUrls.get(i));
                image.setSort(i);
                itemImageMapper.insert(image);
            }
        }
    }

    @Transactional
    public void updateFoundItem(FoundItem foundItem, List<String> imageUrls) {
        foundItemMapper.update(foundItem);

        if (imageUrls != null) {
            itemImageMapper.deleteByItem("found", foundItem.getId());
            for (int i = 0; i < imageUrls.size(); i++) {
                ItemImage image = new ItemImage();
                image.setItemType("found");
                image.setItemId(foundItem.getId());
                image.setImageUrl(imageUrls.get(i));
                image.setSort(i);
                itemImageMapper.insert(image);
            }
        }
    }

    @Transactional
    public void deleteFoundItem(Long id) {
        itemImageMapper.deleteByItem("found", id);
        foundItemMapper.deleteById(id);
    }

    public void updateStatus(Long id, Integer status) {
        foundItemMapper.updateStatus(id, status);
    }
}

