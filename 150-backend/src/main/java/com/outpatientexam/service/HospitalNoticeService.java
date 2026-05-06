package com.outpatientexam.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.outpatientexam.entity.HospitalNotice;
import com.outpatientexam.mapper.HospitalNoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HospitalNoticeService {
    private final HospitalNoticeMapper queueCallMapper;

    public PageInfo<HospitalNotice> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(queueCallMapper.selectPage(keyword, status));
    }

    public void save(HospitalNotice entity) {
        if (entity.getId() == null) queueCallMapper.insert(entity);
        else queueCallMapper.update(entity);
    }

    public void delete(Long id) {
        queueCallMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        queueCallMapper.updateStatus(id, status);
    }
}








