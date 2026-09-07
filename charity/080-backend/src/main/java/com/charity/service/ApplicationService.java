package com.charity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.common.BusinessException;
import com.charity.entity.Application;
import com.charity.entity.User;
import com.charity.mapper.ApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private UserService userService;

    public Page<Application> getList(int pageNum, int pageSize, Integer applyStatus, Long currentUserId) {
        userService.requireAnyRole(currentUserId, "volunteer");
        Page<Application> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Application> wrapper = new QueryWrapper<>();
        if (applyStatus != null) {
            wrapper.eq("apply_status", applyStatus);
        }
        wrapper.orderByDesc("create_time");
        return applicationMapper.selectPage(page, wrapper);
    }

    public void submit(Application application, Long currentUserId) {
        userService.requireAnyRole(currentUserId, "volunteer");
        if (application.getChildId() == null) {
            throw new BusinessException(400, "儿童ID不能为空");
        }
        if (application.getRequiredAmount() != null && application.getRequiredAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(400, "申请金额必须大于0");
        }
        application.setApplyStatus(0);
        applicationMapper.insert(application);
    }

    public void review(Long id, Integer applyStatus, String reviewComment, Long reviewerId) {
        User reviewer = userService.requireAdmin(reviewerId);
        Application application = applicationMapper.selectById(id);
        if (application == null) {
            throw new BusinessException(404, "资助申请不存在");
        }
        if (applyStatus == null || (applyStatus != 1 && applyStatus != 2)) {
            throw new BusinessException(400, "审核状态不合法");
        }
        application.setApplyStatus(applyStatus);
        application.setReviewComment(reviewComment);
        application.setReviewerId(reviewer.getId());
        application.setReviewTime(LocalDateTime.now());
        applicationMapper.updateById(application);
    }
}
