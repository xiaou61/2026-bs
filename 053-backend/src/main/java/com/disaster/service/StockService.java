package com.disaster.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.disaster.common.BusinessException;
import com.disaster.entity.Material;
import com.disaster.entity.Stock;
import com.disaster.entity.StockRecord;
import com.disaster.mapper.MaterialMapper;
import com.disaster.mapper.StockMapper;
import com.disaster.mapper.StockRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StockRecordMapper recordMapper;

    @Autowired
    private MaterialMapper materialMapper;

    public Page<Stock> page(int pageNum, int pageSize, Long warehouseId, Long materialId) {
        LambdaQueryWrapper<Stock> wrapper = new LambdaQueryWrapper<>();
        if (warehouseId != null) {
            wrapper.eq(Stock::getWarehouseId, warehouseId);
        }
        if (materialId != null) {
            wrapper.eq(Stock::getMaterialId, materialId);
        }
        return stockMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public List<Stock> listByWarehouse(Long warehouseId) {
        return stockMapper.selectList(new LambdaQueryWrapper<Stock>()
                .eq(Stock::getWarehouseId, warehouseId));
    }

    @Transactional
    public void stockIn(Long warehouseId, Long materialId, Integer quantity, String source, Long operatorId, String remark) {
        Stock stock = stockMapper.selectOne(new LambdaQueryWrapper<Stock>()
                .eq(Stock::getWarehouseId, warehouseId)
                .eq(Stock::getMaterialId, materialId));
        int before = 0;
        if (stock == null) {
            stock = new Stock();
            stock.setWarehouseId(warehouseId);
            stock.setMaterialId(materialId);
            stock.setQuantity(quantity);
            stockMapper.insert(stock);
        } else {
            before = stock.getQuantity();
            stock.setQuantity(stock.getQuantity() + quantity);
            stockMapper.updateById(stock);
        }
        StockRecord record = new StockRecord();
        record.setWarehouseId(warehouseId);
        record.setMaterialId(materialId);
        record.setType(1);
        record.setQuantity(quantity);
        record.setBeforeQuantity(before);
        record.setAfterQuantity(stock.getQuantity());
        record.setSource(source);
        record.setOperatorId(operatorId);
        record.setRemark(remark);
        recordMapper.insert(record);
    }

    @Transactional
    public void stockOut(Long warehouseId, Long materialId, Integer quantity, String source, Long operatorId, String remark) {
        Stock stock = stockMapper.selectOne(new LambdaQueryWrapper<Stock>()
                .eq(Stock::getWarehouseId, warehouseId)
                .eq(Stock::getMaterialId, materialId));
        if (stock == null || stock.getQuantity() < quantity) {
            throw new BusinessException("库存不足");
        }
        int before = stock.getQuantity();
        stock.setQuantity(stock.getQuantity() - quantity);
        stockMapper.updateById(stock);
        StockRecord record = new StockRecord();
        record.setWarehouseId(warehouseId);
        record.setMaterialId(materialId);
        record.setType(2);
        record.setQuantity(quantity);
        record.setBeforeQuantity(before);
        record.setAfterQuantity(stock.getQuantity());
        record.setSource(source);
        record.setOperatorId(operatorId);
        record.setRemark(remark);
        recordMapper.insert(record);
    }

    public Page<StockRecord> recordPage(int pageNum, int pageSize, Long warehouseId, Long materialId, Integer type) {
        LambdaQueryWrapper<StockRecord> wrapper = new LambdaQueryWrapper<>();
        if (warehouseId != null) {
            wrapper.eq(StockRecord::getWarehouseId, warehouseId);
        }
        if (materialId != null) {
            wrapper.eq(StockRecord::getMaterialId, materialId);
        }
        if (type != null) {
            wrapper.eq(StockRecord::getType, type);
        }
        wrapper.orderByDesc(StockRecord::getCreateTime);
        return recordMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public List<Stock> warning() {
        List<Stock> stocks = stockMapper.selectList(null);
        List<Stock> warnings = new ArrayList<>();
        for (Stock stock : stocks) {
            Material material = materialMapper.selectById(stock.getMaterialId());
            if (material != null && stock.getQuantity() < material.getSafeStock()) {
                warnings.add(stock);
            }
        }
        return warnings;
    }
}
