package com.hrm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hrm.entity.Recruitment;
import com.hrm.mapper.RecruitmentMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class RecruitmentService {
    @Resource
    private RecruitmentMapper recruitmentMapper;

    public Recruitment getById(Long id) {
        return recruitmentMapper.selectById(id);
    }

    public PageInfo<Recruitment> getList(Long positionId, Long departmentId, String status,
                                          Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(recruitmentMapper.selectList(positionId, departmentId, status));
    }

    public List<Recruitment> getOpen() {
        return recruitmentMapper.selectOpen();
    }

    public void add(Recruitment recruitment) {
        recruitment.setStatus("open");
        recruitmentMapper.insert(recruitment);
    }

    public void update(Recruitment recruitment) {
        recruitmentMapper.update(recruitment);
    }

    public void delete(Long id) {
        recruitmentMapper.deleteById(id);
    }

    public void close(Long id) {
        Recruitment recruitment = new Recruitment();
        recruitment.setId(id);
        recruitment.setStatus("closed");
        recruitmentMapper.update(recruitment);
    }

    public int getOpenCount() {
        return recruitmentMapper.countByStatus("open");
    }
}
