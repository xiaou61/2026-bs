package com.xiaou.campusshare.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusshare.entity.SharedItem;
import com.xiaou.campusshare.mapper.SharedItemMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SharedItemService extends ServiceImpl<SharedItemMapper, SharedItem> {

    public List<SharedItem> getNearbyItems(BigDecimal latitude, BigDecimal longitude, String itemType) {
        LambdaQueryWrapper<SharedItem> wrapper = new LambdaQueryWrapper<>();
        if (itemType != null && !itemType.isEmpty()) {
            wrapper.eq(SharedItem::getItemType, itemType);
        }
        wrapper.eq(SharedItem::getStatus, 0);
        return list(wrapper);
    }

    public SharedItem getByItemNo(String itemNo) {
        LambdaQueryWrapper<SharedItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SharedItem::getItemNo, itemNo);
        return getOne(wrapper);
    }

    public boolean updateItemStatus(Long itemId, Integer status) {
        SharedItem item = getById(itemId);
        if (item == null) {
            return false;
        }
        item.setStatus(status);
        return updateById(item);
    }

    public boolean increaseUsageCount(Long itemId) {
        SharedItem item = getById(itemId);
        if (item == null) {
            return false;
        }
        item.setTotalUsageCount(item.getTotalUsageCount() + 1);
        return updateById(item);
    }
}

