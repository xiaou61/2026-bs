package com.licensecheck.service;

import com.licensecheck.entity.ScanTask;
import com.licensecheck.mapper.ScanTaskMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScanTaskService {
    private final ScanTaskMapper mapper;

    public PageInfo<ScanTask> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public ScanTask get(Long id) {
        return mapper.selectById(id);
    }

    public void save(ScanTask entity) {

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
