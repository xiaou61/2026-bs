package com.mfg.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mfg.common.BusinessException;
import com.mfg.entity.Material;
import com.mfg.entity.MaterialStockRecord;
import com.mfg.mapper.MaterialMapper;
import com.mfg.mapper.MaterialStockRecordMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class MaterialStockRecordService {

    @Resource
    private MaterialStockRecordMapper materialStockRecordMapper;

    @Resource
    private MaterialMapper materialMapper;

    public Page<MaterialStockRecord> page(Integer pageNum, Integer pageSize, Long materialId, String type) {
        QueryWrapper<MaterialStockRecord> wrapper = new QueryWrapper<>();
        if (materialId != null) {
            wrapper.eq("material_id", materialId);
        }
        if (type != null) {
            wrapper.eq("type", type);
        }
        wrapper.orderByDesc("create_time");
        Page<MaterialStockRecord> page = materialStockRecordMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        page.getRecords().forEach(r -> {
            Material m = materialMapper.selectById(r.getMaterialId());
            if (m != null) {
                r.setMaterialName(m.getName());
            }
        });
        return page;
    }

    @Transactional
    public void stockIn(MaterialStockRecord record) {
        record.setType("in");
        materialStockRecordMapper.insert(record);
        Material material = materialMapper.selectById(record.getMaterialId());
        if (material != null) {
            material.setStockQuantity(material.getStockQuantity().add(record.getQuantity()));
            materialMapper.updateById(material);
        }
    }

    @Transactional
    public void stockOut(MaterialStockRecord record) {
        record.setType("out");
        Material material = materialMapper.selectById(record.getMaterialId());
        if (material == null) {
            throw new BusinessException("物料不存在");
        }
        if (material.getStockQuantity().compareTo(record.getQuantity()) < 0) {
            throw new BusinessException("库存不足");
        }
        materialStockRecordMapper.insert(record);
        material.setStockQuantity(material.getStockQuantity().subtract(record.getQuantity()));
        materialMapper.updateById(material);
    }
}
