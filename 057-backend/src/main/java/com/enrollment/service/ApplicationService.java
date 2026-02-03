package com.enrollment.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enrollment.entity.Application;
import com.enrollment.mapper.ApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationMapper applicationMapper;

    public IPage<Application> getPage(Integer pageNum, Integer pageSize, String studentName, Integer status) {
        Page<Application> page = new Page<>(pageNum, pageSize);
        return applicationMapper.selectPageWithStudent(page, studentName, status);
    }

    public void add(Application application) {
        applicationMapper.insert(application);
    }

    public void update(Application application) {
        applicationMapper.updateById(application);
    }

    public void delete(Long id) {
        applicationMapper.deleteById(id);
    }

    public void audit(Long id, Integer status, String remark) {
        Application application = new Application();
        application.setId(id);
        application.setStatus(status);
        application.setRemark(remark);
        applicationMapper.updateById(application);
    }
}
