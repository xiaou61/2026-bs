package com.mfg.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mfg.entity.Product;
import com.mfg.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductService {

    @Resource
    private ProductMapper productMapper;

    public Page<Product> page(Integer pageNum, Integer pageSize, String name) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            wrapper.like("name", name);
        }
        wrapper.orderByDesc("create_time");
        return productMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public List<Product> list() {
        return productMapper.selectList(null);
    }

    public void add(Product product) {
        productMapper.insert(product);
    }

    public void update(Product product) {
        productMapper.updateById(product);
    }

    public void delete(Long id) {
        productMapper.deleteById(id);
    }
}
