package com.fraud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fraud.common.BusinessException;
import com.fraud.entity.Blacklist;
import com.fraud.mapper.BlacklistMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class BlacklistService {

    @Resource
    private BlacklistMapper blacklistMapper;

    public List<Blacklist> list() {
        return blacklistMapper.selectList(new QueryWrapper<Blacklist>()
                .eq("status", 1)
                .orderByDesc("id"));
    }

    public Page<Blacklist> page(Integer pageNum, Integer pageSize, String type, String value, Integer status) {
        QueryWrapper<Blacklist> wrapper = new QueryWrapper<>();
        if (type != null && !type.trim().isEmpty()) {
            wrapper.eq("type", type.trim());
        }
        if (value != null && !value.trim().isEmpty()) {
            wrapper.like("value", value.trim());
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("id");
        return blacklistMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public void save(Blacklist blacklist, Long creatorId) {
        if (blacklist == null || blacklist.getType() == null || blacklist.getType().trim().isEmpty()) {
            throw new BusinessException("黑名单类型不能为空");
        }
        if (blacklist.getValue() == null || blacklist.getValue().trim().isEmpty()) {
            throw new BusinessException("黑名单值不能为空");
        }
        blacklist.setType(blacklist.getType().trim().toUpperCase());
        if (!Arrays.asList("ACCOUNT", "DEVICE", "IP").contains(blacklist.getType())) {
            throw new BusinessException("黑名单类型不合法");
        }
        blacklist.setValue(blacklist.getValue().trim());
        if (blacklist.getValue().length() > 100) {
            throw new BusinessException("黑名单值不能超过100字符");
        }
        if (blacklist.getReason() != null) {
            blacklist.setReason(blacklist.getReason().trim());
            if (blacklist.getReason().length() > 255) {
                throw new BusinessException("原因不能超过255字符");
            }
        }
        if (blacklist.getLevel() == null || blacklist.getLevel() < 1 || blacklist.getLevel() > 3) {
            blacklist.setLevel(2);
        }
        if (blacklist.getStatus() == null) {
            blacklist.setStatus(1);
        }
        if (blacklist.getStatus() != 0 && blacklist.getStatus() != 1) {
            throw new BusinessException("状态不合法");
        }
        if (blacklist.getId() == null) {
            Long count = blacklistMapper.selectCount(new QueryWrapper<Blacklist>()
                    .eq("type", blacklist.getType())
                    .eq("value", blacklist.getValue()));
            if (count != null && count > 0) {
                throw new BusinessException("该黑名单记录已存在");
            }
            blacklist.setCreatorId(creatorId);
            blacklistMapper.insert(blacklist);
        } else {
            Blacklist db = blacklistMapper.selectById(blacklist.getId());
            if (db == null) {
                throw new BusinessException("黑名单记录不存在");
            }
            Long count = blacklistMapper.selectCount(new QueryWrapper<Blacklist>()
                    .eq("type", blacklist.getType())
                    .eq("value", blacklist.getValue())
                    .ne("id", blacklist.getId()));
            if (count != null && count > 0) {
                throw new BusinessException("该黑名单记录已存在");
            }
            blacklist.setCreatorId(db.getCreatorId());
            blacklistMapper.updateById(blacklist);
        }
    }

    public void deleteById(Long id) {
        blacklistMapper.deleteById(id);
    }

    public boolean hit(String type, String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        Long count = blacklistMapper.selectCount(new QueryWrapper<Blacklist>()
                .eq("type", type)
                .eq("value", value.trim())
                .eq("status", 1));
        return count != null && count > 0;
    }
}
