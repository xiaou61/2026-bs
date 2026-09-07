package com.mfg.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mfg.entity.Equipment;
import com.mfg.entity.Product;
import com.mfg.entity.ProductionOrder;
import com.mfg.mapper.EquipmentMapper;
import com.mfg.mapper.ProductMapper;
import com.mfg.mapper.ProductionOrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;

@Service
public class ProductionOrderService {

    @Resource
    private ProductionOrderMapper productionOrderMapper;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private EquipmentMapper equipmentMapper;

    public Page<ProductionOrder> page(Integer pageNum, Integer pageSize, String orderNo, String status) {
        QueryWrapper<ProductionOrder> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(orderNo)) {
            wrapper.like("order_no", orderNo);
        }
        if (StrUtil.isNotBlank(status)) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        Page<ProductionOrder> page = productionOrderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        page.getRecords().forEach(o -> {
            if (o.getProductId() != null) {
                Product p = productMapper.selectById(o.getProductId());
                if (p != null) o.setProductName(p.getName());
            }
            if (o.getEquipmentId() != null) {
                Equipment e = equipmentMapper.selectById(o.getEquipmentId());
                if (e != null) o.setEquipmentName(e.getName());
            }
        });
        return page;
    }

    public void add(ProductionOrder order) {
        productionOrderMapper.insert(order);
    }

    public void update(ProductionOrder order) {
        productionOrderMapper.updateById(order);
    }

    public void delete(Long id) {
        productionOrderMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        ProductionOrder order = productionOrderMapper.selectById(id);
        if (order != null) {
            order.setStatus(status);
            if ("producing".equals(status) && order.getActualStartDate() == null) {
                order.setActualStartDate(LocalDate.now());
            }
            if ("completed".equals(status)) {
                order.setActualEndDate(LocalDate.now());
            }
            productionOrderMapper.updateById(order);
        }
    }
}
