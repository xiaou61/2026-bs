package com.charity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.common.BusinessException;
import com.charity.entity.User;
import com.charity.entity.SponsorRelation;
import com.charity.mapper.SponsorRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SponsorRelationService {

    @Autowired
    private SponsorRelationMapper sponsorRelationMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private DonorService donorService;

    public Page<SponsorRelation> getList(int pageNum, int pageSize, Long childId, Long donorId, Long currentUserId) {
        Page<SponsorRelation> page = new Page<>(pageNum, pageSize);
        QueryWrapper<SponsorRelation> wrapper = new QueryWrapper<>();
        User currentUser = userService.requireActiveUser(currentUserId);
        if (userService.isDonor(currentUser)) {
            donorId = donorService.getOwnDonor(currentUserId).getId();
        } else if (!userService.isAdmin(currentUser) && !userService.isVolunteer(currentUser)) {
            throw new BusinessException(403, "无权限查看资助关系");
        }
        if (childId != null) {
            wrapper.eq("child_id", childId);
        }
        if (donorId != null) {
            wrapper.eq("donor_id", donorId);
        }
        wrapper.orderByDesc("create_time");
        return sponsorRelationMapper.selectPage(page, wrapper);
    }

    public void add(SponsorRelation sponsorRelation, Long currentUserId) {
        userService.requireAdmin(currentUserId);
        validate(sponsorRelation);
        sponsorRelationMapper.insert(sponsorRelation);
    }

    public void update(SponsorRelation sponsorRelation, Long currentUserId) {
        userService.requireAdmin(currentUserId);
        if (sponsorRelation.getId() == null || sponsorRelationMapper.selectById(sponsorRelation.getId()) == null) {
            throw new BusinessException(404, "资助关系不存在");
        }
        validate(sponsorRelation);
        sponsorRelationMapper.updateById(sponsorRelation);
    }

    private void validate(SponsorRelation sponsorRelation) {
        if (sponsorRelation.getChildId() == null || sponsorRelation.getDonorId() == null) {
            throw new BusinessException(400, "儿童和捐赠人不能为空");
        }
        if (sponsorRelation.getSponsorAmount() != null && sponsorRelation.getSponsorAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(400, "资助金额必须大于0");
        }
        if (sponsorRelation.getStatus() == null) {
            sponsorRelation.setStatus(0);
        }
    }
}
