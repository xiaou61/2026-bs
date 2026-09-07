package com.mfg.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mfg.entity.Material;
import com.mfg.mapper.MaterialMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MaterialService {

    @Resource
    private MaterialMapper materialMapper;

    public Page<Material> page(Integer pageNum, Integer pageSize, String name) {
        QueryWrapper<Material> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            wrapper.like("name", name);
        }
        wrapper.orderByDesc("create_time");
        return materialMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public void add(Material material) {
        materialMapper.insert(material);
    }

    public void update(Material material) {
        materialMapper.updateById(material);
    }

    public void delete(Long id) {
        materialMapper.deleteById(id);
    }
}
