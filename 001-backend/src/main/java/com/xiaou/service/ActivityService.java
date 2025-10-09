package com.xiaou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.Activity;

/**
 * 活动Service接口
 * @author xiaou
 */
public interface ActivityService extends IService<Activity> {
    
    /**
     * 发布活动
     */
    void publishActivity(Activity activity);
    
    /**
     * 分页查询活动列表
     */
    Page<Activity> getActivityPage(int pageNum, int pageSize, String status);
    
    /**
     * 学生报名活动
     */
    void signupActivity(Long activityId, Long studentId, String remark);
    
    /**
     * 取消报名
     */
    void cancelSignup(Long activityId, Long studentId);
    
    /**
     * 查询学生是否已报名
     */
    boolean hasSignedUp(Long activityId, Long studentId);
    
    /**
     * 获取活动报名列表
     */
    Object getActivitySignupList(Long activityId, int pageNum, int pageSize);
    
    /**
     * 获取活动详情并填充组织者姓名
     */
    Activity getActivityWithOrganizerName(Activity activity);
}

