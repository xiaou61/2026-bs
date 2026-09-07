package com.xiaou.wedding.service.impl;

import com.xiaou.wedding.entity.Photographer;
import com.xiaou.wedding.exception.BusinessException;
import com.xiaou.wedding.mapper.PhotographerMapper;
import com.xiaou.wedding.service.PhotographerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotographerServiceImpl implements PhotographerService {

    @Autowired
    private PhotographerMapper photographerMapper;

    @Override
    public List<Photographer> list(String level) {
        return photographerMapper.list(level);
    }

    @Override
    public Photographer detail(Long id) {
        Photographer p = photographerMapper.findById(id);
        if (p == null) {
            throw new BusinessException("Photographer not found");
        }
        return p;
    }

    @Override
    public void create(Photographer photographer) {
        photographerMapper.insert(photographer);
    }

    @Override
    public void update(Photographer photographer) {
        if (photographerMapper.findById(photographer.getId()) == null) {
            throw new BusinessException("Photographer not found");
        }
        photographerMapper.update(photographer);
    }

    @Override
    public void delete(Long id) {
        if (photographerMapper.findById(id) == null) {
            throw new BusinessException("Photographer not found");
        }
        photographerMapper.logicDelete(id);
    }
}
