package com.xiaou.community.service.impl;

import com.xiaou.community.entity.Visitor;
import com.xiaou.community.mapper.VisitorMapper;
import com.xiaou.community.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VisitorServiceImpl implements VisitorService {
    @Autowired
    private VisitorMapper visitorMapper;

    @Override
    public void register(Visitor visitor) {
        visitor.setStatus("PENDING");
        visitorMapper.insert(visitor);
    }

    @Override
    public void updateStatus(Integer id, String status) {
        Visitor visitor = new Visitor();
        visitor.setId(id);
        visitor.setStatus(status);
        visitorMapper.update(visitor);
    }

    @Override
    public List<Visitor> getByOwnerId(Integer ownerId) {
        return visitorMapper.findByOwnerId(ownerId);
    }

    @Override
    public List<Visitor> getAll() {
        return visitorMapper.findAll();
    }
}
