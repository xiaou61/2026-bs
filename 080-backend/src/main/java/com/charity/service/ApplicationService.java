package com.charity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.entity.Application;
import com.charity.mapper.ApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationMapper applicationMapper;

    public Page<Application> getList(int pageNum, int pageSize, Integer applyStatus) {
        Page<Application> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Application> wrapper = new QueryWrapper<>();
        if (applyStatus != null) {
            wrapper.eq("apply_status", applyStatus);
        }
        wrapper.orderByDesc("create_time");
        return applicationMapper.selectPage(page, wrapper);
    }

    public void submit(Application application) {
        applicationMapper.insert(application);
    }

    public void review(Long id, Integer applyStatus, String reviewComment, Long reviewerId) {
        Application application = applicationMapper.selectById(id);
        application.setApplyStatus(applyStatus);
        application.setReviewComment(reviewComment);
        application.setReviewerId(reviewerId);
        application.setReviewTime(LocalDateTime.now());
        applicationMapper.updateById(application);
    }
}
