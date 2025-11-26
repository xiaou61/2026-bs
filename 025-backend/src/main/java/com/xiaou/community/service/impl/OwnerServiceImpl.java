package com.xiaou.community.service.impl;

import com.xiaou.community.entity.Owner;
import com.xiaou.community.mapper.OwnerMapper;
import com.xiaou.community.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {
    @Autowired
    private OwnerMapper ownerMapper;

    @Override
    public void add(Owner owner) {
        ownerMapper.insert(owner);
    }

    @Override
    public void update(Owner owner) {
        ownerMapper.update(owner);
    }

    @Override
    public void delete(Integer id) {
        ownerMapper.deleteById(id);
    }

    @Override
    public Owner getById(Integer id) {
        return ownerMapper.findById(id);
    }

    @Override
    public Owner getByUserId(Integer userId) {
        return ownerMapper.findByUserId(userId);
    }

    @Override
    public List<Owner> getAll() {
        return ownerMapper.findAll();
    }
}
