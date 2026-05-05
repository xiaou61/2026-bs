package com.meddevice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meddevice.entity.QrTrace;
import com.meddevice.mapper.QrTraceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QrTraceService {
    private final QrTraceMapper qrTraceMapper;

    public PageInfo<QrTrace> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(qrTraceMapper.selectPage(keyword, status));
    }

    public void save(QrTrace entity) {
        if (entity.getId() == null) qrTraceMapper.insert(entity);
        else qrTraceMapper.update(entity);
    }

    public void delete(Long id) {
        qrTraceMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        qrTraceMapper.updateStatus(id, status);
    }
}
