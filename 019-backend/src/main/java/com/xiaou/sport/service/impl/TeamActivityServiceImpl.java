package com.xiaou.sport.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.sport.entity.ActivityParticipant;
import com.xiaou.sport.entity.TeamActivity;
import com.xiaou.sport.mapper.TeamActivityMapper;
import com.xiaou.sport.service.ActivityParticipantService;
import com.xiaou.sport.service.TeamActivityService;
import com.xiaou.sport.service.UserService;
import com.xiaou.sport.utils.PointRuleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TeamActivityServiceImpl extends ServiceImpl<TeamActivityMapper, TeamActivity>
        implements TeamActivityService {

    @Autowired
    private ActivityParticipantService activityParticipantService;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public boolean createActivity(Long userId, TeamActivity activity) {
        activity.setCreatorId(userId);
        activity.setCurrentParticipants(1);
        activity.setStatus("recruiting");
        boolean saved = this.save(activity);
        if (!saved) {
            return false;
        }

        ActivityParticipant participant = new ActivityParticipant();
        participant.setActivityId(activity.getId());
        participant.setUserId(userId);
        participant.setJoinTime(LocalDateTime.now());
        participant.setStatus("joined");
        if (!activityParticipantService.save(participant)) {
            throw new IllegalStateException("创建活动参与记录失败");
        }
        return true;
    }

    @Override
    @Transactional
    public boolean joinActivity(Long activityId, Long userId) {
        TeamActivity activity = this.getById(activityId);
        if (activity == null || !"recruiting".equals(activity.getStatus())
                || activity.getCurrentParticipants() >= activity.getMaxParticipants()) {
            return false;
        }

        LambdaQueryWrapper<ActivityParticipant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivityParticipant::getActivityId, activityId)
                .eq(ActivityParticipant::getUserId, userId);
        ActivityParticipant participant = activityParticipantService.getOne(wrapper);
        if (participant != null && "joined".equals(participant.getStatus())) {
            return false;
        }

        activity.setCurrentParticipants(activity.getCurrentParticipants() + 1);
        if (activity.getCurrentParticipants().equals(activity.getMaxParticipants())) {
            activity.setStatus("full");
        } else {
            activity.setStatus("recruiting");
        }

        boolean participantSaved;
        if (participant == null) {
            participant = new ActivityParticipant();
            participant.setActivityId(activityId);
            participant.setUserId(userId);
            participant.setJoinTime(LocalDateTime.now());
            participant.setStatus("joined");
            participantSaved = activityParticipantService.save(participant);
        } else {
            participant.setJoinTime(LocalDateTime.now());
            participant.setStatus("joined");
            participantSaved = activityParticipantService.updateById(participant);
        }

        boolean activityUpdated = participantSaved && this.updateById(activity);
        if (activityUpdated) {
            changeUserPoints(userId, PointRuleUtil.getActivityJoinPoints());
        }
        return activityUpdated;
    }

    @Override
    @Transactional
    public boolean cancelJoin(Long activityId, Long userId) {
        LambdaQueryWrapper<ActivityParticipant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivityParticipant::getActivityId, activityId)
                .eq(ActivityParticipant::getUserId, userId);
        ActivityParticipant participant = activityParticipantService.getOne(wrapper);

        if (participant == null || !"joined".equals(participant.getStatus())) {
            return false;
        }

        TeamActivity activity = this.getById(activityId);
        if (activity == null) {
            return false;
        }
        activity.setCurrentParticipants(activity.getCurrentParticipants() - 1);
        if (activity.getCurrentParticipants() < 0) {
            activity.setCurrentParticipants(0);
        }
        if ("full".equals(activity.getStatus()) || activity.getCurrentParticipants() < activity.getMaxParticipants()) {
            activity.setStatus("recruiting");
        }

        participant.setStatus("cancelled");
        boolean cancelled = activityParticipantService.updateById(participant) && this.updateById(activity);
        if (cancelled) {
            changeUserPoints(userId, -PointRuleUtil.getActivityJoinPoints());
        }
        return cancelled;
    }

    private void changeUserPoints(Long userId, int delta) {
        if (delta == 0) {
            return;
        }
        var user = userService.getById(userId);
        if (user == null) {
            return;
        }
        int currentPoints = user.getPoints() == null ? 0 : user.getPoints();
        user.setPoints(Math.max(currentPoints + delta, 0));
        userService.updateById(user);
    }
}
