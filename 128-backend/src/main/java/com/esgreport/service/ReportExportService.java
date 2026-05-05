package com.esgreport.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.esgreport.entity.ReportExport;
import com.esgreport.mapper.ReportExportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportExportService {
    private final ReportExportMapper reportExportMapper;

    public PageInfo<ReportExport> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(reportExportMapper.selectPage(keyword, status));
    }

    public void save(ReportExport entity) {
        if (entity.getId() == null) reportExportMapper.insert(entity);
        else reportExportMapper.update(entity);
    }

    public void delete(Long id) {
        reportExportMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        reportExportMapper.updateStatus(id, status);
    }
}
