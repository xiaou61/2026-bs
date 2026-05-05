package com.esgreport.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.esgreport.entity.ScoreModel;
import com.esgreport.mapper.ScoreModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreModelService {
    private final ScoreModelMapper scoreModelMapper;

    public PageInfo<ScoreModel> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(scoreModelMapper.selectPage(keyword, status));
    }

    public void save(ScoreModel entity) {
        if (entity.getId() == null) scoreModelMapper.insert(entity);
        else scoreModelMapper.update(entity);
    }

    public void delete(Long id) {
        scoreModelMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        scoreModelMapper.updateStatus(id, status);
    }
}
