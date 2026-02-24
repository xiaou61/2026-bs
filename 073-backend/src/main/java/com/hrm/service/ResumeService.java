package com.hrm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hrm.entity.Resume;
import com.hrm.mapper.ResumeMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class ResumeService {
    @Resource
    private ResumeMapper resumeMapper;

    public Resume getById(Long id) {
        return resumeMapper.selectById(id);
    }

    public PageInfo<Resume> getList(Long recruitmentId, String name, String status,
                                     Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(resumeMapper.selectList(recruitmentId, name, status));
    }

    public void add(Resume resume) {
        resume.setStatus("pending");
        resumeMapper.insert(resume);
    }

    public void update(Resume resume) {
        resumeMapper.update(resume);
    }

    public void delete(Long id) {
        resumeMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        Resume resume = new Resume();
        resume.setId(id);
        resume.setStatus(status);
        resumeMapper.update(resume);
    }
}
