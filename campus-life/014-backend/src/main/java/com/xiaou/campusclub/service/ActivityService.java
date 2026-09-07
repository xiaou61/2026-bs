package com.xiaou.campusclub.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.campusclub.dto.ActivityRequest;
import com.xiaou.campusclub.vo.ActivityVO;

import java.util.List;

public interface ActivityService {
    IPage<ActivityVO> getActivityList(Integer page, Integer size, Integer status, Long userId);
    ActivityVO getActivityDetail(Long activityId, Long userId);
    Long createActivity(Long userId, ActivityRequest request);
    void updateActivity(Long activityId, Long userId, ActivityRequest request);
    void cancelActivity(Long activityId, Long userId);
    void registerActivity(Long activityId, Long userId);
    void cancelRegistration(Long activityId, Long userId);
    void signActivity(Long activityId, Long userId, String signCode);
    void rateActivity(Long activityId, Long userId, Integer rating, String comment);
    List<ActivityVO> getMyActivities(Long userId);
}

