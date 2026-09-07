package com.greenhouse.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.greenhouse.entity.RemoteCommand;
import com.greenhouse.mapper.RemoteCommandMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoteCommandService {
    private final RemoteCommandMapper remoteCommandMapper;

    public PageInfo<RemoteCommand> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(remoteCommandMapper.selectPage(keyword, status));
    }

    public void save(RemoteCommand entity) {
        if (entity.getId() == null) remoteCommandMapper.insert(entity);
        else remoteCommandMapper.update(entity);
    }

    public void delete(Long id) {
        remoteCommandMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        remoteCommandMapper.updateStatus(id, status);
    }
}
