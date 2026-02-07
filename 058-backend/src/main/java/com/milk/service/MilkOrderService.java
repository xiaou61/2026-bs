package com.milk.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.milk.common.BusinessException;
import com.milk.entity.MilkOrder;
import com.milk.mapper.MilkOrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MilkOrderService {

    @Resource
    private MilkOrderMapper milkOrderMapper;

    public Page<MilkOrder> myOrders(Long userId, Integer pageNum, Integer pageSize, Integer status) {
        QueryWrapper<MilkOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        return milkOrderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public Page<MilkOrder> page(Integer pageNum, Integer pageSize, String orderNo, Integer status) {
        QueryWrapper<MilkOrder> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(orderNo)) {
            wrapper.like("order_no", orderNo);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        return milkOrderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public MilkOrder getById(Long id) {
        return milkOrderMapper.selectById(id);
    }

    public void cancel(Long id) {
        MilkOrder order = milkOrderMapper.selectById(id);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if (order.getStatus() != 0) {
            throw new BusinessException("该订单状态无法取消");
        }
        order.setStatus(3);
        milkOrderMapper.updateById(order);
    }

    public Long countByStatus(Integer status) {
        return milkOrderMapper.selectCount(new QueryWrapper<MilkOrder>().eq("status", status));
    }

    public Long countAll() {
        return milkOrderMapper.selectCount(null);
    }
}
