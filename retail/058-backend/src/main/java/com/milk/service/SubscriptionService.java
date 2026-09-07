package com.milk.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.milk.common.BusinessException;
import com.milk.entity.Subscription;
import com.milk.mapper.SubscriptionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SubscriptionService {

    @Resource
    private SubscriptionMapper subscriptionMapper;

    public List<Subscription> listByUserId(Long userId) {
        return subscriptionMapper.selectList(new QueryWrapper<Subscription>().eq("user_id", userId).orderByDesc("create_time"));
    }

    public Page<Subscription> page(Integer pageNum, Integer pageSize, Long userId, String type) {
        QueryWrapper<Subscription> wrapper = new QueryWrapper<>();
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        if (StrUtil.isNotBlank(type)) {
            wrapper.eq("type", type);
        }
        wrapper.orderByDesc("create_time");
        return subscriptionMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public void save(Subscription subscription) {
        if (subscription.getId() == null) {
            subscription.setStatus(1);
            subscriptionMapper.insert(subscription);
        } else {
            subscriptionMapper.updateById(subscription);
        }
    }

    public void pause(Long id) {
        Subscription sub = subscriptionMapper.selectById(id);
        if (sub == null) {
            throw new BusinessException("订阅不存在");
        }
        sub.setStatus(0);
        subscriptionMapper.updateById(sub);
    }

    public void resume(Long id) {
        Subscription sub = subscriptionMapper.selectById(id);
        if (sub == null) {
            throw new BusinessException("订阅不存在");
        }
        sub.setStatus(1);
        subscriptionMapper.updateById(sub);
    }

    public void deleteById(Long id) {
        Subscription sub = subscriptionMapper.selectById(id);
        if (sub != null) {
            sub.setStatus(2);
            subscriptionMapper.updateById(sub);
        }
    }
}
