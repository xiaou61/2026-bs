package com.xiaou.ordering.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.ordering.entity.Merchant;
import com.xiaou.ordering.mapper.MerchantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantService {

    @Autowired
    private MerchantMapper merchantMapper;

    public Page<Merchant> getMerchantList(int pageNum, int pageSize, String category) {
        Page<Merchant> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Merchant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Merchant::getStatus, 1);
        wrapper.eq(Merchant::getAuditStatus, 1);
        if (category != null && !category.isEmpty()) {
            wrapper.eq(Merchant::getCategory, category);
        }
        wrapper.orderByDesc(Merchant::getRating);
        return merchantMapper.selectPage(page, wrapper);
    }

    public Merchant getMerchantById(Long id) {
        return merchantMapper.selectById(id);
    }

    public List<Merchant> getRecommendMerchants() {
        LambdaQueryWrapper<Merchant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Merchant::getStatus, 1);
        wrapper.eq(Merchant::getAuditStatus, 1);
        wrapper.orderByDesc(Merchant::getRating);
        wrapper.last("LIMIT 6");
        return merchantMapper.selectList(wrapper);
    }
}

