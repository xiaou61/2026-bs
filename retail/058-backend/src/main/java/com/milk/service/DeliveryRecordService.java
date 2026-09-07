package com.milk.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.milk.common.BusinessException;
import com.milk.entity.DeliveryRecord;
import com.milk.entity.MilkOrder;
import com.milk.mapper.DeliveryRecordMapper;
import com.milk.mapper.MilkOrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeliveryRecordService {

    @Resource
    private DeliveryRecordMapper deliveryRecordMapper;

    @Resource
    private MilkOrderMapper milkOrderMapper;

    public List<DeliveryRecord> todayTasks(Long deliveryUserId) {
        return deliveryRecordMapper.selectList(
                new QueryWrapper<DeliveryRecord>()
                        .eq("delivery_user_id", deliveryUserId)
                        .eq("status", 0)
        );
    }

    public Page<DeliveryRecord> history(Long deliveryUserId, Integer pageNum, Integer pageSize) {
        QueryWrapper<DeliveryRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("delivery_user_id", deliveryUserId);
        wrapper.ne("status", 0);
        wrapper.orderByDesc("delivery_time");
        return deliveryRecordMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public void complete(Long id) {
        DeliveryRecord record = deliveryRecordMapper.selectById(id);
        if (record == null) {
            throw new BusinessException("配送记录不存在");
        }
        record.setStatus(1);
        record.setDeliveryTime(LocalDateTime.now());
        deliveryRecordMapper.updateById(record);
        MilkOrder order = milkOrderMapper.selectById(record.getOrderId());
        if (order != null) {
            order.setStatus(2);
            milkOrderMapper.updateById(order);
        }
    }

    public void exception(Long id, String remark) {
        DeliveryRecord record = deliveryRecordMapper.selectById(id);
        if (record == null) {
            throw new BusinessException("配送记录不存在");
        }
        record.setStatus(2);
        record.setRemark(remark);
        record.setDeliveryTime(LocalDateTime.now());
        deliveryRecordMapper.updateById(record);
    }

    public Long countByStatus(Integer status) {
        return deliveryRecordMapper.selectCount(new QueryWrapper<DeliveryRecord>().eq("status", status));
    }
}
