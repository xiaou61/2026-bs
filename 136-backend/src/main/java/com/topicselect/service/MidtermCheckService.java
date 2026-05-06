package com.topicselect.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topicselect.entity.MidtermCheck;
import com.topicselect.mapper.MidtermCheckMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MidtermCheckService {
    private final MidtermCheckMapper patentRecordMapper;

    public PageInfo<MidtermCheck> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(patentRecordMapper.selectPage(keyword, status));
    }

    public void save(MidtermCheck entity) {
        if (entity.getId() == null) patentRecordMapper.insert(entity);
        else patentRecordMapper.update(entity);
    }

    public void delete(Long id) {
        patentRecordMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        patentRecordMapper.updateStatus(id, status);
    }
}

