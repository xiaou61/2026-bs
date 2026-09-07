package com.repair.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.entity.Donation;
import com.repair.mapper.DonationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonationService {

    @Autowired
    private DonationMapper donationMapper;

    public Page<Donation> getList(int pageNum, int pageSize, Long donorId, Long childId) {
        Page<Donation> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Donation> wrapper = new QueryWrapper<>();
        if (donorId != null) {
            wrapper.eq("donor_id", donorId);
        }
        if (childId != null) {
            wrapper.eq("child_id", childId);
        }
        wrapper.orderByDesc("create_time");
        return donationMapper.selectPage(page, wrapper);
    }

    public void add(Donation donation) {
        donationMapper.insert(donation);
    }

    public void confirm(Long id) {
        Donation donation = donationMapper.selectById(id);
        donation.setStatus(1);
        donationMapper.updateById(donation);
    }
}

