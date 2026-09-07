package com.esgreport.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.esgreport.entity.ReviewTask;
import com.esgreport.mapper.ReviewTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewTaskService {
    private final ReviewTaskMapper reviewTaskMapper;

    public PageInfo<ReviewTask> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(reviewTaskMapper.selectPage(keyword, status));
    }

    public void save(ReviewTask entity) {
        if (entity.getId() == null) reviewTaskMapper.insert(entity);
        else reviewTaskMapper.update(entity);
    }

    public void delete(Long id) {
        reviewTaskMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        reviewTaskMapper.updateStatus(id, status);
    }
}
