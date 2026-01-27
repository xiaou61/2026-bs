package com.disaster.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.disaster.entity.Warehouse;
import com.disaster.mapper.WarehouseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseMapper warehouseMapper;

    public Page<Warehouse> page(int pageNum, int pageSize, String keyword) {
        LambdaQueryWrapper<Warehouse> wrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Warehouse::getName, keyword)
                    .or().like(Warehouse::getCode, keyword);
        }
        wrapper.orderByDesc(Warehouse::getCreateTime);
        return warehouseMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public List<Warehouse> list() {
        return warehouseMapper.selectList(new LambdaQueryWrapper<Warehouse>()
                .eq(Warehouse::getStatus, 1));
    }

    public Warehouse getById(Long id) {
        return warehouseMapper.selectById(id);
    }

    public void add(Warehouse warehouse) {
        warehouse.setStatus(1);
        warehouseMapper.insert(warehouse);
    }

    public void update(Warehouse warehouse) {
        warehouseMapper.updateById(warehouse);
    }

    public void delete(Long id) {
        warehouseMapper.deleteById(id);
    }
}
