package com.xiaou.recruitment.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.recruitment.entity.Interview;
import com.xiaou.recruitment.mapper.InterviewMapper;
import org.springframework.stereotype.Service;

@Service
public class InterviewService extends ServiceImpl<InterviewMapper, Interview> {

    public boolean createInterview(Interview interview) {
        interview.setStatus("scheduled");
        return save(interview);
    }

    public boolean updateInterview(Interview interview) {
        return updateById(interview);
    }

    public boolean cancelInterview(Long id) {
        Interview interview = getById(id);
        if (interview != null) {
            interview.setStatus("cancelled");
            return updateById(interview);
        }
        return false;
    }

    public IPage<Interview> getMyInterviews(Integer page, Integer size, Long userId) {
        Page<Interview> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Interview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Interview::getUserId, userId);
        wrapper.orderByDesc(Interview::getInterviewTime);
        return page(pageParam, wrapper);
    }
}
