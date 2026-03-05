package com.groupbuy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.groupbuy.entity.GroupActivity;
import com.groupbuy.entity.Merchant;
import com.groupbuy.entity.Product;
import com.groupbuy.mapper.GroupActivityMapper;
import com.groupbuy.mapper.MerchantMapper;
import com.groupbuy.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GroupActivityService {

    @Autowired
    private GroupActivityMapper groupActivityMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    public Page<GroupActivity> page(Integer pageNum, Integer pageSize, Long productId, Integer status, Long merchantId) {
        Page<GroupActivity> page = new Page<>(pageNum, pageSize);
        QueryWrapper<GroupActivity> wrapper = new QueryWrapper<>();
        if (productId != null) {
            wrapper.eq("product_id", productId);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (merchantId != null) {
            wrapper.eq("merchant_id", merchantId);
        }
        wrapper.orderByDesc("create_time");
        Page<GroupActivity> result = groupActivityMapper.selectPage(page, wrapper);
        result.getRecords().forEach(this::fillActivityInfo);
        return result;
    }

    public GroupActivity detail(Long id) {
        GroupActivity activity = groupActivityMapper.selectById(id);
        if (activity != null) {
            fillActivityInfo(activity);
        }
        return activity;
    }

    public void add(Long merchantId, GroupActivity activity) {
        activity.setMerchantId(merchantId);
        activity.setStatus(0);
        LocalDateTime now = LocalDateTime.now();
        if (activity.getStartTime().isBefore(now) && activity.getEndTime().isAfter(now)) {
            activity.setStatus(1);
        }
        groupActivityMapper.insert(activity);
    }

    public void update(GroupActivity activity) {
        groupActivityMapper.updateById(activity);
    }

    public void end(Long id) {
        GroupActivity activity = new GroupActivity();
        activity.setId(id);
        activity.setStatus(2);
        groupActivityMapper.updateById(activity);
    }

    public Page<GroupActivity> home(Integer pageNum, Integer pageSize, Long categoryId) {
        Page<GroupActivity> page = new Page<>(pageNum, pageSize);
        QueryWrapper<GroupActivity> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        wrapper.gt("end_time", LocalDateTime.now());
        if (categoryId != null) {
            wrapper.inSql("product_id", "SELECT id FROM product WHERE category_id = " + categoryId);
        }
        wrapper.orderByDesc("create_time");
        Page<GroupActivity> result = groupActivityMapper.selectPage(page, wrapper);
        result.getRecords().forEach(this::fillActivityInfo);
        return result;
    }

    private void fillActivityInfo(GroupActivity activity) {
        Product product = productMapper.selectById(activity.getProductId());
        activity.setProduct(product);
        Merchant merchant = merchantMapper.selectById(activity.getMerchantId());
        if (merchant != null) {
            activity.setMerchantName(merchant.getName());
        }
    }

    @Scheduled(fixedRate = 60000)
    public void updateActivityStatus() {
        LocalDateTime now = LocalDateTime.now();
        QueryWrapper<GroupActivity> startWrapper = new QueryWrapper<>();
        startWrapper.eq("status", 0).le("start_time", now).gt("end_time", now);
        groupActivityMapper.selectList(startWrapper).forEach(a -> {
            a.setStatus(1);
            groupActivityMapper.updateById(a);
        });
        QueryWrapper<GroupActivity> endWrapper = new QueryWrapper<>();
        endWrapper.eq("status", 1).le("end_time", now);
        groupActivityMapper.selectList(endWrapper).forEach(a -> {
            a.setStatus(2);
            groupActivityMapper.updateById(a);
        });
    }
}
