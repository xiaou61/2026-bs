package com.charity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.common.BusinessException;
import com.charity.entity.Donor;
import com.charity.entity.Feedback;
import com.charity.entity.User;
import com.charity.mapper.FeedbackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private DonorService donorService;

    public Page<Feedback> getList(int pageNum, int pageSize, String feedbackType, Long childId, Long currentUserId) {
        Page<Feedback> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Feedback> wrapper = new QueryWrapper<>();
        User currentUser = userService.requireActiveUser(currentUserId);
        if (userService.isDonor(currentUser)) {
            wrapper.eq("donor_id", donorService.getOwnDonor(currentUserId).getId());
        } else if (!userService.isAdmin(currentUser) && !userService.isVolunteer(currentUser)) {
            throw new BusinessException(403, "无权限查看反馈");
        }
        if (feedbackType != null && !feedbackType.isEmpty()) {
            wrapper.eq("feedback_type", feedbackType);
        }
        if (childId != null) {
            wrapper.eq("child_id", childId);
        }
        wrapper.orderByDesc("create_time");
        return feedbackMapper.selectPage(page, wrapper);
    }

    public void add(Feedback feedback, Long currentUserId) {
        User currentUser = userService.requireActiveUser(currentUserId);
        if (userService.isDonor(currentUser)) {
            Donor donor = donorService.getOwnDonor(currentUserId);
            feedback.setDonorId(donor.getId());
        } else if (!userService.isAdmin(currentUser) && !userService.isVolunteer(currentUser)) {
            throw new BusinessException(403, "无权限新增反馈");
        }
        feedbackMapper.insert(feedback);
    }
}
