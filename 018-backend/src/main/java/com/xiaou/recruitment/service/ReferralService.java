package com.xiaou.recruitment.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.recruitment.entity.Referral;
import com.xiaou.recruitment.mapper.ReferralMapper;
import org.springframework.stereotype.Service;

@Service
public class ReferralService extends ServiceImpl<ReferralMapper, Referral> {

    public boolean publishReferral(Referral referral) {
        referral.setStatus(1);
        referral.setViews(0);
        return save(referral);
    }

    public Referral getReferralById(Long id) {
        Referral referral = getById(id);
        if (referral != null) {
            referral.setViews(referral.getViews() + 1);
            updateById(referral);
        }
        return referral;
    }

    public IPage<Referral> getReferralList(Integer page, Integer size, String keyword) {
        Page<Referral> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Referral> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Referral::getStatus, 1);
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like(Referral::getCompanyName, keyword)
                    .or().like(Referral::getJobTitle, keyword)
                    .or().like(Referral::getDescription, keyword));
        }
        wrapper.orderByDesc(Referral::getCreatedAt);
        return page(pageParam, wrapper);
    }

    public boolean closeReferral(Long id) {
        Referral referral = getById(id);
        if (referral != null) {
            referral.setStatus(0);
            return updateById(referral);
        }
        return false;
    }
}
