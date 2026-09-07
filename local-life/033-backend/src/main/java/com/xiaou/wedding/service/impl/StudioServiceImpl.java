package com.xiaou.wedding.service.impl;

import com.xiaou.wedding.entity.Studio;
import com.xiaou.wedding.exception.BusinessException;
import com.xiaou.wedding.mapper.StudioMapper;
import com.xiaou.wedding.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudioServiceImpl implements StudioService {

    @Autowired
    private StudioMapper studioMapper;

    @Override
    public List<Studio> list() {
        return studioMapper.list();
    }

    @Override
    public Studio detail(Long id) {
        Studio studio = studioMapper.findById(id);
        if (studio == null) {
            throw new BusinessException("Studio not found");
        }
        return studio;
    }

    @Override
    public void create(Studio studio) {
        studioMapper.insert(studio);
    }

    @Override
    public void update(Studio studio) {
        if (studioMapper.findById(studio.getId()) == null) {
            throw new BusinessException("Studio not found");
        }
        studioMapper.update(studio);
    }

    @Override
    public void delete(Long id) {
        if (studioMapper.findById(id) == null) {
            throw new BusinessException("Studio not found");
        }
        studioMapper.logicDelete(id);
    }
}
