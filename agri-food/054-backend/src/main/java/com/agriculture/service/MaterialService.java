package com.agriculture.service;

import com.agriculture.entity.AgriculturalMaterial;
import com.agriculture.entity.MaterialRecord;
import com.agriculture.mapper.AgriculturalMaterialMapper;
import com.agriculture.mapper.MaterialRecordMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class MaterialService extends ServiceImpl<AgriculturalMaterialMapper, AgriculturalMaterial> {

    @Autowired
    private MaterialRecordMapper recordMapper;

    public Page<AgriculturalMaterial> getPage(Integer pageNum, Integer pageSize, String name, String category) {
        Page<AgriculturalMaterial> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<AgriculturalMaterial> wrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            wrapper.like(AgriculturalMaterial::getName, name);
        }
        if (category != null && !category.isEmpty()) {
            wrapper.eq(AgriculturalMaterial::getCategory, category);
        }
        return this.page(page, wrapper);
    }

    @Transactional
    public void stockIn(Long materialId, Integer quantity, BigDecimal unitPrice, String purpose, Long operatorId) {
        AgriculturalMaterial material = this.getById(materialId);
        material.setStock(material.getStock() + quantity);
        this.updateById(material);
        MaterialRecord record = new MaterialRecord();
        record.setMaterialId(materialId);
        record.setType(1);
        record.setQuantity(quantity);
        record.setUnitPrice(unitPrice);
        record.setTotalPrice(unitPrice.multiply(new BigDecimal(quantity)));
        record.setPurpose(purpose);
        record.setOperatorId(operatorId);
        record.setRecordDate(LocalDate.now());
        recordMapper.insert(record);
    }

    @Transactional
    public void stockOut(Long materialId, Integer quantity, String purpose, Long operatorId) {
        AgriculturalMaterial material = this.getById(materialId);
        material.setStock(material.getStock() - quantity);
        this.updateById(material);
        MaterialRecord record = new MaterialRecord();
        record.setMaterialId(materialId);
        record.setType(2);
        record.setQuantity(quantity);
        record.setUnitPrice(material.getUnitPrice());
        record.setTotalPrice(material.getUnitPrice().multiply(new BigDecimal(quantity)));
        record.setPurpose(purpose);
        record.setOperatorId(operatorId);
        record.setRecordDate(LocalDate.now());
        recordMapper.insert(record);
    }

    public Page<MaterialRecord> getRecordPage(Integer pageNum, Integer pageSize, Long materialId) {
        Page<MaterialRecord> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<MaterialRecord> wrapper = new LambdaQueryWrapper<>();
        if (materialId != null) {
            wrapper.eq(MaterialRecord::getMaterialId, materialId);
        }
        wrapper.orderByDesc(MaterialRecord::getRecordDate);
        return recordMapper.selectPage(page, wrapper);
    }

    public List<AgriculturalMaterial> getWarningList() {
        return this.lambdaQuery().apply("stock <= warning_stock").list();
    }
}
