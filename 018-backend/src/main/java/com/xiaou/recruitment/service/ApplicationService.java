package com.xiaou.recruitment.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.recruitment.entity.Application;
import com.xiaou.recruitment.mapper.ApplicationMapper;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService extends ServiceImpl<ApplicationMapper, Application> {

    public boolean submitApplication(Application application) {
        application.setStatus("pending");
        return save(application);
    }

    public boolean updateApplicationStatus(Long id, String status, String remark) {
        Application application = getById(id);
        if (application != null) {
            application.setStatus(status);
            if (remark != null) {
                application.setRemark(remark);
            }
            return updateById(application);
        }
        return false;
    }

    public IPage<Application> getMyApplications(Integer page, Integer size, Long userId) {
        Page<Application> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Application> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Application::getUserId, userId);
        wrapper.orderByDesc(Application::getCreatedAt);
        return page(pageParam, wrapper);
    }

    public IPage<Application> getReceivedApplications(Integer page, Integer size, Long jobId) {
        Page<Application> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Application> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Application::getJobId, jobId);
        wrapper.orderByDesc(Application::getCreatedAt);
        return page(pageParam, wrapper);
    }
}
