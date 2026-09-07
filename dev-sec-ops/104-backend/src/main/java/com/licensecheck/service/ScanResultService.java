package com.licensecheck.service;

import com.licensecheck.entity.ScanResult;
import com.licensecheck.mapper.ScanResultMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScanResultService {
    private final ScanResultMapper mapper;

    public PageInfo<ScanResult> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public ScanResult get(Long id) {
        return mapper.selectById(id);
    }

    public void save(ScanResult entity) {

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
