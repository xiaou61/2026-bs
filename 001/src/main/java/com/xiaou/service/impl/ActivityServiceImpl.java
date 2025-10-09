package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.common.BusinessException;
import com.xiaou.entity.Activity;
import com.xiaou.entity.ActivitySignup;
import com.xiaou.entity.User;
import com.xiaou.mapper.ActivityMapper;
import com.xiaou.service.ActivityService;
import com.xiaou.service.ActivitySignupService;
import com.xiaou.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 活动Service实现类
 * @author xiaou
 */
@Slf4j
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ActivitySignupService activitySignupService;
    
    @Override
    public void publishActivity(Activity activity) {
        // 初始化当前报名人数
        if (activity.getCurrentParticipants() == null) {
            activity.setCurrentParticipants(0);
        }
        
        // 设置活动状态
        if (activity.getStatus() == null) {
            activity.setStatus(1); // 默认报名中
        }
        
        save(activity);
    }
    
    @Override
    public Page<Activity> getActivityPage(int pageNum, int pageSize, Integer status) {
        Page<Activity> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Activity> wrapper = new LambdaQueryWrapper<>();
        
        if (status != null) {
            wrapper.eq(Activity::getStatus, status);
        }
        
        wrapper.orderByDesc(Activity::getCreateTime);
        Page<Activity> resultPage = page(page, wrapper);
        
        // 填充发布者姓名
        for (Activity record : resultPage.getRecords()) {
            User publisher = userService.getById(record.getPublisherId());
            if (publisher != null) {
                record.setPublisherName(publisher.getRealName() != null ? publisher.getRealName() : publisher.getUsername());
            }
        }
        
        return resultPage;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void signupActivity(Long activityId, Long studentId, String remark) {
        // 检查活动是否存在
        Activity activity = getById(activityId);
        if (activity == null) {
            throw new BusinessException("活动不存在");
        }
        
        // 检查活动状态
        if (activity.getStatus() != 1) {
            throw new BusinessException("活动未开放报名");
        }
        
        // 检查是否已报名
        if (hasSignedUp(activityId, studentId)) {
            throw new BusinessException("您已报名该活动");
        }
        
        // 检查人数限制
        if (activity.getCurrentParticipants() >= activity.getMaxParticipants()) {
            throw new BusinessException("报名人数已满");
        }
        
        // 创建报名记录
        ActivitySignup signup = new ActivitySignup();
        signup.setActivityId(activityId);
        signup.setStudentId(studentId);
        signup.setRemark(remark);
        signup.setStatus(1); // 已通过
        activitySignupService.save(signup);
        
        // 更新活动报名人数
        activity.setCurrentParticipants(activity.getCurrentParticipants() + 1);
        updateById(activity);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelSignup(Long activityId, Long studentId) {
        // 查询报名记录
        LambdaQueryWrapper<ActivitySignup> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivitySignup::getActivityId, activityId)
                .eq(ActivitySignup::getStudentId, studentId);
        ActivitySignup signup = activitySignupService.getOne(wrapper);
        
        if (signup == null) {
            throw new BusinessException("未找到报名记录");
        }
        
        // 删除报名记录
        activitySignupService.removeById(signup.getId());
        
        // 更新活动报名人数
        Activity activity = getById(activityId);
        if (activity != null) {
            activity.setCurrentParticipants(Math.max(0, activity.getCurrentParticipants() - 1));
            updateById(activity);
        }
    }
    
    @Override
    public boolean hasSignedUp(Long activityId, Long studentId) {
        LambdaQueryWrapper<ActivitySignup> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivitySignup::getActivityId, activityId)
                .eq(ActivitySignup::getStudentId, studentId);
        return activitySignupService.count(wrapper) > 0;
    }
    
    @Override
    public Object getActivitySignupList(Long activityId, int pageNum, int pageSize) {
        Page<ActivitySignup> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ActivitySignup> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivitySignup::getActivityId, activityId)
                .orderByDesc(ActivitySignup::getCreateTime);
        
        Page<ActivitySignup> resultPage = activitySignupService.page(page, wrapper);
        
        // 填充学生姓名
        for (ActivitySignup record : resultPage.getRecords()) {
            User student = userService.getById(record.getStudentId());
            if (student != null) {
                record.setStudentName(student.getRealName() != null ? student.getRealName() : student.getUsername());
            }
        }
        
        return resultPage;
    }
}

