package com.disaster.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.disaster.entity.Material;
import com.disaster.mapper.MaterialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MaterialService {

    @Autowired
    private MaterialMapper materialMapper;

    public Page<Material> page(int pageNum, int pageSize, Long categoryId, String keyword) {
        LambdaQueryWrapper<Material> wrapper = new LambdaQueryWrapper<>();
        if (categoryId != null) {
            wrapper.eq(Material::getCategoryId, categoryId);
        }
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Material::getName, keyword);
        }
        wrapper.orderByDesc(Material::getCreateTime);
        return materialMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public List<Material> list() {
        return materialMapper.selectList(new LambdaQueryWrapper<Material>()
                .eq(Material::getStatus, 1));
    }

    public Material getById(Long id) {
        return materialMapper.selectById(id);
    }

    public void add(Material material) {
        material.setStatus(1);
        materialMapper.insert(material);
    }

    public void update(Material material) {
        materialMapper.updateById(material);
    }

    public void delete(Long id) {
        materialMapper.deleteById(id);
    }
}
