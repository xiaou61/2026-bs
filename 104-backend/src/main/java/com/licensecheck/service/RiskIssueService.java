package com.licensecheck.service;

import com.licensecheck.entity.RiskIssue;
import com.licensecheck.mapper.RiskIssueMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiskIssueService {
    private final RiskIssueMapper mapper;

    public PageInfo<RiskIssue> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public RiskIssue get(Long id) {
        return mapper.selectById(id);
    }

    public void save(RiskIssue entity) {

        if (entity.getId() == null) {
            mapper.insert(entity);
        } else {
            mapper.update(entity);
        }
    }

    public void delete(Long id) {
        mapper.deleteById(id);
    }


    public void updateStatus(Long id, String status) {
        mapper.updateStatus(id, status);
    }

}
