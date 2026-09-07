package com.charity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.common.BusinessException;
import com.charity.entity.Donor;
import com.charity.entity.Donation;
import com.charity.entity.Project;
import com.charity.entity.User;
import com.charity.mapper.DonorMapper;
import com.charity.mapper.DonationMapper;
import com.charity.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Service
public class DonationService {

    @Autowired
    private DonationMapper donationMapper;

    @Autowired
    private DonorService donorService;

    @Autowired
    private DonorMapper donorMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private UserService userService;

    public Page<Donation> getList(int pageNum, int pageSize, Long donorId, Long childId, Long currentUserId) {
        Page<Donation> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Donation> wrapper = new QueryWrapper<>();
        User currentUser = userService.requireActiveUser(currentUserId);
        if (userService.isDonor(currentUser)) {
            donorId = donorService.getOwnDonor(currentUserId).getId();
        } else if (!userService.isAdmin(currentUser) && !userService.isVolunteer(currentUser)) {
            throw new BusinessException(403, "无权限查看捐赠记录");
        }
        if (donorId != null) {
            wrapper.eq("donor_id", donorId);
        }
        if (childId != null) {
            wrapper.eq("child_id", childId);
        }
        wrapper.orderByDesc("create_time");
        return donationMapper.selectPage(page, wrapper);
    }

    public void add(Donation donation, Long currentUserId) {
        User currentUser = userService.requireAnyRole(currentUserId, "donor");
        if (donation.getAmount() == null && !"material".equals(donation.getDonationType())) {
            throw new BusinessException(400, "捐赠金额不能为空");
        }
        if (donation.getAmount() != null && donation.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(400, "捐赠金额必须大于0");
        }
        if (userService.isDonor(currentUser)) {
            donation.setDonorId(donorService.getOwnDonor(currentUserId).getId());
        } else {
            donorService.requireDonor(donation.getDonorId());
        }
        if (donation.getDonationDate() == null) {
            donation.setDonationDate(LocalDate.now());
        }
        donation.setStatus(0);
        donationMapper.insert(donation);
    }

    public void confirm(Long id, Long currentUserId) {
        userService.requireAdmin(currentUserId);
        Donation donation = donationMapper.selectById(id);
        if (donation == null) {
            throw new BusinessException(404, "捐赠记录不存在");
        }
        if (Objects.equals(donation.getStatus(), 1)) {
            return;
        }
        donation.setStatus(1);
        donationMapper.updateById(donation);
        Donor donor = donorMapper.selectById(donation.getDonorId());
        if (donor != null && donation.getAmount() != null) {
            donor.setTotalAmount((donor.getTotalAmount() == null ? BigDecimal.ZERO : donor.getTotalAmount()).add(donation.getAmount()));
            donor.setDonationCount((donor.getDonationCount() == null ? 0 : donor.getDonationCount()) + 1);
            donorMapper.updateById(donor);
        }
        if (donation.getProjectId() != null && donation.getAmount() != null) {
            Project project = projectMapper.selectById(donation.getProjectId());
            if (project != null) {
                project.setCurrentAmount((project.getCurrentAmount() == null ? BigDecimal.ZERO : project.getCurrentAmount()).add(donation.getAmount()));
                projectMapper.updateById(project);
            }
        }
    }
}
