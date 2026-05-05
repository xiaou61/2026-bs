package com.esgreport.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.esgreport.entity.EsgScore;
import com.esgreport.mapper.EsgScoreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EsgScoreService {
    private final EsgScoreMapper esgScoreMapper;

    public PageInfo<EsgScore> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(esgScoreMapper.selectPage(keyword, status));
    }

    public void save(EsgScore entity) {
        if (entity.getId() == null) esgScoreMapper.insert(entity);
        else esgScoreMapper.update(entity);
    }

    public void delete(Long id) {
        esgScoreMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        esgScoreMapper.updateStatus(id, status);
    }
}
