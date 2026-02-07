package com.milk.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.milk.entity.MilkProduct;
import com.milk.mapper.MilkProductMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MilkProductService {

    @Resource
    private MilkProductMapper milkProductMapper;

    public List<MilkProduct> list(Long categoryId) {
        QueryWrapper<MilkProduct> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        if (categoryId != null) {
            wrapper.eq("category_id", categoryId);
        }
        wrapper.orderByDesc("create_time");
        return milkProductMapper.selectList(wrapper);
    }

    public Page<MilkProduct> page(Integer pageNum, Integer pageSize, String name, Long categoryId) {
        QueryWrapper<MilkProduct> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            wrapper.like("name", name);
        }
        if (categoryId != null) {
            wrapper.eq("category_id", categoryId);
        }
        wrapper.orderByDesc("create_time");
        return milkProductMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public MilkProduct getById(Long id) {
        return milkProductMapper.selectById(id);
    }

    public void save(MilkProduct product) {
        if (product.getId() == null) {
            milkProductMapper.insert(product);
        } else {
            milkProductMapper.updateById(product);
        }
    }

    public void deleteById(Long id) {
        milkProductMapper.deleteById(id);
    }
}
