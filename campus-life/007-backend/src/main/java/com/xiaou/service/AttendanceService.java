package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Attendance;
import com.xiaou.mapper.AttendanceMapper;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService extends ServiceImpl<AttendanceMapper, Attendance> {

    public Attendance getByActivityIdAndUserId(Long activityId, Long userId) {
        return this.getOne(new QueryWrapper<Attendance>()
                .eq("activity_id", activityId)
                .eq("user_id", userId));
    }

    public boolean hasCheckedIn(Long activityId, Long userId) {
        Attendance attendance = getByActivityIdAndUserId(activityId, userId);
        return attendance != null;
    }
}

