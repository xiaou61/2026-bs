package com.econtract.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.econtract.entity.ExpirationReminder;
import com.econtract.mapper.ExpirationReminderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpirationReminderService {
    private final ExpirationReminderMapper patentRecordMapper;

    public PageInfo<ExpirationReminder> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(patentRecordMapper.selectPage(keyword, status));
    }

    public void save(ExpirationReminder entity) {
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



