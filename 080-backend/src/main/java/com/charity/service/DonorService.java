package com.charity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.entity.Donor;
import com.charity.entity.User;
import com.charity.common.BusinessException;
import com.charity.mapper.DonorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DonorService {

    @Autowired
    private DonorMapper donorMapper;

    @Autowired
    private UserService userService;

    public Page<Donor> getList(int pageNum, int pageSize, String donorType, Long currentUserId) {
        Page<Donor> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Donor> wrapper = new QueryWrapper<>();
        User currentUser = userService.requireActiveUser(currentUserId);
        if (userService.isDonor(currentUser)) {
            wrapper.eq("user_id", currentUserId);
        } else if (!userService.isAdmin(currentUser)) {
            throw new BusinessException(403, "无权限查看捐赠人信息");
        }
        if (donorType != null && !donorType.isEmpty()) {
            wrapper.eq("donor_type", donorType);
        }
        return donorMapper.selectPage(page, wrapper);
    }

    public void add(Donor donor, Long currentUserId) {
        User currentUser = userService.requireActiveUser(currentUserId);
        if (userService.isDonor(currentUser)) {
            donor.setUserId(currentUserId);
        } else if (!userService.isAdmin(currentUser)) {
            throw new BusinessException(403, "无权限新增捐赠人");
        }
        donorMapper.insert(donor);
    }

    public void update(Donor donor, Long currentUserId) {
        Donor existing = requireDonor(donor.getId());
        User currentUser = userService.requireActiveUser(currentUserId);
        if (userService.isDonor(currentUser) && !Objects.equals(existing.getUserId(), currentUserId)) {
            throw new BusinessException(403, "无权限修改该捐赠人");
        }
        if (!userService.isAdmin(currentUser) && !userService.isDonor(currentUser)) {
            throw new BusinessException(403, "无权限修改捐赠人");
        }
        if (userService.isDonor(currentUser)) {
            donor.setUserId(currentUserId);
            donor.setTotalAmount(existing.getTotalAmount());
            donor.setDonationCount(existing.getDonationCount());
        }
        donorMapper.updateById(donor);
    }

    public void delete(Long id, Long currentUserId) {
        userService.requireAdmin(currentUserId);
        donorMapper.deleteById(id);
    }

    public Donor getOwnDonor(Long userId) {
        QueryWrapper<Donor> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        Donor donor = donorMapper.selectOne(wrapper);
        if (donor == null) {
            throw new BusinessException(400, "当前用户未维护捐赠人信息");
        }
        return donor;
    }

    public Donor requireDonor(Long id) {
        if (id == null) {
            throw new BusinessException(400, "捐赠人ID不能为空");
        }
        Donor donor = donorMapper.selectById(id);
        if (donor == null) {
            throw new BusinessException(404, "捐赠人不存在");
        }
        return donor;
    }
}
