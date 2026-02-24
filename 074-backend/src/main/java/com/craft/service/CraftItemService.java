package com.craft.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.craft.common.BusinessException;
import com.craft.entity.CraftItem;
import com.craft.mapper.CraftItemMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CraftItemService {

    @Resource
    private CraftItemMapper itemMapper;

    public Page<CraftItem> page(Integer pageNum, Integer pageSize, String title, Long categoryId, String craftName, Integer status, Long sellerId) {
        QueryWrapper<CraftItem> wrapper = new QueryWrapper<>();
        if (title != null && !title.trim().isEmpty()) {
            wrapper.like("title", title.trim());
        }
        if (categoryId != null) {
            wrapper.eq("category_id", categoryId);
        }
        if (craftName != null && !craftName.trim().isEmpty()) {
            wrapper.like("craft_name", craftName.trim());
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (sellerId != null) {
            wrapper.eq("seller_id", sellerId);
        }
        wrapper.orderByDesc("id");
        return itemMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public Page<CraftItem> list(Integer pageNum, Integer pageSize, String title, Long categoryId, String craftName) {
        QueryWrapper<CraftItem> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        if (title != null && !title.trim().isEmpty()) {
            wrapper.like("title", title.trim());
        }
        if (categoryId != null) {
            wrapper.eq("category_id", categoryId);
        }
        if (craftName != null && !craftName.trim().isEmpty()) {
            wrapper.like("craft_name", craftName.trim());
        }
        wrapper.orderByDesc("id");
        return itemMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public CraftItem getById(Long id) {
        CraftItem item = itemMapper.selectById(id);
        if (item == null) {
            throw new BusinessException("商品不存在");
        }
        item.setViewCount((item.getViewCount() == null ? 0 : item.getViewCount()) + 1);
        itemMapper.updateById(item);
        return item;
    }

    public CraftItem mustGetById(Long id) {
        CraftItem item = itemMapper.selectById(id);
        if (item == null) {
            throw new BusinessException("商品不存在");
        }
        return item;
    }

    public void save(CraftItem item, Long userId, String role) {
        checkItemParams(item);
        if (item.getId() == null) {
            if (item.getSellerId() == null) {
                item.setSellerId(userId);
            }
            if (!"ADMIN".equals(role)) {
                item.setSellerId(userId);
            }
            if (item.getStatus() == null) {
                item.setStatus(1);
            }
            if (item.getViewCount() == null) {
                item.setViewCount(0);
            }
            if (item.getSoldCount() == null) {
                item.setSoldCount(0);
            }
            itemMapper.insert(item);
        } else {
            CraftItem db = itemMapper.selectById(item.getId());
            if (db == null) {
                throw new BusinessException("商品不存在");
            }
            if (!"ADMIN".equals(role) && !db.getSellerId().equals(userId)) {
                throw new BusinessException("无权限修改该商品");
            }
            if (item.getViewCount() == null) {
                item.setViewCount(db.getViewCount());
            }
            if (item.getSoldCount() == null) {
                item.setSoldCount(db.getSoldCount());
            }
            if (item.getStatus() == null) {
                item.setStatus(db.getStatus());
            }
            item.setSellerId(db.getSellerId());
            itemMapper.updateById(item);
        }
    }

    public void updateStatus(Long id, Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException("商品状态不合法");
        }
        CraftItem item = mustGetById(id);
        item.setStatus(status);
        itemMapper.updateById(item);
    }

    public void deleteById(Long id, Long userId, String role) {
        CraftItem item = mustGetById(id);
        if (!"ADMIN".equals(role) && !item.getSellerId().equals(userId)) {
            throw new BusinessException("无权限删除该商品");
        }
        itemMapper.deleteById(id);
    }

    public void decreaseStockAndIncreaseSold(Long itemId, Integer quantity) {
        CraftItem item = mustGetById(itemId);
        int currentStock = item.getStock() == null ? 0 : item.getStock();
        if (currentStock < quantity) {
            throw new BusinessException("库存不足");
        }
        item.setStock(currentStock - quantity);
        item.setSoldCount((item.getSoldCount() == null ? 0 : item.getSoldCount()) + quantity);
        itemMapper.updateById(item);
    }

    public void restoreStockAndSold(Long itemId, Integer quantity) {
        CraftItem item = mustGetById(itemId);
        item.setStock((item.getStock() == null ? 0 : item.getStock()) + quantity);
        int sold = item.getSoldCount() == null ? 0 : item.getSoldCount();
        item.setSoldCount(Math.max(0, sold - quantity));
        itemMapper.updateById(item);
    }

    public List<CraftItem> hotItems(int limit) {
        return itemMapper.selectList(new QueryWrapper<CraftItem>()
                .eq("status", 1)
                .orderByDesc("sold_count")
                .last("limit " + limit));
    }

    private void checkItemParams(CraftItem item) {
        if (item == null) {
            throw new BusinessException("商品参数不能为空");
        }
        if (item.getCategoryId() == null) {
            throw new BusinessException("请选择商品分类");
        }
        if (item.getTitle() == null || item.getTitle().trim().isEmpty()) {
            throw new BusinessException("商品标题不能为空");
        }
        item.setTitle(item.getTitle().trim());
        if (item.getTitle().length() > 120) {
            throw new BusinessException("商品标题不能超过120字符");
        }
        if (item.getCraftName() == null || item.getCraftName().trim().isEmpty()) {
            throw new BusinessException("工艺名称不能为空");
        }
        item.setCraftName(item.getCraftName().trim());
        if (item.getCraftName().length() > 80) {
            throw new BusinessException("工艺名称不能超过80字符");
        }
        if (item.getWorkshopName() != null) {
            item.setWorkshopName(item.getWorkshopName().trim());
            if (item.getWorkshopName().length() > 80) {
                throw new BusinessException("作坊名称不能超过80字符");
            }
        }
        if (item.getCraftType() != null) {
            item.setCraftType(item.getCraftType().trim());
            if (item.getCraftType().length() > 40) {
                throw new BusinessException("工艺类型不能超过40字符");
            }
        }
        if (item.getDeliveryType() != null) {
            item.setDeliveryType(item.getDeliveryType().trim());
            if (item.getDeliveryType().length() > 30) {
                throw new BusinessException("配送方式不能超过30字符");
            }
        }
        if (item.getCover() != null) {
            item.setCover(item.getCover().trim());
            if (item.getCover().length() > 255) {
                throw new BusinessException("封面地址不能超过255字符");
            }
        }
        if (item.getDescription() != null) {
            item.setDescription(item.getDescription().trim());
        }
        if (item.getPrice() == null || item.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("商品价格必须大于0");
        }
        if (item.getStock() == null || item.getStock() < 0) {
            throw new BusinessException("商品库存不能小于0");
        }
        if (item.getStatus() != null && item.getStatus() != 0 && item.getStatus() != 1) {
            throw new BusinessException("商品状态不合法");
        }
    }
}


