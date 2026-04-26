package com.petcafe.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petcafe.common.BusinessException;
import com.petcafe.common.PageResult;
import com.petcafe.entity.MenuCategory;
import com.petcafe.entity.MenuItem;
import com.petcafe.mapper.MenuCategoryMapper;
import com.petcafe.mapper.MenuItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MenuService {

    @Resource
    private MenuItemMapper menuMapper;

    @Resource
    private MenuCategoryMapper categoryMapper;

    public PageResult<MenuItem> page(Integer pageNum, Integer pageSize, String name, Long categoryId, Integer status) {
        Page<MenuItem> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<MenuItem> wrapper = new LambdaQueryWrapper<MenuItem>()
                .like(StringUtils.hasText(name), MenuItem::getName, name == null ? null : name.trim())
                .eq(categoryId != null, MenuItem::getCategoryId, categoryId)
                .eq(status != null, MenuItem::getStatus, status)
                .orderByDesc(MenuItem::getId);
        Page<MenuItem> resultPage = menuMapper.selectPage(page, wrapper);
        fillCategoryName(resultPage.getRecords());
        PageResult<MenuItem> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<MenuItem> listAll() {
        List<MenuItem> list = menuMapper.selectList(new LambdaQueryWrapper<MenuItem>().orderByDesc(MenuItem::getId));
        fillCategoryName(list);
        return list;
    }

    public List<MenuItem> publicList() {
        List<MenuItem> list = menuMapper.selectList(new LambdaQueryWrapper<MenuItem>()
                .eq(MenuItem::getStatus, 1)
                .gt(MenuItem::getStock, 0)
                .orderByDesc(MenuItem::getRecommendFlag)
                .orderByDesc(MenuItem::getId));
        fillCategoryName(list);
        return list;
    }

    public MenuItem getById(Long id) {
        MenuItem menu = menuMapper.selectById(id);
        if (menu == null) {
            throw new BusinessException("菜单不存在");
        }
        fillCategoryName(java.util.Collections.singletonList(menu));
        return menu;
    }

    public void save(MenuItem menu) {
        if (menu == null || !StringUtils.hasText(menu.getMenuNo()) || !StringUtils.hasText(menu.getName()) || menu.getCategoryId() == null) {
            throw new BusinessException("菜单信息不完整");
        }
        MenuCategory category = categoryMapper.selectById(menu.getCategoryId());
        if (category == null) {
            throw new BusinessException("分类不存在");
        }
        validateMenuNo(menu);
        if (menu.getId() == null) {
            menu.setStatus(menu.getStatus() == null ? 1 : menu.getStatus());
            menu.setStock(menu.getStock() == null ? 0 : menu.getStock());
            menu.setRecommendFlag(menu.getRecommendFlag() == null ? 0 : menu.getRecommendFlag());
            menuMapper.insert(menu);
            return;
        }
        MenuItem db = getById(menu.getId());
        db.setMenuNo(menu.getMenuNo().trim());
        db.setName(menu.getName().trim());
        db.setCategoryId(menu.getCategoryId());
        db.setTaste(StringUtils.hasText(menu.getTaste()) ? menu.getTaste().trim() : null);
        db.setCover(StringUtils.hasText(menu.getCover()) ? menu.getCover().trim() : null);
        db.setPrice(menu.getPrice());
        db.setStock(menu.getStock());
        db.setRecommendFlag(menu.getRecommendFlag() == null ? db.getRecommendFlag() : menu.getRecommendFlag());
        db.setDescription(StringUtils.hasText(menu.getDescription()) ? menu.getDescription().trim() : null);
        if (menu.getStatus() != null) {
            db.setStatus(menu.getStatus());
        }
        menuMapper.updateById(db);
    }

    public void deleteById(Long id) {
        menuMapper.deleteById(id);
    }

    public void deductStock(Long menuId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new BusinessException("数量不合法");
        }
        MenuItem menu = menuMapper.selectById(menuId);
        if (menu == null || menu.getStatus() == null || menu.getStatus() == 0) {
            throw new BusinessException("菜单不可下单");
        }
        int stock = menu.getStock() == null ? 0 : menu.getStock();
        if (stock < quantity) {
            throw new BusinessException("菜单库存不足");
        }
        menu.setStock(stock - quantity);
        menuMapper.updateById(menu);
    }

    public Long countAll() {
        return menuMapper.selectCount(null);
    }

    private void validateMenuNo(MenuItem menu) {
        MenuItem exist = menuMapper.selectOne(new LambdaQueryWrapper<MenuItem>()
                .eq(MenuItem::getMenuNo, menu.getMenuNo().trim())
                .last("limit 1"));
        if (exist != null && (menu.getId() == null || !exist.getId().equals(menu.getId()))) {
            throw new BusinessException("菜单编号已存在");
        }
    }

    private void fillCategoryName(List<MenuItem> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        List<Long> categoryIds = list.stream().map(MenuItem::getCategoryId).distinct().collect(Collectors.toList());
        Map<Long, String> categoryMap = categoryMapper.selectList(new LambdaQueryWrapper<MenuCategory>().in(MenuCategory::getId, categoryIds))
                .stream()
                .collect(Collectors.toMap(MenuCategory::getId, MenuCategory::getName, (a, b) -> a, HashMap::new));
        list.forEach(item -> item.setCategoryName(categoryMap.get(item.getCategoryId())));
    }
}
