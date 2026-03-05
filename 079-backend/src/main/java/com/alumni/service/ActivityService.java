package com.alumni.service;

import com.alumni.common.BusinessException;
import com.alumni.entity.*;
import com.alumni.mapper.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private ActivitySignMapper activitySignMapper;

    @Autowired
    private ActivityPhotoMapper activityPhotoMapper;

    @Autowired
    private UserMapper userMapper;

    public Page<Activity> list(Integer pageNum, Integer pageSize, String title, Integer status) {
        Page<Activity> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Activity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(title)) {
            wrapper.like(Activity::getTitle, title);
        }
        if (status != null) {
            wrapper.eq(Activity::getStatus, status);
        }
        wrapper.orderByDesc(Activity::getCreateTime);
        Page<Activity> result = activityMapper.selectPage(page, wrapper);
        List<User> users = userMapper.selectList(null);
        Map<Long, String> userMap = users.stream().collect(Collectors.toMap(User::getId, User::getName));
        result.getRecords().forEach(a -> a.setOrganizerName(userMap.get(a.getOrganizerId())));
        return result;
    }

    public Activity getById(Long id, Long userId) {
        Activity activity = activityMapper.selectById(id);
        if (activity != null) {
            User organizer = userMapper.selectById(activity.getOrganizerId());
            if (organizer != null) activity.setOrganizerName(organizer.getName());
            if (userId != null) {
                ActivitySign sign = activitySignMapper.selectOne(
                        new LambdaQueryWrapper<ActivitySign>()
                                .eq(ActivitySign::getActivityId, id)
                                .eq(ActivitySign::getUserId, userId)
                                .ne(ActivitySign::getStatus, 2));
                activity.setSigned(sign != null);
            }
        }
        return activity;
    }

    public void add(Activity activity) {
        activity.setCurrentCount(0);
        activity.setStatus(0);
        activityMapper.insert(activity);
    }

    public void update(Activity activity) {
        activityMapper.updateById(activity);
    }

    public void delete(Long id) {
        activityMapper.deleteById(id);
        activitySignMapper.delete(new LambdaQueryWrapper<ActivitySign>().eq(ActivitySign::getActivityId, id));
        activityPhotoMapper.delete(new LambdaQueryWrapper<ActivityPhoto>().eq(ActivityPhoto::getActivityId, id));
    }

    public void sign(Long activityId, Long userId) {
        Activity activity = activityMapper.selectById(activityId);
        if (activity == null) {
            throw new BusinessException("活动不存在");
        }
        if (activity.getSignDeadline() != null && LocalDateTime.now().isAfter(activity.getSignDeadline())) {
            throw new BusinessException("报名已截止");
        }
        if (activity.getCurrentCount() >= activity.getMaxCount()) {
            throw new BusinessException("报名人数已满");
        }
        ActivitySign exist = activitySignMapper.selectOne(
                new LambdaQueryWrapper<ActivitySign>()
                        .eq(ActivitySign::getActivityId, activityId)
                        .eq(ActivitySign::getUserId, userId)
                        .ne(ActivitySign::getStatus, 2));
        if (exist != null) {
            throw new BusinessException("已报名");
        }
        ActivitySign sign = new ActivitySign();
        sign.setActivityId(activityId);
        sign.setUserId(userId);
        sign.setStatus(0);
        activitySignMapper.insert(sign);
        activity.setCurrentCount(activity.getCurrentCount() + 1);
        activityMapper.updateById(activity);
    }

    public void cancelSign(Long activityId, Long userId) {
        ActivitySign sign = activitySignMapper.selectOne(
                new LambdaQueryWrapper<ActivitySign>()
                        .eq(ActivitySign::getActivityId, activityId)
                        .eq(ActivitySign::getUserId, userId)
                        .ne(ActivitySign::getStatus, 2));
        if (sign != null) {
            sign.setStatus(2);
            activitySignMapper.updateById(sign);
            Activity activity = activityMapper.selectById(activityId);
            if (activity != null && activity.getCurrentCount() > 0) {
                activity.setCurrentCount(activity.getCurrentCount() - 1);
                activityMapper.updateById(activity);
            }
        }
    }

    public void check(Long activityId, Long userId) {
        ActivitySign sign = activitySignMapper.selectOne(
                new LambdaQueryWrapper<ActivitySign>()
                        .eq(ActivitySign::getActivityId, activityId)
                        .eq(ActivitySign::getUserId, userId)
                        .eq(ActivitySign::getStatus, 0));
        if (sign == null) {
            throw new BusinessException("未报名或已签到");
        }
        sign.setStatus(1);
        sign.setCheckTime(LocalDateTime.now());
        activitySignMapper.updateById(sign);
    }

    public List<ActivitySign> getSignList(Long activityId) {
        List<ActivitySign> signs = activitySignMapper.selectList(
                new LambdaQueryWrapper<ActivitySign>()
                        .eq(ActivitySign::getActivityId, activityId)
                        .ne(ActivitySign::getStatus, 2)
                        .orderByAsc(ActivitySign::getSignTime));
        List<User> users = userMapper.selectList(null);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, u -> u));
        signs.forEach(s -> {
            User user = userMap.get(s.getUserId());
            if (user != null) {
                s.setUserName(user.getName());
                s.setUserPhone(user.getPhone());
            }
        });
        return signs;
    }

    public List<ActivityPhoto> getPhotos(Long activityId) {
        List<ActivityPhoto> photos = activityPhotoMapper.selectList(
                new LambdaQueryWrapper<ActivityPhoto>()
                        .eq(ActivityPhoto::getActivityId, activityId)
                        .orderByDesc(ActivityPhoto::getCreateTime));
        List<User> users = userMapper.selectList(null);
        Map<Long, String> userMap = users.stream().collect(Collectors.toMap(User::getId, User::getName));
        photos.forEach(p -> p.setUploadUserName(userMap.get(p.getUploadUserId())));
        return photos;
    }

    public void addPhoto(ActivityPhoto photo) {
        activityPhotoMapper.insert(photo);
    }

    public void deletePhoto(Long id) {
        activityPhotoMapper.deleteById(id);
    }
}
