package com.eldercare.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eldercare.entity.CheckPackage;
import com.eldercare.entity.CheckPackageItem;
import com.eldercare.mapper.CheckPackageItemMapper;
import com.eldercare.mapper.CheckPackageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckPackageService {

    @Autowired
    private CheckPackageMapper checkPackageMapper;

    @Autowired
    private CheckPackageItemMapper checkPackageItemMapper;

    public Page<CheckPackage> page(int pageNum, int pageSize, String packageName, Integer status) {
        Page<CheckPackage> page = new Page<>(pageNum, pageSize);
        QueryWrapper<CheckPackage> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(packageName)) {
            wrapper.like("package_name", packageName);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        Page<CheckPackage> result = checkPackageMapper.selectPage(page, wrapper);
        result.getRecords().forEach(this::loadItemIds);
        return result;
    }

    public List<CheckPackage> listAll() {
        QueryWrapper<CheckPackage> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1).orderByAsc("id");
        List<CheckPackage> list = checkPackageMapper.selectList(wrapper);
        list.forEach(this::loadItemIds);
        return list;
    }

    public void add(CheckPackage checkPackage) {
        if (checkPackage.getStatus() == null) {
            checkPackage.setStatus(1);
        }
        checkPackageMapper.insert(checkPackage);
        savePackageItems(checkPackage.getId(), checkPackage.getItemIds());
    }

    public void update(CheckPackage checkPackage) {
        checkPackageMapper.updateById(checkPackage);
        QueryWrapper<CheckPackageItem> removeWrapper = new QueryWrapper<>();
        removeWrapper.eq("package_id", checkPackage.getId());
        checkPackageItemMapper.delete(removeWrapper);
        savePackageItems(checkPackage.getId(), checkPackage.getItemIds());
    }

    public void delete(Long id) {
        checkPackageMapper.deleteById(id);
        QueryWrapper<CheckPackageItem> wrapper = new QueryWrapper<>();
        wrapper.eq("package_id", id);
        checkPackageItemMapper.delete(wrapper);
    }

    private void loadItemIds(CheckPackage checkPackage) {
        QueryWrapper<CheckPackageItem> wrapper = new QueryWrapper<>();
        wrapper.eq("package_id", checkPackage.getId()).orderByAsc("sort");
        List<CheckPackageItem> relationList = checkPackageItemMapper.selectList(wrapper);
        List<Long> itemIds = relationList.stream().map(CheckPackageItem::getItemId).collect(Collectors.toList());
        checkPackage.setItemIds(itemIds);
    }

    private void savePackageItems(Long packageId, List<Long> itemIds) {
        if (itemIds == null) {
            itemIds = new ArrayList<>();
        }
        int sort = 1;
        for (Long itemId : itemIds) {
            CheckPackageItem relation = new CheckPackageItem();
            relation.setPackageId(packageId);
            relation.setItemId(itemId);
            relation.setSort(sort++);
            checkPackageItemMapper.insert(relation);
        }
    }
}
