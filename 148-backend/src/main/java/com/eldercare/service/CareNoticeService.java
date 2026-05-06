package com.eldercare.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.eldercare.entity.CareNotice;
import com.eldercare.mapper.CareNoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CareNoticeService {
    private final CareNoticeMapper alertEventMapper;

    public PageInfo<CareNotice> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(alertEventMapper.selectPage(keyword, status));
    }

    public void save(CareNotice entity) {
        if (entity.getId() == null) alertEventMapper.insert(entity);
        else alertEventMapper.update(entity);
    }

    public void delete(Long id) {
        alertEventMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        alertEventMapper.updateStatus(id, status);
    }
}







