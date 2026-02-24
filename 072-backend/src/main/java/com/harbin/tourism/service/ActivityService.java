package com.harbin.tourism.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harbin.tourism.common.BusinessException;
import com.harbin.tourism.entity.Activity;
import com.harbin.tourism.entity.ActivityRegistration;
import com.harbin.tourism.mapper.ActivityMapper;
import com.harbin.tourism.mapper.ActivityRegistrationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private ActivityRegistrationMapper registrationMapper;

    public Page<Activity> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        LambdaQueryWrapper<Activity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like(Activity::getTitle, keyword);
        }
        if (StrUtil.isNotBlank(status)) {
            wrapper.eq(Activity::getStatus, status);
        }
        wrapper.orderByDesc(Activity::getStartTime);
        return activityMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public Activity getById(Long id) {
        return activityMapper.selectById(id);
    }

    public void save(Activity activity) {
        activity.setCurrentParticipants(0);
        activity.setStatus("registering");
        activityMapper.insert(activity);
    }

    public void update(Activity activity) {
        activityMapper.updateById(activity);
    }

    public void delete(Long id) {
        activityMapper.deleteById(id);
    }

    @Transactional
    public void register(Long activityId, Long userId) {
        Activity activity = activityMapper.selectById(activityId);
        if (activity == null) {
            throw new BusinessException("活动不存在");
        }
        if (!"registering".equals(activity.getStatus())) {
            throw new BusinessException("活动不在报名期");
        }
        if (LocalDateTime.now().isAfter(activity.getRegisterDeadline())) {
            throw new BusinessException("报名已截止");
        }
        if (activity.getCurrentParticipants() >= activity.getMaxParticipants()) {
            throw new BusinessException("报名人数已满");
        }
        ActivityRegistration existing = registrationMapper.selectOne(
                new LambdaQueryWrapper<ActivityRegistration>()
                        .eq(ActivityRegistration::getActivityId, activityId)
                        .eq(ActivityRegistration::getUserId, userId)
        );
        if (existing != null) {
            throw new BusinessException("您已报名过此活动");
        }
        ActivityRegistration registration = new ActivityRegistration();
        registration.setActivityId(activityId);
        registration.setUserId(userId);
        registration.setStatus(1);
        registrationMapper.insert(registration);
        activity.setCurrentParticipants(activity.getCurrentParticipants() + 1);
        activityMapper.updateById(activity);
    }

    public void cancelRegistration(Long activityId, Long userId) {
        ActivityRegistration registration = registrationMapper.selectOne(
                new LambdaQueryWrapper<ActivityRegistration>()
                        .eq(ActivityRegistration::getActivityId, activityId)
                        .eq(ActivityRegistration::getUserId, userId)
        );
        if (registration != null) {
            registrationMapper.deleteById(registration.getId());
            Activity activity = activityMapper.selectById(activityId);
            activity.setCurrentParticipants(activity.getCurrentParticipants() - 1);
            activityMapper.updateById(activity);
        }
    }

    public List<ActivityRegistration> userRegistrations(Long userId) {
        return registrationMapper.selectList(new LambdaQueryWrapper<ActivityRegistration>()
                .eq(ActivityRegistration::getUserId, userId)
                .orderByDesc(ActivityRegistration::getRegisterTime));
    }

    public boolean isRegistered(Long activityId, Long userId) {
        return registrationMapper.selectCount(new LambdaQueryWrapper<ActivityRegistration>()
                .eq(ActivityRegistration::getActivityId, activityId)
                .eq(ActivityRegistration::getUserId, userId)) > 0;
    }

    public long count() {
        return activityMapper.selectCount(null);
    }
}
