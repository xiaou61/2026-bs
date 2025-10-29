package com.xiaou.recruitment.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.recruitment.entity.Resume;
import com.xiaou.recruitment.mapper.ResumeMapper;
import org.springframework.stereotype.Service;

@Service
public class ResumeService extends ServiceImpl<ResumeMapper, Resume> {

    public boolean createResume(Resume resume) {
        return save(resume);
    }

    public boolean updateResume(Resume resume) {
        return updateById(resume);
    }

    public Resume getResumeById(Long id) {
        return getById(id);
    }

    public Resume getResumeByUserId(Long userId) {
        LambdaQueryWrapper<Resume> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Resume::getUserId, userId);
        wrapper.orderByDesc(Resume::getCreatedAt);
        wrapper.last("LIMIT 1");
        return getOne(wrapper);
    }
}
