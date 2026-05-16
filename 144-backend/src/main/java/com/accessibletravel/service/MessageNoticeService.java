package com.accessibletravel.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.accessibletravel.entity.MessageNotice;
import com.accessibletravel.mapper.MessageNoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageNoticeService {
    private final MessageNoticeMapper messageNoticeMapper;

    public PageInfo<MessageNotice> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(messageNoticeMapper.selectPage(keyword, status));
    }

    public void save(MessageNotice entity) {
        if (entity.getId() == null) messageNoticeMapper.insert(entity);
        else messageNoticeMapper.update(entity);
    }

    public void delete(Long id) {
        messageNoticeMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        messageNoticeMapper.updateStatus(id, status);
    }
}

