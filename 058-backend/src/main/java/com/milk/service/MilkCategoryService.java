package com.milk.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.milk.entity.MilkCategory;
import com.milk.mapper.MilkCategoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MilkCategoryService {

    @Resource
    private MilkCategoryMapper milkCategoryMapper;

    public List<MilkCategory> list() {
        return milkCategoryMapper.selectList(new QueryWrapper<MilkCategory>().eq("status", 1).orderByAsc("sort"));
    }

    public Page<MilkCategory> page(Integer pageNum, Integer pageSize, String name) {
        QueryWrapper<MilkCategory> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            wrapper.like("name", name);
        }
        wrapper.orderByAsc("sort");
        return milkCategoryMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public void save(MilkCategory category) {
        if (category.getId() == null) {
            milkCategoryMapper.insert(category);
        } else {
            milkCategoryMapper.updateById(category);
        }
    }

    public void deleteById(Long id) {
        milkCategoryMapper.deleteById(id);
    }
}
