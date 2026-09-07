package com.meddevice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meddevice.entity.BorrowRequest;
import com.meddevice.mapper.BorrowRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BorrowRequestService {
    private final BorrowRequestMapper borrowRequestMapper;

    public PageInfo<BorrowRequest> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(borrowRequestMapper.selectPage(keyword, status));
    }

    public void save(BorrowRequest entity) {
        if (entity.getId() == null) borrowRequestMapper.insert(entity);
        else borrowRequestMapper.update(entity);
    }

    public void delete(Long id) {
        borrowRequestMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        borrowRequestMapper.updateStatus(id, status);
    }
}
