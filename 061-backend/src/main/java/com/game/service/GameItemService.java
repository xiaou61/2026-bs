package com.game.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.game.common.BusinessException;
import com.game.entity.GameItem;
import com.game.mapper.GameItemMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class GameItemService {

    @Resource
    private GameItemMapper itemMapper;

    public Page<GameItem> page(Integer pageNum, Integer pageSize, String title, Long categoryId, String gameName, Integer status, Long sellerId) {
        QueryWrapper<GameItem> wrapper = new QueryWrapper<>();
        if (title != null && !title.trim().isEmpty()) {
            wrapper.like("title", title.trim());
        }
        if (categoryId != null) {
            wrapper.eq("category_id", categoryId);
        }
        if (gameName != null && !gameName.trim().isEmpty()) {
            wrapper.like("game_name", gameName.trim());
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

    public Page<GameItem> list(Integer pageNum, Integer pageSize, String title, Long categoryId, String gameName) {
        QueryWrapper<GameItem> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        if (title != null && !title.trim().isEmpty()) {
            wrapper.like("title", title.trim());
        }
        if (categoryId != null) {
            wrapper.eq("category_id", categoryId);
        }
        if (gameName != null && !gameName.trim().isEmpty()) {
            wrapper.like("game_name", gameName.trim());
        }
        wrapper.orderByDesc("id");
        return itemMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public GameItem getById(Long id) {
        GameItem item = itemMapper.selectById(id);
        if (item == null) {
            throw new BusinessException("商品不存在");
        }
        item.setViewCount((item.getViewCount() == null ? 0 : item.getViewCount()) + 1);
        itemMapper.updateById(item);
        return item;
    }

    public GameItem mustGetById(Long id) {
        GameItem item = itemMapper.selectById(id);
        if (item == null) {
            throw new BusinessException("商品不存在");
        }
        return item;
    }

    public void save(GameItem item, Long userId, String role) {
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
            GameItem db = itemMapper.selectById(item.getId());
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
        GameItem item = mustGetById(id);
        item.setStatus(status);
        itemMapper.updateById(item);
    }

    public void deleteById(Long id, Long userId, String role) {
        GameItem item = mustGetById(id);
        if (!"ADMIN".equals(role) && !item.getSellerId().equals(userId)) {
            throw new BusinessException("无权限删除该商品");
        }
        itemMapper.deleteById(id);
    }

    public void decreaseStockAndIncreaseSold(Long itemId, Integer quantity) {
        GameItem item = mustGetById(itemId);
        int currentStock = item.getStock() == null ? 0 : item.getStock();
        if (currentStock < quantity) {
            throw new BusinessException("库存不足");
        }
        item.setStock(currentStock - quantity);
        item.setSoldCount((item.getSoldCount() == null ? 0 : item.getSoldCount()) + quantity);
        itemMapper.updateById(item);
    }

    public void restoreStockAndSold(Long itemId, Integer quantity) {
        GameItem item = mustGetById(itemId);
        item.setStock((item.getStock() == null ? 0 : item.getStock()) + quantity);
        int sold = item.getSoldCount() == null ? 0 : item.getSoldCount();
        item.setSoldCount(Math.max(0, sold - quantity));
        itemMapper.updateById(item);
    }

    public List<GameItem> hotItems(int limit) {
        return itemMapper.selectList(new QueryWrapper<GameItem>()
                .eq("status", 1)
                .orderByDesc("sold_count")
                .last("limit " + limit));
    }

    private void checkItemParams(GameItem item) {
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
        if (item.getGameName() == null || item.getGameName().trim().isEmpty()) {
            throw new BusinessException("游戏名称不能为空");
        }
        item.setGameName(item.getGameName().trim());
        if (item.getGameName().length() > 80) {
            throw new BusinessException("游戏名称不能超过80字符");
        }
        if (item.getServerName() != null) {
            item.setServerName(item.getServerName().trim());
            if (item.getServerName().length() > 80) {
                throw new BusinessException("服务器名称不能超过80字符");
            }
        }
        if (item.getItemType() != null) {
            item.setItemType(item.getItemType().trim());
            if (item.getItemType().length() > 40) {
                throw new BusinessException("商品类型不能超过40字符");
            }
        }
        if (item.getTradeMode() != null) {
            item.setTradeMode(item.getTradeMode().trim());
            if (item.getTradeMode().length() > 30) {
                throw new BusinessException("交易模式不能超过30字符");
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
