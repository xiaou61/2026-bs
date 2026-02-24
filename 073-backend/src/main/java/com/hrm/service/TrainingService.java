package com.hrm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hrm.entity.Training;
import com.hrm.mapper.TrainingMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class TrainingService {
    @Resource
    private TrainingMapper trainingMapper;

    public Training getById(Long id) {
        return trainingMapper.selectById(id);
    }

    public PageInfo<Training> getList(String title, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(trainingMapper.selectList(title, status));
    }

    public void add(Training training) {
        training.setStatus(1);
        trainingMapper.insert(training);
    }

    public void update(Training training) {
        trainingMapper.update(training);
    }

    public void delete(Long id) {
        trainingMapper.deleteById(id);
    }
}
