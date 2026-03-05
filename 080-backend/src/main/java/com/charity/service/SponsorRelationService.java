package com.charity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.entity.SponsorRelation;
import com.charity.mapper.SponsorRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SponsorRelationService {

    @Autowired
    private SponsorRelationMapper sponsorRelationMapper;

    public Page<SponsorRelation> getList(int pageNum, int pageSize, Long childId, Long donorId) {
        Page<SponsorRelation> page = new Page<>(pageNum, pageSize);
        QueryWrapper<SponsorRelation> wrapper = new QueryWrapper<>();
        if (childId != null) {
            wrapper.eq("child_id", childId);
        }
        if (donorId != null) {
            wrapper.eq("donor_id", donorId);
        }
        wrapper.orderByDesc("create_time");
        return sponsorRelationMapper.selectPage(page, wrapper);
    }

    public void add(SponsorRelation sponsorRelation) {
        sponsorRelationMapper.insert(sponsorRelation);
    }

    public void update(SponsorRelation sponsorRelation) {
        sponsorRelationMapper.updateById(sponsorRelation);
    }
}
