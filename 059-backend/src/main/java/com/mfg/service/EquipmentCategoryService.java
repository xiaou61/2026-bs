package com.mfg.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mfg.entity.EquipmentCategory;
import com.mfg.mapper.EquipmentCategoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EquipmentCategoryService {

    @Resource
    private EquipmentCategoryMapper equipmentCategoryMapper;

    public Page<EquipmentCategory> page(Integer pageNum, Integer pageSize, String name) {
        QueryWrapper<EquipmentCategory> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            wrapper.like("name", name);
        }
        wrapper.orderByDesc("create_time");
        return equipmentCategoryMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public List<EquipmentCategory> list() {
        return equipmentCategoryMapper.selectList(null);
    }

    public void add(EquipmentCategory category) {
        equipmentCategoryMapper.insert(category);
    }

    public void update(EquipmentCategory category) {
        equipmentCategoryMapper.updateById(category);
    }

    public void delete(Long id) {
        equipmentCategoryMapper.deleteById(id);
    }
}
