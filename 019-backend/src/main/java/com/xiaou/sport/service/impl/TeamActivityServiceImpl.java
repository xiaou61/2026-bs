package com.xiaou.sport.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.sport.entity.ActivityParticipant;
import com.xiaou.sport.entity.TeamActivity;
import com.xiaou.sport.mapper.TeamActivityMapper;
import com.xiaou.sport.service.ActivityParticipantService;
import com.xiaou.sport.service.TeamActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TeamActivityServiceImpl extends ServiceImpl<TeamActivityMapper, TeamActivity>
        implements TeamActivityService {

    @Autowired
    private ActivityParticipantService activityParticipantService;

    @Override
    @Transactional
    public boolean joinActivity(Long activityId, Long userId) {
        TeamActivity activity = this.getById(activityId);
        if (activity == null || activity.getCurrentParticipants() >= activity.getMaxParticipants()) {
            return false;
        }

        LambdaQueryWrapper<ActivityParticipant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivityParticipant::getActivityId, activityId)
                .eq(ActivityParticipant::getUserId, userId);
        if (activityParticipantService.count(wrapper) > 0) {
            return false;
        }

        ActivityParticipant participant = new ActivityParticipant();
        participant.setActivityId(activityId);
        participant.setUserId(userId);
        participant.setJoinTime(LocalDateTime.now());
        participant.setStatus("joined");

        activity.setCurrentParticipants(activity.getCurrentParticipants() + 1);
        if (activity.getCurrentParticipants().equals(activity.getMaxParticipants())) {
            activity.setStatus("full");
        }

        return activityParticipantService.save(participant) && this.updateById(activity);
    }

    @Override
    @Transactional
    public boolean cancelJoin(Long activityId, Long userId) {
        LambdaQueryWrapper<ActivityParticipant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivityParticipant::getActivityId, activityId)
                .eq(ActivityParticipant::getUserId, userId);
        ActivityParticipant participant = activityParticipantService.getOne(wrapper);

        if (participant == null) {
            return false;
        }

        TeamActivity activity = this.getById(activityId);
        activity.setCurrentParticipants(activity.getCurrentParticipants() - 1);
        activity.setStatus("recruiting");

        participant.setStatus("cancelled");
        return activityParticipantService.updateById(participant) && this.updateById(activity);
    }
}
