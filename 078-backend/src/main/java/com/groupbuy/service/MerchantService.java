package com.groupbuy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.groupbuy.common.BusinessException;
import com.groupbuy.entity.Merchant;
import com.groupbuy.entity.User;
import com.groupbuy.mapper.MerchantMapper;
import com.groupbuy.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MerchantService {

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void apply(Long userId, Merchant merchant) {
        QueryWrapper<Merchant> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        if (merchantMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("您已提交过入驻申请");
        }
        merchant.setUserId(userId);
        merchant.setStatus(0);
        merchantMapper.insert(merchant);
    }

    public Page<Merchant> page(Integer pageNum, Integer pageSize, String name, Integer status) {
        Page<Merchant> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Merchant> wrapper = new QueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            wrapper.like("name", name);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        Page<Merchant> result = merchantMapper.selectPage(page, wrapper);
        result.getRecords().forEach(m -> {
            User user = userMapper.selectById(m.getUserId());
            if (user != null) {
                m.setUsername(user.getUsername());
            }
        });
        return result;
    }

    @Transactional
    public void audit(Long id, Integer status, String auditRemark) {
        Merchant merchant = merchantMapper.selectById(id);
        if (merchant == null) {
            throw new BusinessException("商家不存在");
        }
        merchant.setStatus(status);
        merchant.setAuditRemark(auditRemark);
        merchantMapper.updateById(merchant);
        if (status == 1) {
            User user = new User();
            user.setId(merchant.getUserId());
            user.setRole(1);
            userMapper.updateById(user);
        }
    }

    public Merchant getByUserId(Long userId) {
        QueryWrapper<Merchant> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return merchantMapper.selectOne(wrapper);
    }

    public void update(Long userId, Merchant merchant) {
        QueryWrapper<Merchant> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        Merchant old = merchantMapper.selectOne(wrapper);
        if (old == null) {
            throw new BusinessException("商家信息不存在");
        }
        merchant.setId(old.getId());
        merchant.setUserId(null);
        merchant.setStatus(null);
        merchantMapper.updateById(merchant);
    }

    public Merchant getById(Long id) {
        return merchantMapper.selectById(id);
    }
}
