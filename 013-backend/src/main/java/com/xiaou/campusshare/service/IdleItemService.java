package com.xiaou.campusshare.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusshare.entity.IdleItem;
import com.xiaou.campusshare.mapper.IdleItemMapper;
import org.springframework.stereotype.Service;

@Service
public class IdleItemService extends ServiceImpl<IdleItemMapper, IdleItem> {

    public Page<IdleItem> getItemList(int page, int size, String category, String keyword) {
        LambdaQueryWrapper<IdleItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(IdleItem::getStatus, 1);
        wrapper.eq(IdleItem::getIsDeleted, 0);
        
        if (category != null && !category.isEmpty()) {
            wrapper.eq(IdleItem::getCategory, category);
        }
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like(IdleItem::getTitle, keyword)
                    .or().like(IdleItem::getDescription, keyword));
        }
        
        wrapper.orderByDesc(IdleItem::getCreateTime);
        return page(new Page<>(page, size), wrapper);
    }

    public boolean increaseViewCount(Long itemId) {
        IdleItem item = getById(itemId);
        if (item == null) {
            return false;
        }
        item.setViewCount(item.getViewCount() + 1);
        return updateById(item);
    }

    public boolean increaseOrderCount(Long itemId) {
        IdleItem item = getById(itemId);
        if (item == null) {
            return false;
        }
        item.setOrderCount(item.getOrderCount() + 1);
        return updateById(item);
    }
}

