package com.mfg.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mfg.entity.Product;
import com.mfg.entity.ProductionOrder;
import com.mfg.entity.QualityInspection;
import com.mfg.mapper.ProductMapper;
import com.mfg.mapper.ProductionOrderMapper;
import com.mfg.mapper.QualityInspectionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class QualityInspectionService {

    @Resource
    private QualityInspectionMapper qualityInspectionMapper;

    @Resource
    private ProductionOrderMapper productionOrderMapper;

    @Resource
    private ProductMapper productMapper;

    public Page<QualityInspection> page(Integer pageNum, Integer pageSize, String result, Long orderId) {
        QueryWrapper<QualityInspection> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(result)) {
            wrapper.eq("result", result);
        }
        if (orderId != null) {
            wrapper.eq("order_id", orderId);
        }
        wrapper.orderByDesc("inspect_time");
        Page<QualityInspection> page = qualityInspectionMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        page.getRecords().forEach(q -> {
            if (q.getOrderId() != null) {
                ProductionOrder order = productionOrderMapper.selectById(q.getOrderId());
                if (order != null) q.setOrderNo(order.getOrderNo());
            }
            if (q.getProductId() != null) {
                Product p = productMapper.selectById(q.getProductId());
                if (p != null) q.setProductName(p.getName());
            }
        });
        return page;
    }

    public void add(QualityInspection inspection) {
        qualityInspectionMapper.insert(inspection);
    }

    public void update(QualityInspection inspection) {
        qualityInspectionMapper.updateById(inspection);
    }

    public void delete(Long id) {
        qualityInspectionMapper.deleteById(id);
    }
}
