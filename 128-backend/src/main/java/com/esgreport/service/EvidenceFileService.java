package com.esgreport.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.esgreport.entity.EvidenceFile;
import com.esgreport.mapper.EvidenceFileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvidenceFileService {
    private final EvidenceFileMapper evidenceFileMapper;

    public PageInfo<EvidenceFile> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(evidenceFileMapper.selectPage(keyword, status));
    }

    public void save(EvidenceFile entity) {
        if (entity.getId() == null) evidenceFileMapper.insert(entity);
        else evidenceFileMapper.update(entity);
    }

    public void delete(Long id) {
        evidenceFileMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        evidenceFileMapper.updateStatus(id, status);
    }
}
