package com.eldercare.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eldercare.entity.CheckItem;
import com.eldercare.mapper.CheckItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CheckItemService {

    @Autowired
    private CheckItemMapper checkItemMapper;

    public Page<CheckItem> page(int pageNum, int pageSize, String itemName, Integer status) {
        Page<CheckItem> page = new Page<>(pageNum, pageSize);
        QueryWrapper<CheckItem> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(itemName)) {
            wrapper.like("item_name", itemName);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        return checkItemMapper.selectPage(page, wrapper);
    }

    public List<CheckItem> listAll() {
        QueryWrapper<CheckItem> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1).orderByAsc("id");
        return checkItemMapper.selectList(wrapper);
    }

    public void add(CheckItem checkItem) {
        if (checkItem.getStatus() == null) {
            checkItem.setStatus(1);
        }
        checkItemMapper.insert(checkItem);
    }

    public void update(CheckItem checkItem) {
        checkItemMapper.updateById(checkItem);
    }

    public void delete(Long id) {
        checkItemMapper.deleteById(id);
    }
}
