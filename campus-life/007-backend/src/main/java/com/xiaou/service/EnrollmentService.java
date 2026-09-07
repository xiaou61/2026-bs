package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Enrollment;
import com.xiaou.mapper.EnrollmentMapper;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService extends ServiceImpl<EnrollmentMapper, Enrollment> {

    public Enrollment getByActivityIdAndUserId(Long activityId, Long userId) {
        return this.getOne(new QueryWrapper<Enrollment>()
                .eq("activity_id", activityId)
                .eq("user_id", userId));
    }

    public boolean hasEnrolled(Long activityId, Long userId) {
        Enrollment enrollment = getByActivityIdAndUserId(activityId, userId);
        return enrollment != null && enrollment.getStatus() != 3;
    }
}

