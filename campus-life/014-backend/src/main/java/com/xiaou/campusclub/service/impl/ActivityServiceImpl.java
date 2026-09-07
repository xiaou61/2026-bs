package com.xiaou.campusclub.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.campusclub.dto.ActivityRequest;
import com.xiaou.campusclub.entity.Activity;
import com.xiaou.campusclub.entity.ActivityRegistration;
import com.xiaou.campusclub.entity.Club;
import com.xiaou.campusclub.entity.ClubMember;
import com.xiaou.campusclub.exception.BusinessException;
import com.xiaou.campusclub.mapper.ActivityMapper;
import com.xiaou.campusclub.mapper.ActivityRegistrationMapper;
import com.xiaou.campusclub.mapper.ClubMapper;
import com.xiaou.campusclub.mapper.ClubMemberMapper;
import com.xiaou.campusclub.service.ActivityService;
import com.xiaou.campusclub.service.UserService;
import com.xiaou.campusclub.vo.ActivityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements ActivityService {
    
    @Autowired
    private ActivityMapper activityMapper;
    
    @Autowired
    private ActivityRegistrationMapper registrationMapper;
    
    @Autowired
    private ClubMapper clubMapper;
    
    @Autowired
    private ClubMemberMapper clubMemberMapper;
    
    @Autowired
    private UserService userService;
    
    @Override
    public IPage<ActivityVO> getActivityList(Integer page, Integer size, Integer status, Long userId) {
        Page<Activity> activityPage = new Page<>(page, size);
        LambdaQueryWrapper<Activity> wrapper = new LambdaQueryWrapper<>();
        
        if (status != null) {
            wrapper.eq(Activity::getStatus, status);
        }
        
        wrapper.orderByDesc(Activity::getStartTime);
        
        IPage<Activity> result = activityMapper.selectPage(activityPage, wrapper);
        
        return result.convert(activity -> convertToVO(activity, userId));
    }
    
    @Override
    public ActivityVO getActivityDetail(Long activityId, Long userId) {
        Activity activity = activityMapper.selectById(activityId);
        if (activity == null) {
            throw new BusinessException("活动不存在");
        }
        
        return convertToVO(activity, userId);
    }
    
    @Override
    @Transactional
    public Long createActivity(Long userId, ActivityRequest request) {
        if (!isClubAdmin(request.getClubId(), userId)) {
            throw new BusinessException("无权限操作");
        }
        
        Activity activity = new Activity();
        activity.setClubId(request.getClubId());
        activity.setTitle(request.getTitle());
        activity.setCover(request.getCover());
        activity.setDescription(request.getDescription());
        activity.setLocation(request.getLocation());
        activity.setStartTime(request.getStartTime());
        activity.setEndTime(request.getEndTime());
        activity.setMaxParticipants(request.getMaxParticipants());
        activity.setCurrentParticipants(0);
        activity.setSignCode(RandomUtil.randomString(8).toUpperCase());
        activity.setStatus(0);
        activity.setPoints(request.getPoints() != null ? request.getPoints() : 10);
        activity.setCreateTime(LocalDateTime.now());
        
        activityMapper.insert(activity);
        
        return activity.getId();
    }
    
    @Override
    @Transactional
    public void updateActivity(Long activityId, Long userId, ActivityRequest request) {
        Activity activity = activityMapper.selectById(activityId);
        if (activity == null) {
            throw new BusinessException("活动不存在");
        }
        
        if (!isClubAdmin(activity.getClubId(), userId)) {
            throw new BusinessException("无权限操作");
        }
        
        activity.setTitle(request.getTitle());
        activity.setCover(request.getCover());
        activity.setDescription(request.getDescription());
        activity.setLocation(request.getLocation());
        activity.setStartTime(request.getStartTime());
        activity.setEndTime(request.getEndTime());
        activity.setMaxParticipants(request.getMaxParticipants());
        activity.setUpdateTime(LocalDateTime.now());
        
        activityMapper.updateById(activity);
    }
    
    @Override
    @Transactional
    public void cancelActivity(Long activityId, Long userId) {
        Activity activity = activityMapper.selectById(activityId);
        if (activity == null) {
            throw new BusinessException("活动不存在");
        }
        
        if (!isClubAdmin(activity.getClubId(), userId)) {
            throw new BusinessException("无权限操作");
        }
        
        activity.setStatus(3);
        activity.setUpdateTime(LocalDateTime.now());
        activityMapper.updateById(activity);
    }
    
    @Override
    @Transactional
    public void registerActivity(Long activityId, Long userId) {
        Activity activity = activityMapper.selectById(activityId);
        if (activity == null) {
            throw new BusinessException("活动不存在");
        }
        
        if (activity.getStatus() != 0) {
            throw new BusinessException("活动不在报名期");
        }
        
        if (activity.getMaxParticipants() > 0 && 
            activity.getCurrentParticipants() >= activity.getMaxParticipants()) {
            throw new BusinessException("报名人数已满");
        }
        
        LambdaQueryWrapper<ActivityRegistration> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivityRegistration::getActivityId, activityId)
               .eq(ActivityRegistration::getUserId, userId)
               .ne(ActivityRegistration::getStatus, 2);
        
        if (registrationMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("已报名该活动");
        }
        
        ActivityRegistration registration = new ActivityRegistration();
        registration.setActivityId(activityId);
        registration.setUserId(userId);
        registration.setStatus(0);
        registration.setRegisterTime(LocalDateTime.now());
        registrationMapper.insert(registration);
        
        activity.setCurrentParticipants(activity.getCurrentParticipants() + 1);
        activityMapper.updateById(activity);
    }
    
    @Override
    @Transactional
    public void cancelRegistration(Long activityId, Long userId) {
        LambdaQueryWrapper<ActivityRegistration> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivityRegistration::getActivityId, activityId)
               .eq(ActivityRegistration::getUserId, userId);
        ActivityRegistration registration = registrationMapper.selectOne(wrapper);
        
        if (registration == null) {
            throw new BusinessException("未报名该活动");
        }
        
        if (registration.getStatus() == 2) {
            throw new BusinessException("已取消报名");
        }
        
        registration.setStatus(2);
        registrationMapper.updateById(registration);
        
        Activity activity = activityMapper.selectById(activityId);
        if (activity.getCurrentParticipants() > 0) {
            activity.setCurrentParticipants(activity.getCurrentParticipants() - 1);
            activityMapper.updateById(activity);
        }
    }
    
    @Override
    @Transactional
    public void signActivity(Long activityId, Long userId, String signCode) {
        Activity activity = activityMapper.selectById(activityId);
        if (activity == null) {
            throw new BusinessException("活动不存在");
        }
        
        if (!activity.getSignCode().equals(signCode)) {
            throw new BusinessException("签到码错误");
        }
        
        LambdaQueryWrapper<ActivityRegistration> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivityRegistration::getActivityId, activityId)
               .eq(ActivityRegistration::getUserId, userId);
        ActivityRegistration registration = registrationMapper.selectOne(wrapper);
        
        if (registration == null) {
            throw new BusinessException("未报名该活动");
        }
        
        if (registration.getStatus() == 1) {
            throw new BusinessException("已签到");
        }
        
        registration.setStatus(1);
        registration.setSignTime(LocalDateTime.now());
        registrationMapper.updateById(registration);
        
        userService.addPoints(userId, activity.getPoints(), "ACTIVITY_SIGN", "活动签到：" + activity.getTitle());
    }
    
    @Override
    @Transactional
    public void rateActivity(Long activityId, Long userId, Integer rating, String comment) {
        LambdaQueryWrapper<ActivityRegistration> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivityRegistration::getActivityId, activityId)
               .eq(ActivityRegistration::getUserId, userId);
        ActivityRegistration registration = registrationMapper.selectOne(wrapper);
        
        if (registration == null) {
            throw new BusinessException("未参加该活动");
        }
        
        registration.setRating(rating);
        registration.setComment(comment);
        registrationMapper.updateById(registration);
    }
    
    @Override
    public List<ActivityVO> getMyActivities(Long userId) {
        LambdaQueryWrapper<ActivityRegistration> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivityRegistration::getUserId, userId)
               .ne(ActivityRegistration::getStatus, 2);
        List<ActivityRegistration> registrations = registrationMapper.selectList(wrapper);
        
        return registrations.stream()
                .map(reg -> {
                    Activity activity = activityMapper.selectById(reg.getActivityId());
                    return activity != null ? convertToVO(activity, userId) : null;
                })
                .filter(vo -> vo != null)
                .collect(Collectors.toList());
    }
    
    private boolean isClubAdmin(Long clubId, Long userId) {
        LambdaQueryWrapper<ClubMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClubMember::getClubId, clubId)
               .eq(ClubMember::getUserId, userId)
               .in(ClubMember::getRole, 1, 2)
               .eq(ClubMember::getStatus, 1);
        return clubMemberMapper.selectCount(wrapper) > 0;
    }
    
    private ActivityVO convertToVO(Activity activity, Long userId) {
        ActivityVO vo = BeanUtil.copyProperties(activity, ActivityVO.class);
        
        Club club = clubMapper.selectById(activity.getClubId());
        if (club != null) {
            vo.setClubName(club.getName());
        }
        
        if (userId != null) {
            LambdaQueryWrapper<ActivityRegistration> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ActivityRegistration::getActivityId, activity.getId())
                   .eq(ActivityRegistration::getUserId, userId)
                   .ne(ActivityRegistration::getStatus, 2);
            vo.setIsRegistered(registrationMapper.selectCount(wrapper) > 0);
        }
        
        return vo;
    }
}

