package com.foodinspect.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.foodinspect.entity.InspectionNotice;
import com.foodinspect.mapper.InspectionNoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InspectionNoticeService {
    private final InspectionNoticeMapper riskWarningMapper;

    public PageInfo<InspectionNotice> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(riskWarningMapper.selectPage(keyword, status));
    }

    public void save(InspectionNotice entity) {
        if (entity.getId() == null) riskWarningMapper.insert(entity);
        else riskWarningMapper.update(entity);
    }

    public void delete(Long id) {
        riskWarningMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        riskWarningMapper.updateStatus(id, status);
    }
}






