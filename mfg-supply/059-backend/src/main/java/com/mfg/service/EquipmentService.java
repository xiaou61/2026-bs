package com.mfg.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mfg.entity.Equipment;
import com.mfg.entity.EquipmentCategory;
import com.mfg.mapper.EquipmentCategoryMapper;
import com.mfg.mapper.EquipmentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EquipmentService {

    @Resource
    private EquipmentMapper equipmentMapper;

    @Resource
    private EquipmentCategoryMapper equipmentCategoryMapper;

    public Page<Equipment> page(Integer pageNum, Integer pageSize, String name, String status, Long categoryId) {
        QueryWrapper<Equipment> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            wrapper.like("name", name);
        }
        if (StrUtil.isNotBlank(status)) {
            wrapper.eq("status", status);
        }
        if (categoryId != null) {
            wrapper.eq("category_id", categoryId);
        }
        wrapper.orderByDesc("create_time");
        Page<Equipment> page = equipmentMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        page.getRecords().forEach(e -> {
            if (e.getCategoryId() != null) {
                EquipmentCategory cat = equipmentCategoryMapper.selectById(e.getCategoryId());
                if (cat != null) {
                    e.setCategoryName(cat.getName());
                }
            }
        });
        return page;
    }

    public void add(Equipment equipment) {
        equipmentMapper.insert(equipment);
    }

    public void update(Equipment equipment) {
        equipmentMapper.updateById(equipment);
    }

    public void delete(Long id) {
        equipmentMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        Equipment equipment = new Equipment();
        equipment.setId(id);
        equipment.setStatus(status);
        equipmentMapper.updateById(equipment);
    }
}
